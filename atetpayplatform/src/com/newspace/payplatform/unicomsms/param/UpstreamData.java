package com.newspace.payplatform.unicomsms.param;

/**
 * UpstreamParam.java 
 * 描 述:  封装上行短信内容参数中的公共参数，作为父类。  
 * 作 者:  liushuai
 * 历 史： 2014-7-15 创建
 */
public class UpstreamData
{
	/**
	 * 消息类型
	 */
	private Integer msgType;

	/**
	 * 消息类型：1 强联网。
	 * 强联网时：将短信内容相关数据保存到t_payplatform_bindTel.用来完成绑定手机号。
	 */
	public static final int MSGTYPE_STRONG_RELATE = 0;

	/**
	 * 消息类型：2 弱联网。
	 * 弱联网时：拿到短信参数之后，进行VAC支付操作。并记录订单信息。
	 */
	public static final int MSGTYPE_WEAK_RELATE = 1;

	/**
	 * 消息类型：3 弱联网支付时，用来获取订单号。
	 */
	public static final int MSGTYPE_WEAK_GETORDERNO = 2;

	/**
	 * 应用id
	 */
	private String appId;

	public Integer getMsgType()
	{
		return msgType;
	}

	public void setMsgType(Integer msgType)
	{
		this.msgType = msgType;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}
}