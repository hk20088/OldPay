package com.newspace.common.exception;

/**
 * Bo异常类，bo类中方法抛出的异常
 * @author huqili
 * @since jdk1.6
 * @version 1.0
 */
public class BoException extends BaseException
{
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 * @param errorCode 错误代码
	 */
	public BoException(int errorCode)
	{
		super(errorCode);
	}

	/**
	 * 构造方法
	 * @param errorCode 错误代码
	 * @param cause 底层异常
	 */
	public BoException(int errorCode, Throwable cause)
	{
		super(errorCode, cause);
	}

	/**
	 * 构造方法
	 * @param errorCode 错误代码
	 * @param cause 底层异常
	 * @para params 在错误消息中需要替换的格式化参数
	 */
	public BoException(int errorCode, String[] params, Throwable cause)
	{
		super(errorCode, params, cause);
	}

	/**
	 * 构造方法
	 * @param errorCode 错误代码
	 * @para params 在错误消息中需要替换的格式化参数
	 */
	public BoException(int errorCode, String[] params)
	{
		super(errorCode, params);
	}

	/**
	 * 构造方法
	 * @param DaoException
	 * @para
	 */
	public BoException(DaoException cause)
	{
		super(cause.getErrorCode(), cause);
	}

}