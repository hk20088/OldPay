package com.newspace.common.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * ArrayUtils.java 
 * 描 述:  用于数组、集合操作的工具类  
 * 作 者:  liushuai
 * 历 史： 2014-4-18 创建
 */
public final class ArrayUtils
{
	/**
	 * 检查集合是否为null或空
	 * @param collection 集合元素
	 * @return boolean true: 集合为null或者不包含元素
	 */
	public static boolean isNullOrEmpty(Collection<?> collection)
	{
		return collection == null || collection.size() == 0;
	}

	/**
	 * 检查Map是否为null或空
	 * @param map Map对象
	 * @return boolean true: Map为null或者不包含元素
	 */
	public static boolean isNullOrEmpty(Map<?, ?> map)
	{
		return map == null || map.size() == 0;
	}

	/**
	 * 检查数组是否为null或空
	 * @param array 数组
	 * @return boolean true: 数组为null或者不包含元素
	 */
	public static boolean isNullOrEmpty(Object[] array)
	{
		return array == null || array.length == 0;
	}

	/**
	 * 检查Map是否存在元素
	 * @param map
	 * @return
	 */
	public static boolean hasObject(Map<?, ?>map)
	{
		return map!=null&&map.size()>0;
	}
	
	/**
	 * 检查数组是否存在元素
	 * @param array
	 * @return
	 */
	public static boolean hasObject(Object[]array)
	{
		return array!=null&&array.length>0;
	}
	
	/**
	 * 检查数组中是否包含指定元素
	 * @param array 数组
	 * @param element 指定元素
	 * @return boolean true:包含, false:不包含
	 */
	public static boolean contains(Object[] array, Object element)
	{
		boolean flag = Boolean.FALSE;
		if (!ArrayUtils.isNullOrEmpty(array))
		{
			flag = Arrays.asList(array).contains(element);
		}
		return flag;
	}
}