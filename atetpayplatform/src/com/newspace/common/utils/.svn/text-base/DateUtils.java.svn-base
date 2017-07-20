package com.newspace.common.utils;

import java.text.SimpleDateFormat;

/**
 * DateUtils.java 
 * 描 述:  日期工具类  
 * 作 者:  liushuai
 * 历 史： 2014-7-3 创建
 */
public final class DateUtils
{

	/**
	 * 正常日期格式的SimpleDateFormat：yyyy-MM-dd HH:mm:ss
	 */
	public static ThreadLocal<SimpleDateFormat> DATETIMEFORMAT_NORMAL = new ThreadLocal<SimpleDateFormat>()
	{
		@Override
		protected SimpleDateFormat initialValue()
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	/**
	 * 没有间隙格式的SimpleDateFormat：yyyyMMddHHmmss
	 */
	public static ThreadLocal<SimpleDateFormat> DATETIMEFORMAT_NOGAP = new ThreadLocal<SimpleDateFormat>()
	{
		@Override
		protected SimpleDateFormat initialValue()
		{
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};
}
