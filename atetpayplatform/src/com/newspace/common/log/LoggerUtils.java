package com.newspace.common.log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.log4j.xml.DOMConfigurator;

import com.newspace.common.proxy.CGLIBProxy;

/**
 * LoggerUtils.java 
 * 描 述:  log4j工具类，用于获取{@link com.newspace.common.log.JLogger}类的实例  
 * 作 者:  huqili
 * 历 史： 2014-4-18 创建
 */
public class LoggerUtils
{
	/**
	 * {@link com.newspace.common.log.JLogger}对象的缓存
	 */
	private static final Map<String, JLogger> cache = new HashMap<String, JLogger>();
	/**
	 * 是否已经初始化
	 */
	private static boolean inited = Boolean.FALSE;

	/**
	 * 获取记录日志的JLogger对象
	 * @param name 类名
	 * @return JLogger对象
	 */
	public synchronized static JLogger getLogger(String name)
	{
		JLogger logger = null;
		try
		{
			logger = cache.get(name);
			if (logger == null)
			{
				logger = getLoggerProxy(new JLogger(name));
				cache.put(name, logger);
			}
			return logger;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return logger;
	}

	/**
	 * 获取记录日志的JLogger对象
	 * @param clazz Class对象
	 * @return JLogger对象
	 */
	public static JLogger getLogger(Class<?> clazz)
	{
		return getLogger(clazz.getName());
	}

	/**
	 * 初始化log配置
	 */
	public synchronized static void initLogConfig(String configFilePath)
	{
		if (!inited)
		{
			DOMConfigurator.configure(configFilePath);
			inited = Boolean.TRUE;
		}
	}

	/**
	 * 获取JLogger对象的代理
	 * @param logger JLogger对象
	 * @return JLogger对象的代理
	 * @throws Exception
	 */
	private static JLogger getLoggerProxy(final JLogger logger) throws Exception
	{
		JLogger proxy = (JLogger) new CGLIBProxy()
		{

			public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable
			{
				if (args.length == 1 && "error".equals(method.getName()) && (args[0] instanceof Throwable))
				{
					Throwable e = (Throwable) args[0];
					logger.logger.error(e.getMessage(), (Throwable) args[0]);
					return null;
				}
				return method.invoke(logger.logger, args);
			}
		}.bind(logger);
		return proxy;
	}
}