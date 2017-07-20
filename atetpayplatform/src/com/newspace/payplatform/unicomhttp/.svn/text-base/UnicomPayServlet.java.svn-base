package com.newspace.payplatform.unicomhttp;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.bindtelephone.bo.BindTelephoneBo;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.order.PaymentOrderReq;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.unicompay.UnicomPayResponse;

/**
 * {@link UnicomPayServlet.java} 
 * @description:  联通计费接口，此接口调用联通平台的上行短信接口进行扣费。
 * 					需要注意的是，上行短信接口同步返回的结果只代表扣费短信是否发送成功，不代表扣费是否成功。
 * 					扣费结果联通平台会以异步的形式通知ATET平台，SDK在使用联通扣费的时候需要区别处理。
 * 					处理方案：1、首先调用本接口（unicomPayServlet）上行扣费短信，根据接收到的响应信息判断扣费短信是否发送成功
 * 							2、如果扣费短信发送成功，则不断调用查询单个订单状态的接口来获取此笔订单的支付结果 。
 * @author:  huqili
 * @date 2015年4月14日
 * 
 */
@SuppressWarnings("serial")
public class UnicomPayServlet extends BaseServlet
{
	private static final BindTelephoneBo bindTelBo = SpringBeanUtils.getBean(BindTelephoneBo.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		String reqJsonStr = getStrFromRequest(request);
		PaymentOrderReq req = PaymentOrderReq.getInstanceFromJson(reqJsonStr);
		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());
		PaymentOrderVo orderVo = null;
		String telephone = null;

		if (req != null && key != null)
		{
			//根据订单号获取订单对象,并将请求参数填充到对象中,然后更新记录。
			orderVo = PayUtils.queryByOrderNo(req.getOrderNo(),Boolean.TRUE);
			
			if(orderVo == null){
				returnCode = ReturnCode.ORDERNO_OBJECT_FAIL.getCode();
			}else{
				orderVo = generateOrderObj(orderVo,req);
				returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());
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
				/**
				 * 请求联通平台上行（扣费）短信接口。
				 * 注意：此方法返回的结果只代表上行短信是否发送成功 .
				 * 		支付结果会由联通平台异步通知到服务器。
				 */
				returnCode = HttpReceiveMsg.MO(orderVo.getAmount() / 100.0, telephone, orderVo.getOrderNo());
				logger.info(String.format("【UnicomPayServlet：上行短信返回状态码：%s】", returnCode));
				
				if(returnCode == ReturnCode.SUCCESS.getCode()){  //如果短信发送成功，则修改计费状态
					orderVo.setState(PaymentOrderVo.PAY_STATE_SENDMSGSUC);
				}
				
				//更新订单状态
				orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
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
	

		// 输出响应结果
		UnicomPayResponse respVo = new UnicomPayResponse();
		
		respVo.setCode(returnCode);
		respVo.setDesc(ReturnCode.getDesc(returnCode));
		respVo.setOrderNo(orderVo == null ? null : orderVo.getOrderNo());
		
		if (StringUtils.isNullOrEmpty(key))
			respVo.setSign(PayUtils.WRONG_SIGN);
		else
			respVo.setSign(PayUtils.getSignByAESandMD5(respVo.getData(), key));
		
		logger.info(String.format("联通支付,上行扣费短信_响应结果：%s，orderNo：%s，appId：%s",respVo.getDesc(),req.getOrderNo(),req.getAppId() ));
		outputResult(response, respVo.getJsonStr());
	}

	/**
	 * 填充订单对象 
	 */
	private PaymentOrderVo generateOrderObj(PaymentOrderVo vo , PaymentOrderReq req)
	{
		if(!vo.getAtetId().equals(req.getAtetId()))
			vo.setIsScanPay(PaymentOrderVo.IS_SCAN_PAY);
		
		if(vo.getUserId() == null) //说明此订单是一期大厅请求的，本接口的本次请求也是一期大厅发起的
		{
			vo.setPartnerId(req.getPartnerId());
			vo.setPackageName(req.getPackageName());
			vo.setDeviceCode(req.getDeviceCode());
			vo.setPayPoint(req.getPayPoint());
			vo.setGameName(req.getGameName());
			vo.setProductId(req.getProductId());
			vo.setAmount(req.getAmount());
			vo.setCpId(req.getCpId());
			vo.setCounts(req.getCounts());
			vo.setCpOrderNo(req.getCpOrderNo());
			vo.setCpNotifyUrl(req.getCpNotifyUrl());
			vo.setCpPrivateInfo(req.getCpPrivateInfo());
		}

		vo.setUserId(req.getUserId());
		vo.setAmountType(PaymentOrderVo.AMOUNT_TYPE_RMB);
		vo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM);
		vo.setState(PaymentOrderVo.PAY_STATE_NONPROCESS);
		

		return vo;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}