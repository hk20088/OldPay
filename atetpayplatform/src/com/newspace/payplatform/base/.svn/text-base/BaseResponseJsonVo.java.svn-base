package com.newspace.payplatform.base;


/**
 * BaseResponseJsonVo.java 
 * 描 述:  响应JsonVo对象的基类。对于响应消息只包含状态码的可以直接使用该类。
 * 		   响应消息还包含其他消息，可以继承自该类。
 * 作 者:  liushuai
 * 历 史： 2014-4-22 创建
 */
public class BaseResponseJsonVo implements JsonVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 状态码
	 */
	private int code;

	public BaseResponseJsonVo()
	{
	}

	public BaseResponseJsonVo(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}
}