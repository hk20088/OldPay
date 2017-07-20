package com.newspace.common.proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
/**
 * 使用cglib创建代理
 * @author huqili
 * @since jdk1.6 cglib-2.1.3
 * @version 1.0
 */
public abstract class CGLIBProxy extends BaseProxy implements MethodInterceptor
{
	@Override
	protected Object createProxy(Object target) throws Throwable 
	{
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
}
