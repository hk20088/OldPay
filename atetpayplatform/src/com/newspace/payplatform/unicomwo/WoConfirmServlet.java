package com.newspace.payplatform.unicomwo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.HttpUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.ConstantData;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.paynotify.NotifyEntity;
import com.newspace.payplatform.unicomwo.SGIP.MtTask;
import com.newspace.payplatform.unicomwo.notify.NotifyChannelContent;
import com.newspace.payplatform.unicomwo.param.WoConfirmReq;
import com.newspace.payplatform.unicomwo.param.WoNotifyUrlEnum;
import com.newspace.payplatform.unicomwo.param.WoProduct;
import com.newspace.payplatform.unicomwo.param.WopayReq;
import com.newspace.payplatform.unicomwo.param.WopayResp;
import com.newspace.payplatform.unicomwo.utils.EncodeUtils;
import com.newspace.payplatform.unicomwo.utils.TraceUtils;

/**
 * @description 二次确认接口。 渠道收到短信验证码后，以程序的方式进行二次确认。
 * @author huqili
 * @date 2016年9月8日
 *
 */
public class WoConfirmServlet extends BaseServlet {

	
	private static final long serialVersionUID = 1L;

	private static final JLogger logger = LoggerUtils.getLogger(WoConfirmServlet.class);
	
	//定义一个线程池
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		logger.info("进入邮箱二次确认接口...");
		
		PaymentOrderVo orderVo = null;
		
		WoConfirmReq reqVo = new WoConfirmReq();
		WopayResp respVo = new WopayResp();
		
		String reqStr = getStrFromRequest(request);
		int returnCode = padRequestVo(reqStr, reqVo);
		
		String json = null;
		BufferedReader reader = null;
		
		String res_desc = null;
		boolean flag = true;
		
