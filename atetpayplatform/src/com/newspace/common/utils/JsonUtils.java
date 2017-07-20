package com.newspace.common.utils;

import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JsonUtils.java 
 * 描 述:  用于操作json的工具类，使用gson来进行json字符串和实体类对象之间的转换
 * 作 者:  liushuai
 * 历 史： 2014-4-17 创建
 */
public class JsonUtils
{
	

	private final static Gson gson = new Gson();


	/**
	 * 只操作带有@Exponse注解的字段 的 Gson对象
	 */
	private final static Gson exposeGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	
	/**
	 * 一般的Gson对象（disableHtmlEscaping()方法不转义特殊字符，比如&<>等；setPrettyPrinting()方法可以将json格式化输出）
	 * 这里只给联通沃邮箱使用此方法来封装json
	 */
	private final static Gson woGson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	/**
	 * 将json字符串转换成对象
	 * @param json json字符串
	 * @param clazz 实体类的Class对象
	 * @return T 实体类对象
	 */
	public static <T> T fromJson(String json, Class<T> clazz)
	{
		return gson.fromJson(json, clazz);
	}

	/**
	 * 从流中读取json并转换成对象
	 * @param reader 输入json的流
	 * @param clazz 实体类的Class对象
	 * @return T 实体类对象
	 */
	public static <T> T fromJson(Reader reader, Class<T> clazz)
	{
		return gson.fromJson(reader, clazz);
	}

	/**
	 * 将实体类对象转化成json字符串
	 * @param obj 实体类对象
	 * @return String json格式字符串
	 */
	public static String toJson(Object obj)
	{
		return gson.toJson(obj);
	}

	/**
	 * 将实体类对象转化成json字符串
	 * @param obj 实体类对象
	 * @param clazz 实体类的Class对象
	 * @return String json格式字符串
	 */
	public static String toJson(Object obj, Class<?> clazz)
	{
		return gson.toJson(obj, clazz);
	}

	/**
	 * 将实体类中转换成json字符串（仅转换带有@Exponse注解的字段）
	 * @param obj 实体对象
	 * @return String json格式字符串
	 */
	public static String toJsonWithExpose(Object obj)
	{
		return exposeGson.toJson(obj);
	}
	
	/**
	 * 将实体类转换成json字符串（格式化json串）
	 * @param obj
	 * @return String json格式字符串
	 */
	public static String toJsonWo(Object obj)
	{
		return woGson.toJson(obj);
	}
}