package com.newspace.common.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.newspace.common.utils.ArrayUtils;

/**
 * SpringBeanUtils.java 
 * 描 述:  Spring工具类，用于获取Spring配置的Bean  
 * 作 者:  xieyongui
 * 历 史： 2014-4-18 修改initContext的实现，去掉同步 modify by liushuai
 */
public final class SpringBeanUtils
{
	/**
	 * 保存bean的map。
	 * map的key为bean的Class对象，value为bean本身
	 */
	private final static Map<Class<?>, Map<String, Object>> CLAZZ_MAP = new HashMap<Class<?>, Map<String, Object>>();

	/**
	 * spring context对象
	 */
	private static ApplicationContext context;

	/**
	 * 获取配置在spring中指定名称或ID的bean
	 * @param id bean名称或ID
	 * @return T bean对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String id)
	{
		initContext();
		T bean = null;
		if (context.containsBean(id))
		{
			bean = (T) context.getBean(id);
		}
		return bean;
	}

	/**
	 * 获取配置在spring中指定Class的bean
	 * @param clazz 指定Class对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> getBeans(Class<T> clazz)
	{
		Map<String, T> beans = null;
		synchronized (CLAZZ_MAP)
		{
			if (CLAZZ_MAP.containsKey(clazz))
			{
				beans = (Map<String, T>) CLAZZ_MAP.get(clazz);
			}
			else
			{
				initContext();
				String[] names = context.getBeanNamesForType(clazz);
				Map<String, Object> map = null;
				if (!ArrayUtils.isNullOrEmpty(names))
				{
					map = new HashMap<String, Object>();
					for (String name : names)
					{
						T bean = (T) getBean(name);
						map.put(name, bean);
					}
				}
				beans = (Map<String, T>) map;
				CLAZZ_MAP.put(clazz, map);
			}
		}
		return beans;
	}

	/**
	 * 获取指定Class的bean。如果有多个bean的Class为指定的Class，则随机取出一个
	 * @param clazz 指定的Class对象
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz)
	{
		T bean = null;
		Map<String, T> map = getBeans(clazz);
		if (!ArrayUtils.isNullOrEmpty(map))
		{
			bean = map.get(map.keySet().iterator().next());
		}
		return bean;
	}

	/**
	 * 初始化ApplicationContext
	 */
	public static void initContext()
	{
		context = ContextHolder.ctx;
	}

	/**
	 * lazy initialization holder class方式
	 * 使用这种方式可以使initContext()不被同步，不增加访问成本。
	 */
	private static class ContextHolder
	{
		static final ApplicationContext ctx = SpringContextLoader.getCurrentWebApplicationContext();
	}
}