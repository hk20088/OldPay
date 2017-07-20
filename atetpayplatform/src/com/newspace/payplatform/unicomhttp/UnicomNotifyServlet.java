package com.newspace.payplatform.unicomhttp;

import static com.newspace.payplatform.ConstantData.PERFECT_NOTIFY_URL;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.paynotify.NotifyContent;
import com.newspace.payplatform.paynotify.NotifyEntity;
import com.newspace.payplatform.paynotify.param.NotifyCustomerContent;
import com.newspace.payplatform.unicomhttp.param.VacNotifyUrlEnum;

/**
 * {@link UnicomNotifyServlet.java}
 * 
 * @description: 接收联通异步通知结果接口。调用联通计费接口后，联通会将计费结果异步通知到ATET支付服务器。
 * @author huqili
 * @date 2015年4月14日
 * 
 */
public class UnicomNotifyServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	private static final JLogger logger = LoggerUtils.getLogger(UnicomNotifyServlet.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("\r\n进入联通异步通知接口...");
		
		long startTime = System.currentTimeMillis();
		
		PaymentOrderVo orderVo = new PaymentOrderVo();
		String reqStr = getStrFromRequest(request);
		logger.info(String.format("\r\n接收到联通平台异步通知内容是:%s", reqStr));

		if(reqStr != null){
			//响应结果给联通服务器。
			outputResult(response, "resultCode=0");
			
			long endTime = System.currentTimeMillis();
			logger.info(String.format("Accept success in %s ms!", endTime - startTime));
			
			try {
				//从request中读取参数并放到map中
				reqStr = URLDecoder.decode(reqStr,"utf-8");
				Map<String, String> params = resolveToMap(reqStr);
				
				//从map中取出各个参数的值 。
				String orderNo = params.get("Linkid");    //ATET支付平台订单号
				int responecode =Integer.parseInt(params.get("code")); //计费结果
				String telephone = params.get("mobile");  //用户手机号
				String commandid = params.get("commandid");  //短信接入号
				String mo_msg = params.get("mo_msg");  //短信内容
				
				/**
				 * 短信指令说明：DC金额*产品短代码+渠道代码*透传内容
				 * 1、上行短信内容格式为DC1*202010100800000，其中最后六位800000为渠道短代码（完美的用这种来区分，完美短代码800010）
				 * 2、上行短信内容格式为DC1*202010100800000*1000，用最后的透传内容1000来区分渠道。中间的渠道短代码固定为800000
				 */
				
				String[] contents = mo_msg.split("\\*");  //将短信内容用*分割成数组。
				int len = contents.length;  //短信数组长度
				String channelShortCode = contents[1].substring(9); //渠道短代码
				
				
				/**
				 * 根据渠道短代码来判断此消费记录的来源。
				 * 如果是800000则表示是ATET游戏平台消费，再根据ATET的逻辑来处理，处理完成后通知CP
				 * 如果是800010则表示是完美游戏中心消费，直接将记录插入到数据库,通知完美即可。
				 * 备注：此版本不考虑手机钱包支付，即所有ATET联通通道的支付全部当作TV支付。
				 */
				if("800000".equals(channelShortCode)) 
				{
					/**
					 * ATET游戏平台分级管理
					 * 1、如果contents长度为2，则说明是用DC1*202010100800000 这种格式来上行的扣费短信，属于ATET平台产生的计费
					 * 2、如果contents长度为3，则说明是用DC1*202010100800000*1000这种格式来上行的扣费短信，属于ATET二级代理商产生的计费。
					 */
					if(len == 2)
					{
						//根据订单号查询订单，更新支付状态及时间 。
						orderVo = PayUtils.queryByOrderNo(orderNo,Boolean.FALSE);
						if(orderVo != null){
							logger.info(String.format("联通异步通知_根据订单号%s成功查询出相应的订单记录！", orderNo));
							orderVo.setState(responecode);
							orderVo.setTelephone(telephone);
							orderVo.setCommandid(commandid);
							orderVo.setChannelShortCode(channelShortCode);
							orderVo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM);
							orderVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
							
							// 如果请求参数中包含了notifyUrL就使用请求参数中的，如果没有则从数据库读取
							String cpNotifyUrl = StringUtils.isNullOrEmpty(orderVo.getCpNotifyUrl()) ? PayUtils.getNotifyUrl(orderVo.getAppId()) : orderVo
									.getCpNotifyUrl();
							if (!StringUtils.isNullOrEmpty(cpNotifyUrl) && StringUtils.isHttpURL(cpNotifyUrl))
							{
								NotifyContent content = NotifyContent.getInstanceByOrder(orderVo);
								NotifyEntity entity = new NotifyEntity(cpNotifyUrl, content.generateContent());
								HttpAsyncConnExecutor.submitNotifyTask(entity);
							}
							
							//更新订单记录。
							logger.info("\r\n联通异步通知_准备更新ATET订单表...");
							PayUtils.saveOrUpdateOrder(orderVo);
							logger.info("\r\n联通异步通知_ATET订单表更新成功...");
							
							
						}else{
							logger.error(String.format("联通异步通知_根据订单号%s未查询出相应的订单记录！", orderNo));
						}
					}
					else if(len == 3)
					{
						String shortCode = contents[2].substring(0,4);
						orderVo = fillOrder(orderVo, orderNo, responecode, telephone, commandid, shortCode, contents);

						//异步通知渠道
						corpNotify(orderVo, mo_msg);
						
						//更新订单记录。
						logger.info("\r\n联通异步通知_准备更新订单表...");
						PayUtils.saveOrUpdateOrder(orderVo);
						logger.info("\r\n联通异步通知_订单表更新成功...");
						
					}
				}
				else if("800010".equals(channelShortCode))
				{
					logger.info("此笔为完美游戏中心交易订单...");
					
					orderVo = fillOrder(orderVo, orderNo, responecode, telephone, commandid, channelShortCode, contents);
					
					//通知完美
					if(!StringUtils.isExistNullOrEmpty(PERFECT_NOTIFY_URL) && StringUtils.isHttpURL(PERFECT_NOTIFY_URL))
					{
						logger.info("开始通知完美...");
						NotifyCustomerContent content = NotifyCustomerContent.getinstance(orderVo, mo_msg);
						NotifyEntity entity = new NotifyEntity(PERFECT_NOTIFY_URL, content.generateContent());
						HttpAsyncConnExecutor.submitNotifyTask(entity);
					}
					
					//更新订单记录。
					logger.info("\r\n联通异步通知_准备更新完美订单表...");
					PayUtils.saveOrUpdateOrder(orderVo);
					logger.info("\r\n联通异步通知_完美订单表更新成功...");
				}
				
			}catch (UnsupportedEncodingException e) {
				logger.error("UnicomNotifyServlet:联通异步通知_加密编码出错！",e);
			} catch (Exception e) {
				logger.error("UnicomNotifyServlet:联通异步通知出错！",e);
			}
		}else{
			//响应结果给联通服务器。
			outputResult(response, "resultCode=1");
		}
	}
	
	/**
	 * 异步通知渠道
	 * @param orderVo
	 * @param mo_msg
	 */
	private void corpNotify(PaymentOrderVo orderVo,String mo_msg)
	{
		String corpId = orderVo.getChannelShortCode();  //渠道ID
		String telePhone = orderVo.getTelephone();     //用户手机号
		String notify_url = VacNotifyUrlEnum.getUrlMap.get(corpId);  //根据渠道获取异步通知地址
		
		if(!StringUtils.isExistNullOrEmpty(notify_url) && StringUtils.isHttpURL(notify_url))
		{
			logger.info(String.format("开始通知渠道[%s]...", corpId));
			
			NotifyCustomerContent content = NotifyCustomerContent.getinstance(orderVo, mo_msg);
			NotifyEntity entity;
			try
			{
				entity = new NotifyEntity(notify_url, content.generateContent());
				HttpAsyncConnExecutor.submitNotifyTask(entity);
			} 
			catch (UnsupportedEncodingException e)
			{
				logger.error(String.format("通知VAC渠道时出现异常，渠道[%s]，用户[%s],异常信息：[%s]", corpId,telePhone,e));
			}
			logger.info(String.format("渠道[%s],用户[%s]成功提交异步通知请求...",corpId, telePhone));
		}
		else
		{
			logger.error(String.format("渠道[%s]异步通知地址为空，用户[%s]无法提交异步通知...", corpId,telePhone));
		}
	}
	
	/**
	 * 将请求参数封装到Map中，请求参数的格式是：key1=value1&key2=value2...
	 * @param requestStr
	 * @return
	 */
	private Map<String, String> resolveToMap(String requestStr)
	{
		Map<String, String> map = new HashMap<String, String>();
		
		String[] reqStrs = requestStr.split("&");
		for(String str : reqStrs)
		{
			//请求参数中会有Src=&link=等空值情况出现，为了避免ArrayIndexOutOfBoundsException异常为，封装Map时过滤掉空值
			if(!str.endsWith("="))
			{
				String[] valueStrs = str.split("=");
				map.put(valueStrs[0], valueStrs[1]);
			}
		}
		
		return map;
	}
	
	/**
	 * 封装由合作商产生的联通订单
	 * @param orderVo
	 * @param orderNo
	 * @param responecode
	 * @param telephone
	 * @param commandid
	 * @param channelShortCode
	 * @param contents
	 * @return
	 */
	private PaymentOrderVo fillOrder(PaymentOrderVo orderVo,String orderNo,int responecode,String telephone,String commandid,String channelShortCode,String[] contents)
	{
		orderVo.setOrderNo(orderNo);  //手机支付时，联通会将短信网关产生的linkid同步过来 ,当作此记录的订单号。
		orderVo.setPaymentCreatedOrderNo(orderNo);  
		orderVo.setState(responecode);
		orderVo.setTelephone(telephone);
		orderVo.setCommandid(commandid);
		orderVo.setChannelShortCode(channelShortCode);
		orderVo.setAmount(Integer.parseInt(contents[0].substring(2)) * 100); //数据库存储的金额单位为 分。
		orderVo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM);
		orderVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		return orderVo;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//联通异步通知方法换成POST请求，弃用get请求
		this.doPost(request, response);
		
		/*logger.info("\r\n进入联通异步通知接口...");
		
		long startTime = System.currentTimeMillis();
		
		PrintWriter out = response.getWriter();
		PaymentOrderVo orderVo = new PaymentOrderVo();
		String reqContent = request.getQueryString();
		logger.info(String.format("\r\n接收到联通平台异步通知内容是:%s", reqContent));

		if(reqContent != null){
			//响应结果给联通服务器。
			out.write("resultCode=0");
			
			long endTime = System.currentTimeMillis();
			logger.info(String.format("Accept success in %s ms!", endTime - startTime));
			
			try {
				//从request中读取参数并放到map中
				Map<String, String> params = generateParamMap(request);
				
				//从map中取出各个参数的值 。
				String orderNo = params.get("linkid");    //ATET支付平台订单号
				int responecode =Integer.parseInt(params.get("code")); //计费结果
				String telephone = params.get("mobile");  //用户手机号
				String commandid = params.get("commandid");  //短信接入号
				String mo_msg = params.get("mo_msg");  //短信内容
				
				*//**
				 * 短信指令说明：DC金额*产品短代码+渠道代码*透传内容
				 * 1、上行短信内容格式为DC1*202010100800000，其中最后六位800000为渠道短代码（完美的用这种来区分，完美短代码800010）
				 * 2、上行短信内容格式为DC1*202010100800000*1000，用最后的透传内容1000来区分渠道。中间的渠道短代码固定为800000
				 *//*
				
				String[] contents = mo_msg.split("\\*");  //将短信内容用*分割成数组。
				int len = contents.length;  //短信数组长度
				String channelShortCode = contents[1].substring(9); //渠道短代码
				
				
				*//**
				 * 根据渠道短代码来判断此消费记录的来源。
				 * 如果是800000则表示是ATET游戏平台消费，再根据ATET的逻辑来处理，处理完成后通知CP
				 * 如果是800010则表示是完美游戏中心消费，直接将记录插入到数据库,通知完美即可。
				 * 备注：此版本不考虑手机钱包支付，即所有ATET联通通道的支付全部当作TV支付。
				 *//*
				if("800000".equals(channelShortCode)) 
				{
					*//**
					 * ATET游戏平台分级管理
					 * 1、如果contents长度为2，则说明是用DC1*202010100800000 这种格式来上行的扣费短信，属于ATET平台产生的计费
					 * 2、如果contents长度为3，则说明是用DC1*202010100800000*1000这种格式来上行的扣费短信，属于ATET二级代理商产生的计费。
					 *//*
					if(len == 2)
					{
						//根据订单号查询订单，更新支付状态及时间 。
						orderVo = PayUtils.queryByOrderNo(orderNo,Boolean.FALSE);
						if(orderVo != null){
							logger.info(String.format("联通异步通知_根据订单号%s成功查询出相应的订单记录！", orderNo));
							orderVo.setState(responecode);
							orderVo.setTelephone(telephone);
							orderVo.setCommandid(commandid);
							orderVo.setChannelShortCode(channelShortCode);
							orderVo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM);
							orderVo.setCreateTime(new Timestamp(System.currentTimeMillis()));
							orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
							
							// 如果请求参数中包含了notifyUrL就使用请求参数中的，如果没有则从数据库读取
							String cpNotifyUrl = StringUtils.isNullOrEmpty(orderVo.getCpNotifyUrl()) ? PayUtils.getNotifyUrl(orderVo.getAppId()) : orderVo
									.getCpNotifyUrl();
							if (!StringUtils.isNullOrEmpty(cpNotifyUrl) && StringUtils.isHttpURL(cpNotifyUrl))
							{
								NotifyContent content = NotifyContent.getInstanceByOrder(orderVo);
								NotifyEntity entity = new NotifyEntity(cpNotifyUrl, content.generateContent());
								HttpAsyncConnExecutor.submitNotifyTask(entity);
							}
							
							//更新订单记录。
							logger.info("\r\n联通异步通知_准备更新ATET订单表...");
							PayUtils.saveOrUpdateOrder(orderVo);
							logger.info("\r\n联通异步通知_ATET订单表更新成功...");
							
							
						}else{
							logger.error(String.format("联通异步通知_根据订单号%s未查询出相应的订单记录！", orderNo));
						}
					}
					else if(len == 3)
					{
						String shortCode = contents[2].substring(0,4);
						if(shortCode.equals("2000"))
						{
							logger.info("此笔为代理商交易订单...");
							
							orderVo = fillOrder(orderVo, orderNo, responecode, telephone, commandid, shortCode, contents);
							
							if(!StringUtils.isExistNullOrEmpty(NOSS_NOTIFY_URL) && StringUtils.isHttpURL(NOSS_NOTIFY_URL))
							{
								logger.info("开始通知支付代理商...");
								NotifyCustomerContent content = NotifyCustomerContent.getinstance(orderVo, mo_msg);
								NotifyEntity entity = new NotifyEntity(NOSS_NOTIFY_URL, content.generateContent());
								HttpAsyncConnExecutor.submitNotifyTask(entity);
							}
							
							//更新订单记录。
							logger.info("\r\n联通异步通知_准备更新订单表...");
							PayUtils.saveOrUpdateOrder(orderVo);
							logger.info("\r\n联通异步通知_订单表更新成功...");
						}
					}
					
					
				}
				else if("800010".equals(channelShortCode))
				{
					logger.info("此笔为完美游戏中心交易订单...");
					
					orderVo = fillOrder(orderVo, orderNo, responecode, telephone, commandid, channelShortCode, contents);
					
					//通知完美
					if(!StringUtils.isExistNullOrEmpty(PERFECT_NOTIFY_URL) && StringUtils.isHttpURL(PERFECT_NOTIFY_URL))
					{
						logger.info("开始通知完美...");
						NotifyCustomerContent content = NotifyCustomerContent.getinstance(orderVo, mo_msg);
						NotifyEntity entity = new NotifyEntity(PERFECT_NOTIFY_URL, content.generateContent());
						HttpAsyncConnExecutor.submitNotifyTask(entity);
					}
					
					//更新订单记录。
					logger.info("\r\n联通异步通知_准备更新完美订单表...");
					PayUtils.saveOrUpdateOrder(orderVo);
					logger.info("\r\n联通异步通知_完美订单表更新成功...");
				}
				
			}catch (UnsupportedEncodingException e) {
				logger.error("UnicomNotifyServlet:联通异步通知_加密编码出错！",e);
			} catch (Exception e) {
				logger.error("UnicomNotifyServlet:联通异步通知出错！",e);
			}
		}else{
			//响应结果给联通服务器。
			out.write("resultCode=1");
		}*/
		
		
	}
	
	

}
