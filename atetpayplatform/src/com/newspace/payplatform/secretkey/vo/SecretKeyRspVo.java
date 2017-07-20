package com.newspace.payplatform.secretkey.vo;

import com.newspace.payplatform.base.BaseResponseJsonVo;

/**
 * SecretKeyRspVo.java 
 * 描 述:  密钥接口响应Vo类
 * 作 者:  liushuai
 * 历 史： 2014-4-26 创建
 */
public class SecretKeyRspVo extends BaseResponseJsonVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 作为响应内容的appKey(私钥)
	 */
	private String appKey;

	/**
	 * 默认构造方法
	 */
	public SecretKeyRspVo()
	{
	}

	/**
	 * 带参构造方法
	 */
	public SecretKeyRspVo(int code, String appKey)
	{
		super(code);
		this.appKey = appKey;
	}

	public String getAppKey()
	{
		return appKey;
	}

	public void setAppKey(String appKey)
	{
		this.appKey = appKey;
	}
}