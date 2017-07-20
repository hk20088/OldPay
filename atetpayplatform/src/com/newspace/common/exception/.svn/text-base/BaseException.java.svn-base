package com.newspace.common.exception;

import org.springframework.dao.DataAccessException;

import com.newspace.common.utils.PropertiesUtils;

/**
 * BaseException.java 
 * 描 述:  异常基类  
 * 作 者:  huqili
 * 历 史： 2014-4-18 修改
 */
public class BaseException extends DataAccessException
{
	private static final long serialVersionUID = 1L;

	/**
	 * 错误代码
	 */
	private int errorCode;

	/**
	 * 构造方法
	 * @param errorCode 错误代码
	 */
	protected BaseException(int errorCode)
	{
		super(PropertiesUtils.getExceptionMsg(errorCode));
		this.errorCode = errorCode;
	}

	/**
	 * 构造方法
	 * @param errorCode 错误代码
	 * @param cause 底层异常
	 */
	protected BaseException(int errorCode, Throwable cause)
	{
		super(PropertiesUtils.getExceptionMsg(errorCode), cause);
		this.errorCode = errorCode;
	}

	/**
	 * 构造方法
	 * @param errorCode 错误代码
	 * @param params 在错误消息中需要替换的格式化参数
	 * @param cause 底层异常
	 */
	protected BaseException(int errorCode, String[] params, Throwable cause)
	{
		super(PropertiesUtils.getExceptionMsg(errorCode, params), cause);
		this.errorCode = errorCode;
	}

	/**
	 * 构造方法
	 * @param errorCode 错误代码
	 * @param params 在错误消息中需要替换的格式化参数
	 */
	protected BaseException(int errorCode, String[] params)
	{
		super(PropertiesUtils.getExceptionMsg(errorCode, params));
		this.errorCode = errorCode;
	}

	/**
	 * 获取异常的错误代码
	 */
	public int getErrorCode()
	{
		return this.errorCode;
	}
}
