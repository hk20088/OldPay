package com.newspace.payplatform.unicomwo.SGIP;

import static com.newspace.payplatform.ConstantData.WO_POWER;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.log4j.Logger;

import com.huawei.insa2.comm.sgip.message.SGIPDeliverMessage;
import com.huawei.insa2.comm.sgip.message.SGIPMessage;
import com.newspace.common.utils.HttpUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.utils.TwoTuple;
import com.newspace.payplatform.ConstantData;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.paynotify.NotifyEntity;
import com.newspace.payplatform.unicomwo.notify.NotifyChannelContent;
import com.newspace.payplatform.unicomwo.param.WoNotifyUrlEnum;
import com.newspace.payplatform.unicomwo.param.WoProduct;
import com.newspace.payplatform.unicomwo.param.WopayReq;
import com.newspace.payplatform.unicomwo.param.WopayResp;
import com.newspace.payplatform.unicomwo.utils.EncodeUtils;
import com.newspace.payplatform.unicomwo.utils.TraceUtils;

/**
 * @description 短信上午逻辑处理类
 * @author huqili
 * @since JDK1.7
 * @date 2016年8月9日
 *
 */
public class MoProcess {

	private static final Logger logger = Logger.getLogger(MoProcess.class);
	
	public static void ProcessRecvDeliverMsg(SGIPMessage msg) throws Exception
	{
		if (msg instanceof SGIPDeliverMessage) {
			// 收到用户发送的短信(上行)
			SGIPDeliverMessage deliverMsg = (SGIPDeliverMessage) msg;
			logger.info("接收到Deliver内容："+deliverMsg);
			String userPhone = deliverMsg.getUserNumber(); // 手机号码
			
			String msgContent = new String(deliverMsg.getMsgContent(),"GBK"); // 短信内容，如果内容是数字或英文，用GBK方式解码
//			String msgContent = new String(deliverMsg.getMsgContent(),"UnicodeBigUnmarked"); // 短信内容，如果内容是汉字，用UnicodeBigUnmarked方式解码
			logger.info(String.format("接收到用户[%s]上行的短信，短信内容[%s]",userPhone,msgContent));
			
			//如果用户回复的是数字，说明确认付费
			if(msgContent.matches("\\d*"))
			{
				boolean flag = true;
				PaymentOrderVo orderVo = null;
				
				//WO_POWER=false，说明渠道在请求计费的时候没有向联通发起支付，只是下发了短信
				if(!WO_POWER)  
				{
					logger.info(String.format("根据[%s]查询未支付的订单，准备扣费...", userPhone));
					//根据手机号，支付渠道，支付状态查询出最近的一条订单
					orderVo = PayUtils.queryByTel(userPhone, Boolean.TRUE);
					if(orderVo !=null)
					{
						logger.info(String.format("收到用户[%s]上行确认短信，请求计费...", userPhone));
						TwoTuple<Boolean, WopayResp> result = woPay(orderVo, userPhone);
						logger.info(String.format("用户[%s]计费结果[%s]", userPhone,result.first));
						
						if(result.first)  //说明支付成功
						{
							//更新订单状态
							orderVo.setState(PaymentOrderVo.PAY_STATE_SUCCESS);
							orderVo.setPaymentCreatedOrderNo(result.second.getTransaction_id());
							
							logger.info(String.format("用户[%s]计费成功，准备封装交易轨迹...", userPhone));
							//封装并记录交易轨迹 
							TraceUtils.insertTrace(orderVo.getCommandid(),userPhone, WoProduct.getSubjectMap.get(orderVo.getChannelShortCode()), WoProduct.getPriceMap.get(orderVo.getChannelShortCode()), result.second.getTransaction_id());
							
							logger.info(String.format("用户[%s]交易轨迹封装完毕，准备通知渠道...", userPhone));
							//计费成功，异步通知渠道
							corpNotify(orderVo);
							
						}
						else
						{
							//更新订单状态
							orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
							
							//支付失败，将标识改为false，不下发确认短信给用户
							flag = false;
						}
						
						//更新订单记录
						PayUtils.saveOrUpdateOrder(orderVo);
					}
					else
					{
						logger.error(String.format("根据手机号[%s]未查询出未支付的订单，无法扣费...", userPhone));
						flag = false; //不下发确认短信给用户
					}
					
				}
				
				//下发计费成功的确认短信给用户
				/*if(flag)
				{
					try 
					{
						logger.info(String.format("根据[%s]查询已支付成功的订单...", userPhone));
						orderVo = PayUtils.queryByTel(userPhone, Boolean.FALSE);
						if(orderVo != null)
						{
							logger.info(String.format("根据[%s]查询出订单,订单号为：[%s]", userPhone,orderVo.getOrderNo()));
							String subject = WoProduct.getSubjectMap.get(orderVo.getChannelShortCode());
							Integer price = WoProduct.getPriceMap.get(orderVo.getChannelShortCode());
							
							String message = TraceUtils.fillMsgContent_action4(subject, price);
							
							logger.info(String.format("准备下发计费确认计费信息给用户[%s]...", userPhone));
							//下发支付确认短信给用户
							int returnCode = Mt.sgipMt(userPhone, message);
							logger.info(String.format("用户[%s]计费确认短信的发送结果为：[%s]", userPhone,returnCode));
						}
						else
						{
							logger.error(String.format("根据手机号[%s]未查询出未支付的订单，无法下发计费确认短信...", userPhone));
						}
					}
					catch (Exception e) 
					{
						logger.error(String.format("给用户[%s}下发计费确认短信时出现异常...[%s]", userPhone,e));
					}
				}*/
				
			}
			else
			{
				logger.error(String.format("用户[%s]上行短信内容错误...短信内容：[%s]", userPhone,msgContent));
			}
			
		}
	}
	
	/**
	 * 请求联通进行计费
	 * @param orderVo
	 * @param userPhone
	 * @return
	 * @throws Exception
	 */
	private static TwoTuple<Boolean, WopayResp> woPay(PaymentOrderVo orderVo,String userPhone) throws Exception
	{
		boolean flag = false;
		
		String channelId = orderVo.getChannelShortCode();
		String phoneNum = EncodeUtils.encode(WoProduct.getKeyMap.get(channelId), userPhone);
		
		WopayReq info = new WopayReq();
		info.setPhoneNum(phoneNum);
		info.setChannelId(channelId);
		String json = JsonUtils.toJsonWithExpose(info);
		
		//请求联通沃邮箱进行计费
		HttpEntity entity = HttpUtils.postJson(ConstantData.UNICOM_WO_PAY_URL, json, "utf-8");
		
		BufferedReader reader = null;
		WopayResp respVo = new WopayResp();
		reader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
		String str = reader.readLine();
		logger.info(String.format("联通沃邮箱计费_订单号：%s 的计费结果是：%s", orderVo.getOrderNo(),str));
		
		//封装计费结果
		respVo = JsonUtils.fromJson(str, WopayResp.class);
		
		if(respVo.getRes_code().equals("0"))
		{
			flag = true;
		}
		
		return new TwoTuple<Boolean, WopayResp>(flag, respVo);
	}
	
	
	/**
	 * 异步通知沃+渠道
	 * @param orderVo
	 */
	private static void corpNotify(PaymentOrderVo orderVo)
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
