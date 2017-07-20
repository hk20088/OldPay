package com.newspace.payplatform.wocheng;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.utils.IPUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.paynotify.NotifyContent;
import com.newspace.payplatform.paynotify.NotifyEntity;

/**
 * @description 接收沃橙支付结果通知的Servlet
 * @author huqili
 * @date 2016年9月28日
 *
 */
public class WochengNotifyServlet extends BaseServlet {

	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("进入沃橙异步通知接口...");
		PrintWriter out = response.getWriter();
		PaymentOrderVo orderVo = new PaymentOrderVo();
		
		long startTime = System.currentTimeMillis();
		Map<String, String> map = generateParamMap(request);
		
		if(WochengUtils.verify(map))
		{
			
			logger.info(String.format("[%s ms]内响应给沃橙...", System.currentTimeMillis() - startTime));
			//根据订单号获取订单对象,并将请求参数填充到对象中,然后更新记录。
			orderVo = PayUtils.queryByOrderNo(map.get("cpparam"),Boolean.TRUE);
			if(orderVo != null)
			{
				//响应沃橙
				out.write("ok");
				
				orderVo = fillOrder(map, orderVo);
				
				if(map.get("chargestatus").equals("0"))  //支付成功
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
						logger.error(String.format("沃橙渠道_无法通知[%s]", cpNotifyUrl));
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
				logger.error(String.format("沃橙根据订单号[%s]未查询出订单...", map.get("linkid")));
				out.write("parameter is invalid");
			}
		}
		else
		{
			logger.error("沃橙异步通知参数校验失败...");
			out.write("Signature verification failed");
		}
		
	}

	/**
	 * 封装订单信息
	 * @param map
	 * @param orderVo
	 * @return
	 */
	private static PaymentOrderVo fillOrder(Map<String, String> map,PaymentOrderVo orderVo)
	{
		
		orderVo.setAmount(Integer.parseInt(map.get("price")));  //金额
		orderVo.setPaymentCreatedOrderNo(map.get("linkid")); //沃橙的订单号
		
		orderVo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM_WOCHENG);
		orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return orderVo;
		
	}
}
