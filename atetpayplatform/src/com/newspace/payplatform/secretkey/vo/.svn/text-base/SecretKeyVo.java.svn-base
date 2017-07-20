package com.newspace.payplatform.secretkey.vo;

import java.util.Date;

import com.newspace.common.vo.BaseVo;
import com.newspace.payplatform.base.JsonVo;

/**
 * SecretKeyVo.java 
 * 描 述:  密钥Vo类; 同时作为密钥接口的请求JsonVo类
 * 作 者:  liushuai
 * 历 史： 2014-4-22 创建
 */
public class SecretKeyVo extends BaseVo implements JsonVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * CP id
	 */
	private String cpId;

	/**
	 * CP Name
	 */
	private String cpName;

	/**
	 * 应用、游戏 id
	 */
	private String appId;
	
	/**
	 * 应用包名
	 */
	private String packageName;

	/**
	 * 私钥
	 */
	private String privateKey;

	/**
	 * 公钥
	 */
	private String publicKey;

	/**
	 * CP用于接收支付平台响应的地址
	 */
	private String cpNotifyUrl;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	@Override
	public String logString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{id : ").append(id);
		sb.append(", appId: ").append(appId);
		sb.append(", packageName: ").append(packageName);
		sb.append(", cpName: ").append(cpName).append(" }");
		return sb.toString();
	}

	public String getCpId()
	{
		return cpId;
	}

	public void setCpId(String cpId)
	{
		this.cpId = cpId;
	}

	public String getCpName()
	{
		return cpName;
	}

	public void setCpName(String cpName)
	{
		this.cpName = cpName;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPrivateKey()
	{
		return privateKey;
	}

	public void setPrivateKey(String privateKey)
	{
		this.privateKey = privateKey;
	}

	public String getPublicKey()
	{
		return publicKey;
	}

	public void setPublicKey(String publicKey)
	{
		this.publicKey = publicKey;
	}

	public String getCpNotifyUrl()
	{
		return cpNotifyUrl;
	}

	public void setCpNotifyUrl(String cpNotifyUrl)
	{
		this.cpNotifyUrl = cpNotifyUrl;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}