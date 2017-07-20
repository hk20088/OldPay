package com.newspace.common.threadlocal;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;

import com.newspace.common.log.JLogger;

/**
 * ThreadLocalUtils.java 
 * 描 述:  ThreadLocal工具类,用于将一些信息保存到ThreadLocal中或从ThreadLocal中取出这些信息  
 * 作 者:  huqili
 * 历 史： 2014-4-18 修改
 */
public class ThreadLocalUtils
{
	/**
	 * 本地化设置
	 */
	private final static ThreadLocal<Locale> locale = new ThreadLocal<Locale>();

	/**
	 * HttpServletRequest对象
	 */
	private final static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();

	/**
	 * HttpServletResponse对象
	 */
	private final static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();

	/**
	 * HttpSession对象
	 */
	private final static ThreadLocal<HttpSession> session = new ThreadLocal<HttpSession>();

	/**
	 * 保存WebApplicationContext对象的ThreadLocal
	 */
	private final static ThreadLocal<WebApplicationContext> wctx = new ThreadLocal<WebApplicationContext>();

	/**
	 * 记录日志的对象
	 */
	private final static ThreadLocal<JLogger> logger = new ThreadLocal<JLogger>();

	/**
	 * 获取key指定的请求参数
	 * 
	 * @param name 参数名
	 * @return
	 */
	public static String getRequestParameter(String name)
	{
		if (getRequest() == null)
		{
			return "";
		}
		String param = getRequest().getParameter(name);
		if (param == null)
		{
			return "";
		}
		return param.trim();
	}

	/**
	 * 获取key指定的请求参数数组
	 * 
	 * @param name 参数名
	 * @return
	 */
	public static String[] getRequestParameters(String name)
	{
		if (getRequest() == null)
		{
			return null;
		}
		return getRequest().getParameterValues(name);
	}

	// 以下为getter setter方法。setter:把相关属性放置到ThreadLocal中 ；getter:从ThreadLocal中取出相关对象
	public static void setLocale(Locale locale)
	{
		ThreadLocalUtils.locale.set(locale);
	}

	public static Locale getLocale()
	{
		Locale l = locale.get();
		if (l == null)
		{
			l = Locale.CHINA;
		}
		return l;
	}

	public static void setRequest(HttpServletRequest request)
	{
		ThreadLocalUtils.request.set(request);
	}

	public static HttpServletRequest getRequest()
	{
		return request.get();
	}

	public static void setResponse(HttpServletResponse response)
	{
		ThreadLocalUtils.response.set(response);
	}

	public static HttpServletResponse getResponse()
	{
		return response.get();
	}

	public static void setSession(HttpSession session)
	{
		ThreadLocalUtils.session.set(session);
	}

	public static HttpSession getSession()
	{
		return session.get();
	}

	public static void setWebApplicationContext(WebApplicationContext context)
	{
		wctx.set(context);
	}

	public static WebApplicationContext getWebApplicationContext()
	{
		return wctx.get();
	}

	public static void setLogger(JLogger logger)
	{
		ThreadLocalUtils.logger.set(logger);
	}

	public static JLogger getLogger()
	{
		return logger.get();
	}
}
