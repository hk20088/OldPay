package com.newspace.payplatform;

import javax.servlet.ServletException;

import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.unicomwo.SGIP.ConnSms;

/**
 * StartupServlet.java 
 * 描 述:  初始化Servlet  
 * 作 者:  huqili
 * 历 史：  2016年8月10日 修改
 */
@SuppressWarnings("serial")
public class StartupServlet extends BaseServlet
{
	@Override
	public void init() throws ServletException
	{
		// 启动短信接收服务
//		ReceiveSmsServer.startServer();

		// 启动CP通知模块
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				HttpAsyncConnExecutor.execute();
			}
		}).start();
		
		//启动创建与SMS连接服务(打VAC的版本，不需要启动这个服务)
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ConnSms.getConn();
			}
		}).start();
		
		//启动SGIP接收短信服务
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				Mo.startSgipServer();
			}
		}).start();
		*/
		
	}
}