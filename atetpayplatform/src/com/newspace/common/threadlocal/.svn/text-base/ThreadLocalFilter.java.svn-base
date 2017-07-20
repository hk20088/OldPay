package com.newspace.common.threadlocal;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;


public class ThreadLocalFilter implements Filter
{
	private ServletContext context;

	@Override
	public void destroy()
	{

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		try
		{
			HttpServletRequest req = (HttpServletRequest) request;
			ThreadLocalUtils.setRequest(req);
			ThreadLocalUtils.setResponse((HttpServletResponse) response);
			ThreadLocalUtils.setSession(req.getSession());
			ThreadLocalUtils.setWebApplicationContext(WebApplicationContextUtils
					.getRequiredWebApplicationContext(context));
			chain.doFilter(request, response);
		}
		catch (Exception e)
		{
			throw new ServletException(e);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		this.context = config.getServletContext();
	}

	private String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
