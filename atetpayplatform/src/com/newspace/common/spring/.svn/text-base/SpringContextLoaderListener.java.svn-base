package com.newspace.common.spring;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

/**
 * 用于加载spring context的监听器，在程序中的web启动时加载spring context
 * @author huqili
 * @since jdk1.6 spring3.0
 * @version 1.0
 */
public class SpringContextLoaderListener extends ContextLoaderListener
{
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		super.contextInitialized(event);
	}

	@Override
	protected ContextLoader createContextLoader()
	{
		return new SpringContextLoader();
	}
}
