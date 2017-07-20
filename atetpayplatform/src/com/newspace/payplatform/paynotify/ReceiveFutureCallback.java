package com.newspace.payplatform.paynotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;

/**
 * ReceiveFutureCallback.java 
 * 描 述:  接收响应的Future  
 * 作 者:  huqili
 * 历 史： 2014-9-10 创建
 */
public class ReceiveFutureCallback implements FutureCallback<HttpResponse>
{
	private NotifyEntity entity;

	private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(20);

	private static final JLogger logger = LoggerUtils.getLogger(ReceiveFutureCallback.class);

	public ReceiveFutureCallback(NotifyEntity entity)
	{
		this.entity = entity;
	}

	@Override
	public void completed(HttpResponse response)
	{
		logger.info(String.format("\r\n【正常接收到NotfiyUrl ：%s 响应， Completed】", entity.getNotifyUrl()));
		
		System.out.println(String.format("\r\n【正常接收到NotfiyUrl ：%s 响应， Completed】", entity.getNotifyUrl()));
		BufferedReader reader = null;
		try
		{
			HttpEntity httpEntity = response.getEntity();
			reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
			String str = reader.readLine();
			logger.info(String.format("\r\n【接收到NotfiyUrl ：%s，响应信息：%s】", entity.getNotifyUrl(), str, entity.getSendTime()));
			
			System.out.println(String.format("\r\n【接收到NotfiyUrl ：%s，响应信息：%s】", entity.getNotifyUrl(), str, entity.getSendTime()));
			// 接收到success响应 或 已经发送8次，则不再发送
			if (!"success".equalsIgnoreCase(str) && entity.getSendTime() < 3)
			{
				entity.setSendTime(entity.getSendTime() + 1);
				int delay = entity.getDelayTime();
				pool.schedule(new Runnable()
				{
					@Override
					public void run()
					{
						HttpAsyncConnExecutor.submitNotifyTask(entity);
					}
				}, delay, TimeUnit.SECONDS);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			entity.setSendTime(entity.getSendTime() + 1);
		}
		finally
		{
			if (reader != null)
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
				}
		}
	}

	@Override
	public void failed(Exception exception)
	{
		logger.error(String.format("\r\n【接收到NotfiyUrl ：%s 响应失败， failed，出现异常：%s】", entity.getNotifyUrl(),exception));
		
		System.out.println(String.format("\r\n【接收到NotfiyUrl ：%s 响应失败， failed，出现异常: %s】", entity.getNotifyUrl(),exception));
		// 已经发送8次，则不再发送
		if (entity.getSendTime() >= 3)
			return;
		entity.setSendTime(entity.getSendTime() + 1);
		int delay = entity.getDelayTime();
		pool.schedule(new Runnable() 
		{
			@Override
			public void run()
			{
				HttpAsyncConnExecutor.submitNotifyTask(entity);
			}
		}, delay, TimeUnit.SECONDS);
	}

	@Override
	public void cancelled()
	{
		logger.info(String.format("\r\n【接收到NotfiyUrl ：%s 响应取消， cancelled】", entity.getNotifyUrl()));
	}
}
