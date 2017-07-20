package com.newspace.common.proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
/**
 * 使用jvm动态代理机制创建代理
 * @author huqili
 * @since jdk1.6
 * @version 1.0
 */
public abstract class JVMProxy extends BaseProxy implements InvocationHandler
{
	@Override
	protected Object createProxy(Object target) throws Throwable 
	{
		Class<?> clazz = target.getClass();
		return Proxy.newProxyInstance(clazz.getClassLoader(), 
										clazz.getInterfaces(), 
										this);
	}
}
