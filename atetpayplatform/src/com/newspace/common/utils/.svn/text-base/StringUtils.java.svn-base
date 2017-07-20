package com.newspace.common.utils;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * StringUtils.java 
 * @description:  字符串操作工具类，封装了一些诸如字符串非空转换、非空判断、时间日期和字符串之间转换等方法  
 * @author huqili
 * @date： 2014年11月21日
 */
public final class StringUtils
{
	/**
	 * 默认的日期格式转换对象 
	 */
	private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 读取Clob字段
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String clobToString(Clob clob) throws Exception
	{
		StringBuffer buffer = null;
		if (clob != null)
		{
			buffer = new StringBuffer();
			Reader reader = null;
			try
			{
				reader = clob.getCharacterStream();
				char[] bytes = new char[1024];
				int i = 0;
				while ((i = reader.read(bytes)) != -1)
				{
					buffer.append(bytes, 0, i);
				}
			}
			finally
			{
				if (reader != null)
				{
					reader.close();
				}
			}
		}
		return buffer == null ? null : buffer.toString();
	}

	/**
	 * 判断字符串是否为null或空
	 * @param str 字符串
	 * @param isTrim 是否去除字符串首尾空格
	 * @return boolean true 为null或空字符串
	 */
	public static boolean isNullOrEmpty(String str, boolean isTrim)
	{
		if (str == null || str.length() == 0)
		{
			return true;
		}
		if (isTrim)
		{
			str = str.trim();
			if (str.length() == 0)
				return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为null或空
	 * @param str 字符串
	 * @return boolean ture：为null或空字符串
	 */
	public static boolean isNullOrEmpty(String str)
	{
		return isNullOrEmpty(str, Boolean.TRUE);
	}

	/**
	 * 判断传入的参数中是否存在空值
	 * @param strs 可变参数
	 * @return 是否存在空值
	 */
	public static boolean isExistNullOrEmpty(String... strs)
	{
		for (String str : strs)
			if (isNullOrEmpty(str, Boolean.TRUE))
				return true;
		return false;
	}

	/**
	 * 将Date转换为字符串
	 * @param date 需要转换的Date对象
	 * @param formatString 时间展示格式
	 * @return 时间日期字符串
	 * @throws Exception
	 */
	public static String dateToString(Date date, String formatString) throws Exception
	{
		SimpleDateFormat format = null;
		if (isNullOrEmpty(formatString))
			format = DEFAULT_DATE_FORMAT;
		else
			format = new SimpleDateFormat(formatString);
		return format.format(date);
	}

	/**
	 * 将Date转换为字符串(默认使用“yyyy-MM-dd HH:mm:ss”的格式展示时间)
	 * @param date 需转换的日期
	 * @return 转换后的时间日期字符串
	 * @throws Exception
	 */
	public static String dateToString(Date date) throws Exception
	{
		return dateToString(date, null);
	}

	/**
	 * 将Timestamp对象转换成字符串
	 * @param timestamp 需Timestamp对象
	 * @param formatString 时间展示格式
	 * @return 转换后的时间日期字符串
	 * @throws Exception
	 */
	public static String timestampToString(Timestamp timestamp, String formatString) throws Exception
	{
		Date date = new Date(timestamp.getTime());
		return dateToString(date, formatString);
	}

	/**
	 * 将Timestamp对象转换成字符串(默认使用“yyyy-MM-dd HH:mm:ss”的格式展示时间)
	 * @param timestamp
	 * @return 转换后的时间日期字符串
	 * @throws Exception
	 */
	public static String timestampToString(Timestamp timestamp) throws Exception
	{
		return timestampToString(timestamp, null);
	}

	/**
	 * 如果字符串为空则将其转换为指定值
	 * @param str 需要转换的字符串
	 * @param replacement 如果字符串为空则返回replacement指定的值
	 * @return 转换后的字符串
	 */
	public static String parseNull(String str, String replacement)
	{
		if (isNullOrEmpty(str, Boolean.TRUE))
		{
			str = replacement;
		}
		return str;
	}

	/**
	 * 如果字符串为空则将其转换为空串
	 * @param str 需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String parseNull(String str)
	{
		return parseNull(str, "");
	}

	/**
	 * 使用指定的字符将字符串补足为指定的长度
	 * @param source 需要补足长度的字符串
	 * @param len 补足之后的字符串长度
	 * @param paddingChar 用于补足长度的字符 
	 * @param before 是否在前面补足 true：将字符加到原字符串前面 false：将字符加到原字符串后面
	 * @return 补足长度之后的字符串
	 */
	public static String paddingString(String source, int len, char paddingChar, boolean before)
	{
		if (isNullOrEmpty(source))
		{
			source = "";
		}
		int paddingSize = len - source.length();
		if (paddingSize > 0)
		{
			for (int i = 0; i < paddingSize; i++)
			{
				if (before)
				{
					source = paddingChar + source;
				}
				else
				{
					source += paddingChar;
				}
			}
		}
		return source;
	}

	/**
	 * 查找指定字符再字符串中出现的索引位置
	 * @param str 被查找的字符串
	 * @param s 要查找的字符串
	 * @param index 第几次出现的位置
	 * @return 返回要查找字符的索引
	 */
	public static int indexStrOf(String str, String s, int index)
	{

		int strIndex = 0;
		int firstIndex = 0;

		for (int i = 1; i <= index; i++)
		{

			firstIndex = str.indexOf(s, firstIndex + 1);
			if (firstIndex == -1)
			{
				strIndex = str.length();
				break;
			}
			if (i == index)
			{
				strIndex = firstIndex;
				break;
			}
		}

		return strIndex;
	}

	/**
	 * 截取参数字符 串
	 * @param object 方法参数
	 * @return
	 */
	public static String splitParams(Object object)
	{
		if (object == null)
		{
			return null;
		}
		String str = object.toString();
		if (str.length() > 50)
		{
			str = str.substring(0, 20) + "..." + str.substring(str.length() - 10, str.length());
		}
		return str;
	}
	
	/**
	 * 按照固定长度截取字符串，并将截取的字符串按顺序放到Map中
	 * @param str　需要截取的字符串
	 * @param length　固定长度
	 * @return
	 */
	public static synchronized Map<Integer, String> splitStr(String str,int length){
		Map<Integer, String> map = new HashMap<Integer, String>();
		int strLength = str.length();
		int n = strLength % length == 0 ? strLength / length : strLength / length + 1;
		String s = null;
		int t = 0;
		for(int i=0;i<n;i++){
			t = str.length() > length ? length : str.length();
			s = str.substring(0, t);
			str = str.substring(t);
			map.put(i, s);
		}
		return map;
	}

	/**
	 * 模糊查询时，将参数中的特殊字符进行转换
	 * @param value
	 * @return String
	 */
	public static String covertSpecialCharWhenUseLike(String value)
	{
		if ("".equals(value))
			return value;
		return "%" + value.replaceAll("%", "/%").replaceAll("_", "/_") + "%";
	}

	/**
	 * 是否是Http URL地址 
	 */
	public static boolean isHttpURL(String url)
	{
		if (url.trim().startsWith("http://") || url.startsWith("https://"))
			return true;
		return false;
	}

	/***
	* 匹配字符串是否是日期格式(yyyy-MM-dd)格式
	* @param dateStr 
	* @return true 表示字符串匹配成功
	*/
	public static Boolean isDateStr(String dateStr)
	{
		if (dateStr == null)
		{
			return false;
		}
		else
		{
			String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(dateStr);
			boolean dateFlag = m.matches();
			return dateFlag;
		}
	}
}