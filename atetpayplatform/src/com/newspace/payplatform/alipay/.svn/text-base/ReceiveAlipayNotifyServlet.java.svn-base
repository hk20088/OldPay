package com.newspace.payplatform.alipay;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import com.newspace.payplatform.warnnotify.CheckAccountTask;

/**
 * ReceiveAlipayNotifyServlet.java 
 * 描 述:  接收支付宝异步消息通知接口  
 * 作 者:  liushuai
 * 历 史： 2014-9-18 创建
 */
@SuppressWarnings("serial")
public class ReceiveAlipayNotifyServlet extends BaseServlet
{
	private static final JLogger logger = LoggerUtils.getLogger(ReceiveAlipayNotifyServlet.class);
	
	private static final ExecutorService pool = Executors.newCachedThreadPool();

	@SuppressWarnings("null")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		logger.info("\r\n【支付宝异步通知...】");

		Map<String, String> params = generateParamMap(request);

		String outTradeNo = params.get("out_trade_no"); // ATET平台订单号
		String tradeNo = params.get("trade_no"); // 支付宝交易号
		String tradeStatus = params.get("trade_status");
		String body = params.get("body");
		Map<String, String> map = new HashMap<String, String>();
		map = fillMap(body);  //将body里的参数放到map中。
		
		String alipayServerIP = IPUtils.getRemoteAddress(request);
		logger.info("支付宝异步通知服务器IP地址："+alipayServerIP);
		
		logger.info(String.format("\r\n【接收到支付宝异步通知：支付宝交易流水：%s，支付状态：%s，ATET平台订单号：%s，Body：%s】", tradeNo, tradeStatus, outTradeNo, body));

		PrintWriter out = response.getWriter();
		
		if(map != null && map.size() > 0){
			if (AlipayNotifyUtils.verify(params))
			{
				if ("TRADE_FINISHED".equalsIgnoreCase(tradeStatus) || "TRADE_SUCCESS".equalsIgnoreCase(tradeStatus))
				{
					PaymentOrderVo order = PayUtils.queryByOrderNo(outTradeNo,Boolean.TRUE);
					if (order == null)
					{
						order.setOrderNo(outTradeNo);
						order.setAppId(map.get("APPID"));
						order.setDeviceType(map.get("DEVICETYPE") == null ? null : Integer.parseInt(map.get("DEVICETYPE")));
						order.setChannelId(map.get("CHANNELID"));
						order.setDeviceId(map.get("DEVICEID"));
						order.setAtetId(map.get("ATETID"));
					}
					
					order.setPaymentCreatedOrderNo(tradeNo);
					order = generateOrderByBody(order,map);
					logger.info("【即将校验支付点】");
					if (PayUtils.verifyPayPointInfo(order) != ReturnCode.SUCCESS.getCode())
						logger.error(String.format("支付点信息不存在或金额不正确！cpId：%s；　appId：%s", map.get("CPID"),map.get("APPID")));
					logger.info("【即将保存订单】");
					int tmp = PayUtils.saveOrUpdateOrder(order);
					logger.info("【保存订单操作结果：" + tmp + "】");
					// 如果请求参数中包含了notifyUrL就使用请求参数中的，如果没有则从数据库读取
					String cpNotifyUrl = StringUtils.isNullOrEmpty(order.getCpNotifyUrl()) ? PayUtils.getNotifyUrl(order.getAppId())
							: order.getCpNotifyUrl();
					if (!StringUtils.isNullOrEmpty(cpNotifyUrl) && StringUtils.isHttpURL(cpNotifyUrl))
					{
						NotifyContent content = NotifyContent.getInstanceByOrder(order);
						NotifyEntity entity = new NotifyEntity(cpNotifyUrl, content.generateContent());
						HttpAsyncConnExecutor.submitNotifyTask(entity);
					}
					
					out.print("success");
					logger.info("已响应支付宝success");
					
					//操作成功后检查此订单是否异常，如果异常则下发预警短信。
					pool.execute(new CheckAccountTask(order));
				}else
				{
					logger.error(String.format("支付宝异步通知：支付状态是%s，并不是TRADE_FINISHED 或 TRADE_SUCCESS；cpId：%s ; appId：%s ; ordeNo：%s", tradeStatus,map.get("CPID"),map.get("APPID"),outTradeNo));
					out.print("fail");
				}
			}else
			{
				logger.error("接收支付宝系统通知验证签名失败！");
				out.print("fail");
			}
		}else{
			logger.error("支付宝异步通知_将body中的参数解析到Map中出现错误！");
			out.print("fail");
		}
		
	}

	/**
	 * 将支付宝通知的Body参数放到map中
	 */
	private Map<String,String> fillMap(String body){
		
		Map<String,String> map = new HashMap<String, String>();
		String[] params = body.split("&");
		for (String param : params)
		{
			String[] keyValue = param.split("=");
			map.put(keyValue[0].toUpperCase(), keyValue[1]);
			logger.info("【Tmp " + keyValue[0] + " ：" + keyValue[1] + "】");
		}
		return map;
	}
	
	/**
	 * 根据支付宝通知的Body参数生成订单对象
	 */
	private PaymentOrderVo generateOrderByBody(PaymentOrderVo vo, Map<String, String> map)
	{
		
		if(!vo.getAtetId().equals(map.get("ATETID")))
			vo.setIsScanPay(PaymentOrderVo.IS_SCAN_PAY);
		vo.setPartnerId(map.get("PARTNERID"));
		vo.setPackageName(map.get("PACKAGENAME"));
		vo.setDeviceCode(map.get("DEVICECODE"));
		vo.setPayPoint(map.get("PAYPOINT"));
		vo.setGameName(map.get("GAMENAME"));
		vo.setProductId(map.get("PRODUCTID"));
		vo.setAmount(map.get("AMOUNT") == null ? null : Integer.parseInt(map.get("AMOUNT")));
		vo.setCpId(map.get("CPID"));
		vo.setUserId(map.get("USERID") == null ? null : Integer.parseInt(map.get("USERID")));
		vo.setCounts(map.get("COUNTS") == null ? null : Integer.parseInt(map.get("COUNTS")));
		vo.setAmountType(PaymentOrderVo.AMOUNT_TYPE_RMB);
		vo.setPayType(PaymentOrderVo.PAYTYPE_ALIPAY);
		
		vo.setCpOrderNo(map.get("CPORDERNO"));
		vo.setCpNotifyUrl(map.get("CPNOTIFYURL"));
		vo.setCpPrivateInfo(map.get("CPPRIVATEINFO"));
		
		vo.setUpdateTime(new Timestamp(new Date().getTime()));
		vo.setState(PaymentOrderVo.PAY_STATE_SUCCESS);
		logger.info("【构造订单对象成功】");
		return vo;
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}
}
