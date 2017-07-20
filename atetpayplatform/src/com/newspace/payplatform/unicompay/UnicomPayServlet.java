package com.newspace.payplatform.unicompay;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.utils.TwoTuple;
import com.newspace.payplatform.ConstantData;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.bindtelephone.bo.BindTelephoneBo;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.order.PaymentOrderReq;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.paynotify.NotifyContent;
import com.newspace.payplatform.paynotify.NotifyEntity;
import com.newspace.payplatform.unicomhttp.HttpSendMsgTask;
import com.newspace.payplatform.unicompay.ice.ResVacBean;
import com.newspace.payplatform.warnnotify.CheckAccountTask;

/**
 * UnicomPayServlet.java 
 * 描 述:  联通支付接口
 * 作 者:  liushuai
 * 历 史： 2014-7-10 创建
 */
@SuppressWarnings("serial")
public class UnicomPayServlet extends BaseServlet
{
	private static final BindTelephoneBo bindTelBo = SpringBeanUtils.getBean(BindTelephoneBo.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		String reqJsonStr = getStrFromRequest(request);
		PaymentOrderReq req = PaymentOrderReq.getInstanceFromJson(reqJsonStr);
		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());
		PaymentOrderVo orderVo = null;
		String telephone = null;

		if (req != null && key != null && req.isLegal())
		{
			//根据订单号获取订单对象,并将请求参数填充到对象中,然后更新记录。
			orderVo = PayUtils.queryByOrderNo(req.getOrderNo(),Boolean.TRUE);
			if(orderVo == null){
				returnCode = ReturnCode.ORDERNO_OBJECT_FAIL.getCode();
			}else{
				orderVo = generateOrderObj(orderVo,req);
				returnCode = PayUtils.verifyAndRecordOrder(req.getData(), req.getSign(), orderVo);
			}
		}

		if (returnCode == ReturnCode.SUCCESS.getCode() && orderVo != null)
		{
			telephone = bindTelBo.getTelephone(req.getUserId(),req.getAtetId());
			if (StringUtils.isNullOrEmpty(telephone))
			{
				logger.info("【UnicomPayServlet：未查询出此用户绑定的手机号！】");
				returnCode = ReturnCode.UNICOM_VAC_SIM_ISNULL.getCode();
			}
			else
			{
				logger.info(String.format("【UnicomPayServlet：查询出请求绑定的手机号：%s】", telephone));
				TwoTuple<Integer, ResVacBean> result = IcePaymentClient.call(orderVo.getAmount() / 100.0, telephone);
				// result.first 是 IceClient.call方法是否发生异常的返回码
				if ((returnCode = result.first) == ReturnCode.SUCCESS.getCode() && result.second != null)
				{
					// result.second.resultcode是此次支付是否成功的返回码
					returnCode = Integer.parseInt(result.second.resultcode);
					logger.info(String.format("【UnicomPayServlet：支付请求返回状态码：%s】", returnCode));
					orderVo.setPaymentCreatedOrderNo(result.second.seq);
					// 更新订单支付状态
					if (returnCode == ReturnCode.SUCCESS.getCode())
						orderVo.setState(PaymentOrderVo.PAY_STATE_SUCCESS);
					else
						orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
					orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				}
				else
				{
					logger.info("【UnicomPayServlet：支付请求发生异常】");
					orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
				}
				PayUtils.saveOrUpdateOrder(orderVo);
			}
		}

		// 请求失败，记录日志
		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			String data = req == null ? null : req.getData();
			String sign = req == null ? null : req.getSign();
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_UNICOM_VAC_PAYMENT, data, sign, returnCode);
		}
		else
		{
			//检查此订单是否异常，如果异常则下发预警短信。
			pool.execute(new CheckAccountTask(orderVo));
			
			if (orderVo.getIsReceipt() == 1)
			{ // 发送通知短信 ,通知用户扣费相关信息
				String msgContent = String.format("【欢乐沃】您购买的%s，数量%s，共计%s元 已经购买成功！"+ConstantData.APPEND_BLANK, orderVo.getPayPoint(), orderVo.getCounts(),
						orderVo.getAmount() / 100.0);
				
				pool.execute(new HttpSendMsgTask(telephone, msgContent));
				
			}

			// 如果请求参数中包含了notifyUrL就使用请求参数中的，如果没有则从数据库读取
			String cpNotifyUrl = StringUtils.isNullOrEmpty(orderVo.getCpNotifyUrl()) ? PayUtils.getNotifyUrl(orderVo.getAppId()) : orderVo
					.getCpNotifyUrl();
			if (!StringUtils.isNullOrEmpty(cpNotifyUrl) && StringUtils.isHttpURL(cpNotifyUrl))
			{
				NotifyContent content = NotifyContent.getInstanceByOrder(orderVo);
				NotifyEntity entity = new NotifyEntity(cpNotifyUrl, content.generateContent());
				HttpAsyncConnExecutor.submitNotifyTask(entity);
			}
		}

		// 输出响应结果
		UnicomPayResponse respVo = new UnicomPayResponse();
		respVo.setCode(returnCode);
		respVo.setDesc(ReturnCode.getDesc(returnCode));
		respVo.setOrderNo(orderVo == null ? null : orderVo.getOrderNo());
		if (StringUtils.isNullOrEmpty(key))
			respVo.setSign(PayUtils.WRONG_SIGN);
		else
			respVo.setSign(PayUtils.getSignByAESandMD5(respVo.getData(), key));
		
		logger.info(String.format("联通支付_响应结果：%s，orderNo：%s，appId：%s",respVo.getDesc(),req.getOrderNo(),req.getAppId() ));
		outputResult(response, respVo.getJsonStr());
	}

	/**
	 * 填充订单对象 
	 */
	private PaymentOrderVo generateOrderObj(PaymentOrderVo vo , PaymentOrderReq req)
	{
		if(!vo.getAtetId().equals(req.getAtetId()))
			vo.setIsScanPay(PaymentOrderVo.IS_SCAN_PAY);
		vo.setPartnerId(req.getPartnerId());
		vo.setPackageName(req.getPackageName());
		vo.setDeviceCode(req.getDeviceCode());
		vo.setPayPoint(req.getPayPoint());
		vo.setGameName(req.getGameName());
		vo.setProductId(req.getProductId());
		vo.setAmount(req.getAmount());
		vo.setCpId(req.getCpId());
		vo.setUserId(req.getUserId());
		vo.setCounts(req.getCounts());
		vo.setAmountType(PaymentOrderVo.AMOUNT_TYPE_RMB);
		vo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM);
		vo.setState(PaymentOrderVo.PAY_STATE_NONPROCESS);
		
		vo.setCpOrderNo(req.getCpOrderNo());
		vo.setCpNotifyUrl(req.getCpNotifyUrl());
		vo.setCpPrivateInfo(req.getCpPrivateInfo());
		return vo;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}