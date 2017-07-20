package com.newspace.payplatform.qmshortcut.entity;

import com.newspace.payplatform.qmshortcut.QMShortcutUtils;

/**
 * MerchantInfo.java 
 * 描 述:  商户信息实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-5 创建
 */
public class MerchantInfo
{
	/**
	 * 商户编号
	 */
	private String merchantId;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * tr1格式xml报文
	 */
	private String xml;

	/**
	 * url
	 */
	private String url;

	/**
	 * 证书密码
	 */
	private String certPass;

	/**
	 * 证书路径
	 */
	private String certPath;

	/**
	 * 超时时间
	 */
	private int timeOut;

	/**
	 * 地址域名
	 */
	private String domainName;

	/**
	 * 端口号
	 */
	private String sslPort;

	/**
	 * 获得商户信息对象
	 */
	public static MerchantInfo getInstance()
	{
		MerchantInfo merchant = new MerchantInfo();

		merchant.setMerchantId(QMShortcutUtils.MERCHANTINFO_ID);
		merchant.setCertPass(QMShortcutUtils.MERCHANTINFO_CERTPASSWORD);
		merchant.setCertPath(QMShortcutUtils.MERCHANTINFO_CERTFILENAME);
		merchant.setPassword(QMShortcutUtils.MERCHANTINFO_MERCHANTLOGINKEY);
		merchant.setTimeOut(QMShortcutUtils.MERCHANTINFO_TIMECOUT);
		merchant.setDomainName(QMShortcutUtils.MERCHANTINFO_DOMAINNAME);
		merchant.setSslPort(QMShortcutUtils.MERCHANTINFO_SSLPORT);

		return merchant;
	}

	/**
	 * 私有化构造函数
	 */
	private MerchantInfo()
	{
	}

	public String getUrl()
	{
		return this.domainName + ":" + this.sslPort + url;
	}

	public String getDomainName()
	{
		return domainName;
	}

	public void setDomainName(String domainName)
	{
		this.domainName = domainName;
	}

	public String getSslPort()
	{
		return sslPort;
	}

	public void setSslPort(String sslPort)
	{
		this.sslPort = sslPort;
	}

	public void setTimeOut(int timeOut)
	{
		this.timeOut = timeOut;
	}

	public String getMerchantId()
	{
		return merchantId;
	}

	public void setMerchantId(String merchantId)
	{
		this.merchantId = merchantId;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getXml()
	{
		return xml;
	}

	public void setXml(String xml)
	{
		this.xml = xml;
	}

	public String getCertPath()
	{
		return certPath;
	}

	public void setCertPath(String certPath)
	{
		this.certPath = certPath;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getCertPass()
	{
		return certPass;
	}

	public void setCertPass(String certPass)
	{
		this.certPass = certPass;
	}

	public int getTimeOut()
	{
		return timeOut;
	}
}