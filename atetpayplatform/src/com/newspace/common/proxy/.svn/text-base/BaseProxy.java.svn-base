package com.newspace.common.proxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * 代理基类
 * @author huqili
 * @since jdk1.6
 * @version 1.0
 */
public abstract class BaseProxy 
{
	/**
	 * 被代理的对象
	 */
	protected Object target;
	/**
	 * 代理对象
	 */
	protected Object proxy;
	/**
	 * 保存代理配置属性的map
	 */
	protected Map<String, Object>propMap;
	/**
	 * 创建一个代理对象
	 * @param target 被代理的对象
	 * @return 代理对象
	 * @throws Throwable
	 */
	protected abstract Object createProxy(Object target)throws Throwable;
	/**
	 * 将对象和代理对象绑定
	 * @param obj 需要代理的目标对象
	 * @return
	 * @throws Exception
	 */
	public Object bind(Object target)throws Exception
	{
		return bind(target, null);
	}
	/**
	 * 将对象和代理对象绑定
	 * @param obj 需要代理的目标对象
	 * @param props 代理对象可以访问的属性
	 * @return
	 * @throws Throwable
	 */
	public Object bind(Object target,Map<String,Object>props)throws Exception
	{
		if(target==null)
		{
			throw new Exception("Unable to create a proxy to null");
		}
		this.target = target;
		this.propMap = props;
		try 
		{
			this.proxy = createProxy(target);
		} 
		catch (Throwable e) 
		{
			throw new Exception("bind proxy fail.");
		}
		return this.proxy;
	}
	/**
	 * 同步执行代理对象的方法
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	protected Object synchronizedInvoke(Method method, Object[] args)throws Throwable
	{
		synchronized (proxy)
		{
			Object result = method.invoke(target, args);
			return result;
		}
	}
	/**
	 * 获取绑定的各种属性
	 * @param propKey 属性名称
	 * @return
	 */
	protected Object getProp(String propKey)
	{
		Object value = null;
		if(propMap!=null)
		{
			value = propMap.get(propKey);
		}
		return value;
	}
	/**
	 * 设置绑定的属性
	 * @param propKey 属性名称
	 * @param propValue 属性值
	 */
	protected void setProp(String propKey,Object propValue)
	{
		if(propMap==null)
		{
			propMap = new HashMap<String, Object>();
		}
		propMap.put(propKey, propValue);
	}
}
