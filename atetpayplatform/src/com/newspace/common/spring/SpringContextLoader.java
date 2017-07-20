package com.newspace.common.spring;

import javax.servlet.ServletContext;

import org.apache.xbean.spring.context.XmlWebApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * spring context加载器
 * @author huqili
 * @since jdk1.6 spring3.0
 * @version 1.0
 */
public class SpringContextLoader extends ContextLoader
{
	@Override
	protected WebApplicationContext createWebApplicationContext(ServletContext servletContext, ApplicationContext parent)
			throws BeansException
	{
		Class<?> contextClass = determineContextClass(servletContext);
		if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass))
		{
			throw new ApplicationContextException("Custom context class [" + contextClass.getName()
					+ "] is not of type [" + ConfigurableWebApplicationContext.class.getName() + "]");
		}
		ConfigurableWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setParent(parent);
		wac.setServletContext(servletContext);
		wac.setConfigLocation(servletContext.getInitParameter("contextConfigLocation"));
		customizeContext(servletContext, wac);
		wac.refresh();
		return wac;
	}
}
