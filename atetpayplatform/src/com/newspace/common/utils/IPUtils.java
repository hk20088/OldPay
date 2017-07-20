package com.newspace.common.utils;

import javax.servlet.http.HttpServletRequest;


/**
 * IPUtils.java
 * @description 工具类，用于获取客户端IP 
 * @author huqili
 * @date 2014年11月19日
 *
 */
public class IPUtils {

	/**
	 * 获取客户端的IP
	 */
	public static String getRemoteAddress(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		if(ip != null)
			ip = ip.split(",")[0];
		return ip;
	}
}
