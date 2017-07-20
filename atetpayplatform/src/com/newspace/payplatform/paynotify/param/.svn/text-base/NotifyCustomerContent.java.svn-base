package com.newspace.payplatform.paynotify.param;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.newspace.common.coder.RSACoder;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.DateUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.order.vo.PaymentOrderVo;

/**
 * {@link NotifyCustomerContent.java}
 * @description: 通知内容类，一般通知合作商，比如完美的时候用这个类（因为此类使用商只使用联通计费）。如果通知CP，则使用通用的NotifyContent.java类
 * @author Huqili
 * @date 2015年5月6日
 *
 */
public class NotifyCustomerContent {

	/**
	 * 交易数据，json串
	 */
	private String transdata;

	/**
	 * 加密串
	 */
	private String sign;
	
	/**
	 * 订单号（联通）
	 */
	private String linkid;
	
	/**
	 * 上行短信内容
	 */
	private String mo_msg;
	
	/**
	 * 用户手机号
	 */
	private String mobile;
	
	/**
	 * 短信接入号
	 */
	private String commandid;
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
	 * 生成NotifyCustomerContent的实例
	 */
	public static NotifyCustomerContent getinstance(PaymentOrderVo orderVo,String mo_msg)
	{
		NotifyCustomerContent content = new NotifyCustomerContent();
		content.setLinkid(orderVo.getOrderNo()); //将订单号返回给渠道
		content.setCommandid(orderVo.getCommandid());
		content.setMo_msg(mo_msg);
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
		logger.info(String.format("\r\n【通知合作合作商的内容为：%s】", sb.toString()));
		return sb.toString();
	}


	/**
	 * get/set方法
	 * @return
	 */
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

	public String getMo_msg() {
		return mo_msg;
	}

	public void setMo_msg(String moMsg) {
		mo_msg = moMsg;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCommandid() {
		return commandid;
	}

	public void setCommandid(String commandid) {
		this.commandid = commandid;
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

	public String getLinkid() {
		return linkid;
	}

	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	
	
}