		if(returnCode == ReturnCode.SUCCESS.getCode())
		{
			/**
			 * 1、先根据订单号查询出订单详情
			 * 2、校验短信验证码是否正确
			 * 3、如果短信验证码正确，则扣费
			 * 4、如果扣费成功，封装交易轨迹，通知渠道，下发扣费成功的短信
			 */
			logger.info(String.format("接收到用户[%s]填写的验证码为：%s", reqVo.getPhone(),reqVo.getMessCode()));
			
			try 
			{
				//根据订单号和状态查询订单信息
				orderVo = PayUtils.queryByOrderNo(reqVo.getOrderId(), Boolean.TRUE);
				
				if(orderVo != null)
				{
					//扣费需要的信息
					String channelId = orderVo.getChannelShortCode();
					String userPhone = orderVo.getTelephone();
					String phoneNum = EncodeUtils.encode(WoProduct.getKeyMap.get(channelId), userPhone);
					
					//下行短信需要的信息
					String subject = WoProduct.getSubjectMap.get(channelId);
					Integer price = WoProduct.getPriceMap.get(channelId);
					
					//校验验证码是否正确
					if(reqVo.getMessCode().equals(orderVo.getCommandid()))
					{
						logger.info("验证码校验成功...");
						
						WopayReq info = new WopayReq();
						info.setPhoneNum(phoneNum);
						info.setChannelId(channelId);
						json = JsonUtils.toJsonWithExpose(info);
						
						//请求联通沃邮箱进行计费
						long startPayTime = System.currentTimeMillis(); //定义扣费时间点
						HttpEntity entity = HttpUtils.postJson(ConstantData.UNICOM_WO_PAY_URL, json, "utf-8");
						reader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
						String str = reader.readLine();
						
						long endPayTime = System.currentTimeMillis(); //定义扣费时间点
						logger.info(String.format("用户[%s],订单号为[%s],计费结果是：[%s],计费耗时[%s]ms",reqVo.getPhone(), reqVo.getOrderId(),str,(endPayTime - startPayTime)));
						
						//封装计费结果
						returnCode = padRequestVo(str, respVo);
					
						if(respVo.getRes_code().equals("0"))
						{
							
							//计费成功
							orderVo.setState(PaymentOrderVo.PAY_STATE_SUCCESS);
							//将联通订单号封装到paymentCreatedOrdeNo
							orderVo.setPaymentCreatedOrderNo(respVo.getTransaction_id());
							
							logger.info(String.format("用户[%s]计费成功，开始通知渠道...", reqVo.getPhone()));
							
							//计费成功，通知渠道
							corpNotify(orderVo);
							
							logger.info(String.format("用户[%s]异步通知成功，开始封装交易轨迹...", reqVo.getPhone()));
							//封装并记录交易轨迹 
							TraceUtils.insertTrace(orderVo.getCommandid(),reqVo.getPhone(), subject, price, respVo.getTransaction_id());
							
							logger.info(String.format("用户[%s]交易轨迹封装完毕，开始下行扣费成功通知短信...", reqVo.getPhone()));
							
							//计费成功后，下发扣费成功短信给用户 2016年9月12日  添加
							String pay_msgContent = TraceUtils.fillMsgContent_action4(subject, price);
							pool.submit(new MtTask(reqVo.getPhone(), pay_msgContent));
						}
						else
						{
							logger.error(String.format("用户[%s]计费失败...", reqVo.getPhone()));
							//计费失败,这里使用计费网关给的状态码
							orderVo.setState(Integer.parseInt(respVo.getRes_code()));
							
							//如果计费失败，使用计费网关给的状态码和说明
							returnCode = Integer.parseInt(respVo.getRes_code());
							res_desc = respVo.getRes_desc();
							
							//状态标识，如果为false,则代表返回给渠道的状态码是计费网关给的， 不是自己定义的
							flag = false;
							
						}
						
						//更新订单
						PayUtils.saveOrUpdateOrder(orderVo);
						logger.info(String.format("用户[%s]订单更新成功", reqVo.getPhone()));
					}
					else
					{
						logger.error(String.format("手机号[%s],订单号[%s],验证码校验失败...", reqVo.getPhone(),reqVo.getOrderId()));
						returnCode = ReturnCode.VERIFY_CHECKCODE_FAIL.getCode();
					}
				}
				else
				{
					logger.error(String.format("根据手机号[%s],订单号[%s],未查询出未支付的订单，无法扣费...", reqVo.getPhone(),reqVo.getOrderId()));
					returnCode = ReturnCode.NOT_EXIST_MATCHED_INFO.getCode();
				}
				
			} 
			catch (Exception e) 
			{
				logger.error("沃邮箱计费时发生异常...异常信息：%s",e);
				returnCode = ReturnCode.ERROR.getCode();
			
			}
		}
		else
		{
			logger.error("沃邮箱计费参数不正确...");
			returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		}
		
		respVo.setRes_code(String.valueOf(returnCode));
		if(flag)
		{
			respVo.setRes_desc(ReturnCode.getDesc(returnCode));
		}
		else
		{
			respVo.setRes_desc(res_desc);
		}
		
		//响应渠道
		outputResult(response, respVo);
	
	}
	
	/**
	 * 异步通知沃+渠道
	 * @param orderVo
	 */
	private void corpNotify(PaymentOrderVo orderVo)
	{
		//根据企业ID获取通知地址
		String notify_url = WoNotifyUrlEnum.getUrlMap.get(orderVo.getPartnerId());
		
		if(!StringUtils.isExistNullOrEmpty(notify_url) && StringUtils.isHttpURL(notify_url))
		{
			logger.info("开始通知wo+渠道...");
			NotifyChannelContent content = NotifyChannelContent.getinstance(orderVo);
			NotifyEntity notifyEntity;
			try 
			{
				notifyEntity = new NotifyEntity(notify_url, content.generateContent());
				HttpAsyncConnExecutor.submitNotifyTask(notifyEntity);
			} 
			catch (Exception e)
			{
				logger.error(String.format("通知沃+渠道时出现异常，渠道[%s]，用户[%s],异常信息：[%s]", orderVo.getPartnerId(), orderVo.getTelephone(),e));
			}
			
			logger.info(String.format("用户[%s]成功提交异步通知请求...", orderVo.getTelephone()));
		}
		else
		{
			logger.error(String.format("渠道[%s]异步通知地址为空，用户[%s]无法提交异步通知...", orderVo.getPartnerId(), orderVo.getTelephone()));
		}
	}
}