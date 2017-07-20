package com.newspace.payplatform.paynotify.param;

import java.io.UnsupportedEncodingException;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.DateUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.payplatform.order.vo.PaymentOrderVo;

/**
 * {@link NotifyContentJson.java}
 * @description: 通知内容类，此类通知内容不做加密。 通知类似于老虎地图这样的合作商。
 * @author Huqili
 * @date 2017年3月24日
 *
 */
public class NotifyContentJson {


	
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
	
	private static final JLogger logger = LoggerUtils.getLogger(NotifyContentJson.class);
	
	/**
	 * 生成NotifyCustomerContent的实例
	 */
	public static NotifyContentJson getinstance(PaymentOrderVo orderVo,String mo_msg)
	{
		NotifyContentJson content = new NotifyContentJson();
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
	public String generateContent()
	{
		String transdata = JsonUtils.toJson(this);
		logger.info(String.format("\r\n【通知合作合作商的内容为：%s】", transdata));
		return transdata;
	}


	/**
	 * get/set方法
	 * @return
	 */

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
