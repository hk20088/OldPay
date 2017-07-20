package com.newspace.payplatform.unicomwo.SGIP;

import org.apache.log4j.Logger;

import com.huawei.smproxy.SGIPSMProxy;

public class ConnSms {
	
	private static final Logger logger = Logger.getLogger(ConnSms.class);

	//获取与SMS的连接
	public static void getConn()
	{
		// 连接登陆
		SGIPSMProxy sgipsmp = SGIPSMProxy.getInstance();// 这里
		try
		{
			/**
			 * connect表示向SMG登陆,登录名与密码分别是SMG向SP分配的用户名与密码,调用这个接口方法，向SMG发送Bind命令消息。
			 * 如果发送消息超时或通信异常则抛出异常，需要调用者捕获处理。
			 * true 代表登录成功
			 * false 代表登录失败
			 */
			boolean result = sgipsmp.connect(SGIPConstant.SGIP_LOGIN_NAME, SGIPConstant.SGIP_LOGIN_PWD); 

			if (result) 
			{
				logger.info(String.format("与SMS创建连接成功..."));
			}
			else 
			{
				logger.error("登陆SMG失败....");
			}
		}
		catch (Exception ex) 
		{
			logger.error("Bind时网络异常，断开Bind连接，异常信息为：",ex);
			try 
			{
				sgipsmp.close();
			} 
			catch (Exception e) 
			{
				logger.error("断开Bind连接时出现异常，异常信息：",e);
			}
		}
		
		logger.error(String.format("Bind类sgipsmp对象的Code：[%s]，连接状态是：[%s]", sgipsmp.hashCode(),sgipsmp.getConnState()));
	}
}
