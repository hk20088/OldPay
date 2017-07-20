package com.newspace.payplatform.unicomwo;

import static com.newspace.payplatform.ConstantData.WO_POWER;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.IPUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.unicomwo.SGIP.MtTask;
import com.newspace.payplatform.unicomwo.param.WoProduct;
import com.newspace.payplatform.unicomwo.param.WopayReq;
import com.newspace.payplatform.unicomwo.param.WopayResp;
import com.newspace.payplatform.unicomwo.utils.TraceUtils;


public class WopayServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private static final JLogger logger = LoggerUtils.getLogger(WopayServlet.class);
	
	//定义一个线程池
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		logger.info("进入联通沃邮箱计费...");
		long startTime = System.currentTimeMillis();  //定义接收到请求的时间
		
		PaymentOrderVo orderVo = new PaymentOrderVo();
		WopayReq reqVo = new WopayReq();
		WopayResp respVo = new WopayResp();
		String reqStr = getStrFromRequest(request);
		int returnCode = padRequestVo(reqStr, reqVo);
		
		String code = TraceUtils.getcode();  //下发给用户的三位验证码
		
		
		try 
		{
			if(reqVo.isLegal() && returnCode == ReturnCode.SUCCESS.getCode())
			{
				
				//封装订单信息
				orderVo = fillOrderVo(orderVo, reqVo, code);
				orderVo.setClientIP(IPUtils.getRemoteAddress(request));
				
				//封装下行短信内容
				String subject = WoProduct.getSubjectMap.get(reqVo.getChannelId());
				Integer price = WoProduct.getPriceMap.get(reqVo.getChannelId());
				
				String msgContent = TraceUtils.fillMsgContent_action2(code,subject, price);
				
				//将下发给用户的三位验证码返回给渠道
				respVo.setMessCode(code);
				
					
				if(WO_POWER) //如果为true，则代表直接扣费
				{
					try 
					{
						logger.info(String.format("用户[%s]准备下发扣费确认短信...", reqVo.getPhone()));
										
						//下发扣费验证码给用户
						Future<String>  future =  pool.submit(new MtTask(reqVo.getPhone(),msgContent));
						
						if(future.get().equals("0"))
						{
							long sendSmsTime = System.currentTimeMillis();
							logger.info(String.format("用户[%s]确认短信下行成功，短信耗时[%s]ms", reqVo.getPhone(),(sendSmsTime - startTime)));
							
							/*
							 * 
							 * 
							 * 给用户下行短信验证码短信后， 将短信验证码响应给渠道， 用户在应用内输入验证码，
							 * 渠道发起confim.do接口，ATET平台进行校验，扣费等操作   - 胡起立 2017年4月20日 改 
							 * 
							 * 
							 //请求联通沃邮箱进行计费
							HttpEntity entity = HttpUtils.postJson(ConstantData.UNICOM_WO_PAY_URL, json, "utf-8");
							reader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
							String str = reader.readLine();
							
							long payTime = System.currentTimeMillis(); //定义扣费时间点
							logger.info(String.format("用户[%s],订单号为[%s],计费结果是：[%s],计费耗时[%s]ms",reqVo.getPhone(), reqVo.getOrderId(),str,(payTime - sendSmsTime)));
							
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
								if(!StringUtils.isExistNullOrEmpty(WO_NOTIFY_URL) && StringUtils.isHttpURL(WO_NOTIFY_URL))
								{
									logger.info("开始通知wo+渠道...");
									NotifyChannelContent content = NotifyChannelContent.getinstance(orderVo);
									NotifyEntity notifyEntity = new NotifyEntity(WO_NOTIFY_URL, content.generateContent());
									HttpAsyncConnExecutor.submitNotifyTask(notifyEntity);
									
									logger.info(String.format("用户[%s]成功提交异步通知请求...", reqVo.getPhone()));
								}
								else
								{
									logger.error(String.format("异步通知地址为空，无法提交异步通知...用户[%s]", reqVo.getPhone()));
								}
								
								logger.info(String.format("用户[%s]异步通知成功，开始封装交易轨迹...", reqVo.getPhone()));
								//封装并记录交易轨迹 
								TraceUtils.insertTrace(code,reqVo.getPhone(), WoProduct.getSubjectMap.get(reqVo.getChannelId()), WoProduct.getPriceMap.get(reqVo.getChannelId()), respVo.getTransaction_id());
								
								logger.info(String.format("用户[%s]交易轨迹封装完毕，开始下行扣费成功通知短信...", reqVo.getPhone()));
								
								//计费成功后，下发扣费成功短信给用户 2016年9月12日  添加
								String pay_msgContent = TraceUtils.fillMsgContent_action4(subject, price);
								pool.submit(new MtTask(reqVo.getPhone(), pay_msgContent));
								
							}
							else
							{
								logger.error(String.format("用户[%s]计费失败...", reqVo.getPhone()));
								//计费失败
								orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
							}
							
							*/
							
							//更新订单
							PayUtils.saveOrUpdateOrder(orderVo);
							logger.info(String.format("用户[%s]订单更新成功", reqVo.getPhone()));
							
						}
						else
						{
							logger.error(String.format("用户[%s]下发扣费确认短信失败，短信耗时[%s]ms，停止计费...", reqVo.getPhone(),(System.currentTimeMillis() - startTime)));
							returnCode = ReturnCode.UNICOM_WO_STOP_PAY.getCode();
						}
						
					} 
					catch (Exception e) 
					{
						logger.error("联通沃邮箱下发短信时出现异常...",e);
					}
					
				}
				else  //说明要等到用户短信回复二次确认时再扣费
				{
					orderVo.setState(PaymentOrderVo.PAY_STATE_NONPROCESS);  //这里只下发了短信给用户，并未支付，所以设置订单状态为 2
					
					//封装响应参数
					respVo.setRes_code(String.valueOf(returnCode));
					respVo.setRes_desc(ReturnCode.getDesc(returnCode));
					
					//响应结果给渠道
					outputResult(response,JsonUtils.toJsonWithExpose(respVo));
					
					//使用线程池下行确认短信  2016年8月5日
					pool.submit(new MtTask(reqVo.getPhone(), msgContent));
				}
			}
			else
			{
				returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
			}
			
		} 
		catch (Exception e) 
		{
			logger.error(String.format("联通沃邮箱用户[%s]计费出现异常...", reqVo.getPhone()));
			returnCode = ReturnCode.ERROR.getCode();
		}
		
		respVo.setRes_code(String.valueOf(returnCode));
		respVo.setRes_desc(ReturnCode.getDesc(returnCode));
		
		//响应结果给渠道
		String resultJson = JsonUtils.toJsonWithExpose(respVo);
		logger.info(String.format("用户[%s]响应计费结果给渠道，响应内容：[%s],响应总耗时[%s]ms ...", reqVo.getPhone(),resultJson,(System.currentTimeMillis() - startTime)));
		outputResult(response,resultJson);
	}
	
	private PaymentOrderVo fillOrderVo(PaymentOrderVo orderVo, WopayReq reqVo,String code)
	{
		orderVo.setState(PaymentOrderVo.PAY_STATE_NONPROCESS); //这时只是给用户发了一条验证码短信，并没有计费
		orderVo.setAmount(WoProduct.getPriceMap.get(reqVo.getChannelId()) * 100);  //这里将金额转换成  分
		orderVo.setOrderNo(reqVo.getOrderId());  //这里将渠道商的订单号放到OrderNo中
		orderVo.setCpOrderNo(reqVo.getOrderId());  //这里将渠道商的订单号放到cpOrderNo中
		orderVo.setTelephone(reqVo.getPhone());  //保存用户手机号
		orderVo.setChannelShortCode(reqVo.getChannelId());  //这里将业务代码保存在channelShortCode中，以便收到用户确认付费的短信时，直接拿来扣费
		orderVo.setCommandid(code);   //这里将下发给用户的短信验证码保存到commandid中，以便用户上行扣费时，封装交易轨迹时使用
		orderVo.setPartnerId(reqVo.getCorpId());  //企业ID，放到 partnerId 中用来区分渠道
		orderVo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM_WO);
		orderVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return orderVo;
	}

}
