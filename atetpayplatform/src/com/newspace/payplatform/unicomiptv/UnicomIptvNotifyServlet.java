package com.newspace.payplatform.unicomiptv;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.IPUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.paynotify.NotifyContent;
import com.newspace.payplatform.paynotify.NotifyEntity;

/**
 * @description 联通IPTV异步通知接口
 * @author huqili
 * @since JDK1.6
 * @date 2016年7月4日
 *
 */
public class UnicomIptvNotifyServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private static final JLogger logger = LoggerUtils.getLogger(UnicomIptvNotifyServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("进入联通IPTV异步通知接口...");
		
		int returnCode = IptvReturnCode.UNICOM_IPTV_SUCCESS.getCode();
		IptvRespVo respVo = new IptvRespVo();
		PaymentOrderVo orderVo = new PaymentOrderVo();
		String reqStr = request.getQueryString();
		logger.info(String.format("接收到联通IPTV异步通知的内容是：%s", reqStr));
		
		if(reqStr != null)
		{
			try 
			{
				//将请求参数封装到map中
				Map<String, String> params = resolveToMap(reqStr);
				
				//校验sign是否有效
				if(IptvUtils.verify(params))
				{
					//根据订单号获取订单对象,并将请求参数填充到对象中,然后更新记录。
					orderVo = PayUtils.queryByOrderNo(params.get("TradeNo"),Boolean.TRUE);
					
					if(orderVo != null)
					{
						//将参数封装到Order对象中
						orderVo = fillOrderVo(orderVo, params);

						/**
						 * 支付结果状态定义： 0：支付成功。
						 * 1：订单处理中（最终结果以后台通知为准，如有必要由应用调用queryPayment做漏单查询）。
						 * 2：支付失败，余额不足。 3：支付失败，用户主动取消支付。 9：支付失败，其它错误。
						 * 
						 * 目前用的这个支付结果定义
						 * 支付结果： Completed:支付成功 Failed:失败 Canceled:取消支付 * Expired:处理中
						 */
						if(params.get("TradeStatus").equals("completed"))
						{
							orderVo.setState(PaymentOrderVo.PAY_STATE_SUCCESS);
							
							// 如果请求参数中包含了notifyUrL就使用请求参数中的，如果没有则从数据库读取
							String cpNotifyUrl = StringUtils.isNullOrEmpty(orderVo.getCpNotifyUrl()) ? PayUtils.getNotifyUrl(orderVo.getAppId()) : orderVo
									.getCpNotifyUrl();
							if (!StringUtils.isNullOrEmpty(cpNotifyUrl) && StringUtils.isHttpURL(cpNotifyUrl))
							{
								NotifyContent content = NotifyContent.getInstanceByOrder(orderVo);
								NotifyEntity entity = new NotifyEntity(cpNotifyUrl, content.generateContent());
								HttpAsyncConnExecutor.submitNotifyTask(entity);
							}
							else
							{
								logger.error(String.format("联通IPTV_无法通知[%s]", cpNotifyUrl));
							}
						}
						else
						{
							orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
						}
						
						orderVo.setClientIP(IPUtils.getRemoteAddress(request));
						
						//更新订单
						PayUtils.saveOrUpdateOrder(orderVo);
					}
					else
					{
						logger.error("联通IPTV异步通知接口，订单号无效...");
						returnCode = IptvReturnCode.UNICOM_IPTV_ORDERNO_INVALID.getCode();
					}
				}
				else
				{
					logger.error("联通IPTV异步通知，校验签名失败...");
					returnCode = IptvReturnCode.UNICOM_IPTV_SIGN_INVALID.getCode();
				}
				
			} 
			catch (Exception e) 
			{
				logger.error("联通IPTV异步通知接口出现异常...",e);
				returnCode = IptvReturnCode.UNICOM_IPTV_FAIL.getCode();
			}
		}
		else
		{
			logger.error("联通IPTV异步通知请求参数无效...");
			returnCode = IptvReturnCode.UNICOM_IPTV_PARAM_INVALID.getCode();
		}
		
		respVo.setErrorCode(String.valueOf(returnCode));
		respVo.setErrorDesc(ReturnCode.getDesc(returnCode));
		
		outputResult(response, respVo);
		
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
			//请求参数中会有Src=&link=等空值情况出现，为了避免ArrayIndexOutOfBoundsException异常为，封装Map时做个判断
			if(!str.endsWith("="))
			{
				String[] valueStrs = str.split("=");
				map.put(valueStrs[0], valueStrs[1]);
			}
			else
			{
				map.put(str.substring(0,str.indexOf("=")), "");
			}
		}
		
		return map;
	}

	/**
	 * 封装订单对象
	 * @param orderVo
	 * @param orderId
	 * @param channelId
	 * @return
	 */
	private PaymentOrderVo fillOrderVo(PaymentOrderVo orderVo,Map<String, String> map)
	{
		orderVo.setAmount( (int)(Double.parseDouble(map.get("Amount"))) * 100);  //金额
		//orderVo.setOrderNo(map.get("TradeNo"));  //这里将渠道商的订单号放到OrderNo中
		
		orderVo.setPaymentCreatedOrderNo(map.get("ConsumeStreamId"));
		orderVo.setPayPointName(map.get("Subject"));
		orderVo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM_IPTV);
		orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return orderVo;
	}
}
