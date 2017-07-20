package com.newspace.payplatform.bindtelephone.vo;

import java.sql.Timestamp;

import com.newspace.common.vo.BaseVo;

/**
 * BindTelephoneVo.java 
 * 描 述:  绑定手机号操作的vo类。
 * 		   绑定规则：一个手机号可以绑定多个用户，一个用户在一个设备上只能绑定一个手机号。
 * 		   当设备上无用户的时候设备只能绑定一个手机号。
 * 	       也就是：由userId，productId，deviceCode三个字段确定一条记录。
 * 作 者:  liushuai
 * 历 史： 2014-7-15 创建
 */
public class BindTelephoneVo extends BaseVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 服务器下发的设备唯一Id
	 */
	private String atetId;

	/**
	 * 设备唯一标识
	 */
	private String productId;

	/**
	 * 设备编码
	 */
	private String deviceCode;

	/**
	 * 手机号
	 */
	private String telephone;

	/**
	 * 设备类型
	 */
	private Integer deviceType;

	/**
	 * 设备类型，1：电视
	 */
	public static final Integer DEVICETYPE_TV = 1;

	/**
	 * 设备类型，2：手机
	 */
	public static final Integer DEVICTTYPE_PHONE = 2;

	/**
	 * 绑定状态
	 */
	private Integer state;

	/**
	 * 绑定状态，0：成功
	 */
	public static final Integer STATE_SUCCESS = 0;

	/**
	 * 绑定状态，1：还未通过验证 
	 */
	public static final Integer STATE_NOT_VERIFY = 1;

	/**
	 * 验证码
	 */
	private String ext;

	/**
	 * 最后一次发送验证码的时间
	 */
	private Timestamp lastSendTime;

	private Integer sendCount;

	@Override
	public String logString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{ id : ").append(this.id);
		sb.append(", userId : ").append(this.userId);
		sb.append(", atetId : ").append(this.atetId);
		sb.append(", productId : ").append(this.productId);
		sb.append(", deviceCode ").append(this.deviceCode);
		sb.append(", tel : ").append(this.telephone).append(" }");
		return sb.toString();
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public String getAtetId() {
		return atetId;
	}

	public void setAtetId(String atetId) {
		this.atetId = atetId;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getDeviceCode()
	{
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode)
	{
		this.deviceCode = deviceCode;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public Integer getDeviceType()
	{
		return deviceType;
	}

	public void setDeviceType(Integer deviceType)
	{
		this.deviceType = deviceType;
	}

	public String getExt()
	{
		return ext;
	}

	public void setExt(String ext)
	{
		this.ext = ext;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	public Timestamp getLastSendTime()
	{
		return lastSendTime;
	}

	public void setLastSendTime(Timestamp lastSendTime)
	{
		this.lastSendTime = lastSendTime;
	}

	public Integer getSendCount()
	{
		return sendCount;
	}

	public void setSendCount(Integer sendCount)
	{
		this.sendCount = sendCount;
	}
}