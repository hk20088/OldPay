package com.newspace.common.vo;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.newspace.common.utils.ArrayUtils;
import com.newspace.common.utils.StringUtils;

/**
 * BaseVo.java 
 * 描 述:  vo基类，系统中vo类均应继承此类
 * 作 者:  huqili
 * 历 史： 2014-5-12 修改propFromObject方法的实现，之前存在线程安全问题。
 */
public abstract class BaseVo implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 用于保存VO类属性信息的map，map的key为vo类的Class对象，value为vo类的属性描述数组
	 */
	private final static Map<Class<?>, PropertyDescriptor[]> PROPS_MAP = new HashMap<Class<?>, PropertyDescriptor[]>();

	/**
	 * 用于保存VO类属性名称的map，map的key为VO类的Class对象，value为VO类的属性名称集合
	 */
	private final static Map<Class<?>, Set<String>> FIELD_NAMES_SET = new HashMap<Class<?>, Set<String>>();

	/**
	 * 用于保存VO类属性名称和属性描述对象关系的map，map的key为VO类的Class对象，value为VO类中属性名称和属性描述对象的对应关系
	 */
	private final static Map<Class<?>, Map<String, PropertyDescriptor>> FIELD_NAME_PROP_MAP = new HashMap<Class<?>, Map<String, PropertyDescriptor>>();

	/**
	 * id，对应数据库表中的主键
	 */
	protected String id;

	/**
	 * 获取vo的id
	 * @return
	 */
	public String getId()
	{
		if (StringUtils.isNullOrEmpty(id))
		{
			return null;
		}
		return id;
	}

	/**
	 * 设置vo的id
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public int hashCode()
	{
		return (getClass().getCanonicalName() + id).hashCode();
	}

	@Override
	public String toString()
	{
		String log = logString();
		if (!StringUtils.isNullOrEmpty(log))
		{
			log = log.replaceAll("(<b>)|(</b>)", "");
			return log;
		}
		return super.toString();
	}

	public abstract String logString();

	/**
	 * 获取VO对象PropertyDescriptor数组
	 * @return VO类属性描述数组
	 */
	public PropertyDescriptor[] props()
	{
		Class<?> clazz = getClass();
		if (!BaseVo.PROPS_MAP.containsKey(clazz))
		{
			initPropInfo();
		}
		return BaseVo.PROPS_MAP.get(clazz);
	}

	/**
	 * 获取VO中属性名称的集合
	 * @return VO类属性名称集合
	 */
	public Set<String> propNames()
	{
		Class<?> clazz = getClass();
		if (!BaseVo.FIELD_NAMES_SET.containsKey(clazz))
		{
			initPropInfo();
		}
		return BaseVo.FIELD_NAMES_SET.get(clazz);
	}

	/**
	 * 获取VO中包含的数据的Map（属性名称为Key，属性值为value）
	 * @param excludeNames 不加入Map的属性名称集合
	 * @param upperKey 将Map的key转换为大写形式
	 * @return 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Map<String, Object> map(Set<String> excludeNames, boolean upperKey) throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		PropertyDescriptor[] props = props();
		boolean exclude = !ArrayUtils.isNullOrEmpty(excludeNames);
		if (!ArrayUtils.isNullOrEmpty(props))
		{
			Object[] params = new Object[] {};
			for (PropertyDescriptor prop : props)
			{
				String name = prop.getName();
				if (exclude && excludeNames.contains(name))
					continue;
				Method getter = prop.getReadMethod();
				if (getter == null)
					continue;
				Class<?> clazz = getter.getDeclaringClass();
				if ((clazz.equals(getClass()) || clazz.equals(getClass().getSuperclass()) || clazz.equals(BaseVo.class)))
				{
					name = upperKey ? name.toUpperCase() : name;
					map.put(name, getter.invoke(this, params));
				}
			}
		}
		return map;
	}

	/**
	 * 获取VO中包含的数据的Map（属性名称为Key，属性值为value）
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Map<String, Object> map() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		return map(null, Boolean.TRUE);
	}

	/**
	 * 获取VO中包含的数据的Map（属性名称为Key，属性值为value）
	 * @param 是否采用大写的key
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Map<String, Object> map(boolean upperKey) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		return map(null, upperKey);
	}

	/**
	 * 获取字符串形式的表示Vo各属性值的Map
	 * @param excludeNull 是否忽略null值
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> stringMap(boolean excludeNull) throws Exception
	{
		return stringMap(excludeNull, Boolean.FALSE);
	}

	/**
	 * 获取字符串形式的表示Vo各属性值的Map
	 * @param excludeNull 是否忽略null值
	 * @param upperKey 是否采用大写的key
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> stringMap(boolean excludeNull, boolean upperKey) throws Exception
	{
		return stringMap(new HashSet<String>(), excludeNull, upperKey);
	}

	/**
	 * 获取字符串形式的表示Vo各属性值的Map
	 * @param excludeNames 不加入map的字段名称
	 * @param excludeNull 是否忽略null值
	 * @param upperKey 是否采用大写的key
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> stringMap(String[] excludeNames, boolean excludeNull, boolean upperKey) throws Exception
	{
		Set<String> eset = null;
		if (!ArrayUtils.isNullOrEmpty(excludeNames))
		{
			eset = new HashSet<String>();
			Collections.addAll(eset, excludeNames);
		}
		return stringMap(eset, excludeNull, upperKey);
	}

	/**
	 * 获取字符串形式的表示Vo各属性值的Map
	 * @param excludeNames 不加入map的字段名称
	 * @param excludeNull 是否忽略null值
	 * @param upperKey 是否采用大写的key
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Map<String, String> stringMap(Set<String> excludeNames, boolean excludeNull, boolean upperKey) throws Exception
	{
		Map<String, Object> map = map(excludeNames, upperKey);
		Map<String, String> temp = null;
		if (!ArrayUtils.isNullOrEmpty(map))
		{
			temp = new HashMap<String, String>();
			for (Map.Entry<String, Object> entry : map.entrySet())
			{
				Object value = entry.getValue();
				if (value == null && excludeNull)
				{
					continue;
				}
				String str = null;
				if (value == null)
				{
					str = null;
				}
				else if (value instanceof Clob)
				{
					str = StringUtils.clobToString((Clob) value);
				}
				else if (value instanceof Date)
				{
					str = StringUtils.dateToString((Date) value);
				}
				else if (value instanceof Timestamp)
				{
					str = StringUtils.timestampToString((Timestamp) value);
				}
				else
				{
					str = value.toString();
				}
				temp.put(entry.getKey(), str);
			}
		}
		return temp;
	}

	private void initPropInfo()
	{
		try
		{
			Class<? extends BaseVo> clazz = getClass();
			BeanInfo info = Introspector.getBeanInfo(getClass());
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			if (!ArrayUtils.isNullOrEmpty(props))
			{
				Set<String> names = new HashSet<String>();
				Map<String, PropertyDescriptor> map = new HashMap<String, PropertyDescriptor>();
				for (PropertyDescriptor prop : props)
				{
					String name = prop.getName();
					if (!propFromObject(prop))
					{
						names.add(name);
						map.put(name, prop);
					}
				}
				BaseVo.FIELD_NAMES_SET.put(clazz, names);
				BaseVo.FIELD_NAME_PROP_MAP.put(clazz, map);
			}
			BaseVo.PROPS_MAP.put(clazz, props);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 判断属性是否是从Object继承而来的 
	 */
	private boolean propFromObject(PropertyDescriptor prop)
	{
		return ObjMethodHolder.OBJECT_METHODS.contains(prop.getReadMethod());
	}

	/**
	 * 静态内部类: OBJECT_METHODS属性包含了Object声明的Method集合。
	 * 采用这种方式防止propFromObject出现线程安全问题。
	 */
	private static class ObjMethodHolder
	{
		static final Set<Method> OBJECT_METHODS = new HashSet<Method>();

		static
		{
			OBJECT_METHODS.addAll(Arrays.asList(Object.class.getDeclaredMethods()));
		}
	}
}
