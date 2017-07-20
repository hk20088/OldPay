package com.newspace.payplatform.unicomwo.notify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.newspace.common.coder.RSACoder;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.DateUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.param.NotifyCustomerContent;

/**
 * @description: 通知内容类，
 * @author Huqili
 * @date 2016年7月22日
 *
 */
public class NotifyChannelContent {

	/**
	 * 交易数据，json串
	 */
	private String transdata;

	/**
	 * 加密串
	 */
	private String sign;
	
	/**
	 * 渠道订单号
	 */
	private String orderId;
	
	/**
	 * 用户手机号
	 */
	private String mobile;
	
	/**
	 * 交易时间
	 */
	private String transTime;
	
	/**
	 * 交易结果，0：成功；1：失败
	 */
	private int result;
	
	private static final JLogger logger = LoggerUtils.getLogger(NotifyCustomerContent.class);
	
	/**
	 * 生成NotifyChannelContent的实例
	 */
	public static NotifyChannelContent getinstance(PaymentOrderVo orderVo)
	{
		NotifyChannelContent content = new NotifyChannelContent();
		content.setOrderId(orderVo.getCpOrderNo());
		content.setMobile(orderVo.getTelephone());
		content.setTransTime((DateUtils.DATETIMEFORMAT_NORMAL.get().format(orderVo.getUpdateTime())));
		content.setResult(orderVo.getState());
		return content;
	}
	
	
	/**
	 * 生成需要传输的内容字符串
	 * @throws UnsupportedEncodingException 
	 */
	public String generateContent() throws UnsupportedEncodingException
	{
		transdata = null;
		sign = null;
		transdata = JsonUtils.toJson(this);
		//需要对加密串进行一次编码
		sign = URLEncoder.encode(RSACoder.sign(transdata, PayUtils.RSA_ATET_PRIVATEKEY),"utf-8");
		StringBuilder sb = new StringBuilder();
		sb.append("transdata=").append(transdata);
		sb.append("&sign=").append(sign);
		logger.info(String.format("\r\n【通知wo+渠道的内容为：%s】", sb.toString()));
		return sb.toString();
	}


	public String getTransdata() {
		return transdata;
	}


	public void setTransdata(String transdata) {
		this.transdata = transdata;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getTransTime() {
		return transTime;
	}


	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}


	public int getResult() {
		return result;
	}


	public void setResult(int result) {
		this.result = result;
	}
	
	
}
