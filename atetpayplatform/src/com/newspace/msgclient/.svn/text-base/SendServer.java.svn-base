package com.newspace.msgclient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * SendServer.java 
 * 描 述:  发送服务  
 * 作 者:  liushuai
 * 历 史： 2014-9-19 创建
 */
public class SendServer
{
	private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

	public static void main(String[] args)
	{
		// 按照固定频率， 每隔5秒执行一次查询任务。
		pool.scheduleAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				pool.execute(new QueryMsgTask());
			}
		}, 10, 5, TimeUnit.SECONDS);
	}
}
