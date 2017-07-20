package com.newspace.payplatform.paynotify;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.impl.nio.conn.PoolingClientAsyncConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.conn.ClientAsyncConnectionManager;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.payplatform.ConstantData;
import com.newspace.payplatform.paynotify.param.NotifyCustomerContent;

/**
 * HttpAsyncConnExecutor.java 
 * 描 述:  异步通知执行器
 * 作 者:  huqili
 * 历 史： 2014-11-12 修改
 */
public class HttpAsyncConnExecutor
{
	private static volatile BlockingQueue<NotifyEntity> taskQueue = new LinkedBlockingQueue<NotifyEntity>();

	private static final JLogger logger = LoggerUtils.getLogger(HttpAsyncConnExecutor.class);

	private static final ScheduledExecutorService pool = Executors.newScheduledThreadPool(20);

	/**
	 *  提交通知任务
	 */
	public static void submitNotifyTask(final NotifyEntity entity)
	{
		logger.info(String.format("当前队列大小：%s", taskQueue.size()));
		if (!taskQueue.offer(entity)) // 添加失败，则三秒后尝试再添加一次
		{
			logger.info(String.format("【提交通知任务失败，3秒后再重新提交一次，NotifyUrl：%s,】", entity.getNotifyUrl()));
			pool.schedule(new Runnable()
			{
				@Override
				public void run()
				{
					if (taskQueue.offer(entity))
						logger.info(String.format("\r\n【提交通知任务成功，第%s次通知，NotifyUrl：%s,通知内容：%s 】",entity.getSendTime(), entity.getNotifyUrl(),entity.getNotifyContent() ));
					else
						logger.info(String.format("【再次提交任务失败，将忽略此任务，NotifyUrl：%s,】", entity.getNotifyUrl()));
				}
			}, 3, TimeUnit.SECONDS);
		}
		else
		{
			logger.info(String.format("【提交通知任务成功，NotifyUrl：%s, 第%s次通知，当前队列大小：%s】", entity.getNotifyUrl(), entity.getSendTime(),taskQueue.size()));
		}
	}

	/**
	 * 启动执行器开始执行通知任务
	 */
	public static void execute()
	{
		HttpAsyncClient httpclient = null;
		try
		{
			ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
			ClientAsyncConnectionManager manager = new PoolingClientAsyncConnectionManager(ioReactor);
			httpclient = new DefaultHttpAsyncClient(manager);
			httpclient.getParams().setParameter("http.connection.timeout", 5000);
			httpclient.start();
		}
		catch (IOReactorException e)
		{
			logger.error("初始化HttpAsyncClient对象失败，异步通知模块启动失败！", e);
			return;
		}
		logger.info("异步通知模块启动成功！");
		while (true)
		{
			NotifyEntity entity = null;
			try
			{
				entity = taskQueue.take();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
				continue;
			}
			if (entity != null)
			{
				logger.info(String.format("\r\n【正在执行通知任务，NotifyUrl：%s, 本次为第%s次通知】", entity.getNotifyUrl(), entity.getSendTime()));
				try
				{
					httpclient.execute(entity.getHttpPost(), new ReceiveFutureCallback(entity));
				}
				catch (Exception e)
				{
					logger.error(String.format("\r\n【通知发生错误，NotifyUrl：%s】", entity.getNotifyUrl()), e);
				}
			}
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				HttpAsyncConnExecutor.execute();
			}
		}).start();

		//通知CP
		/*NotifyContent content = new NotifyContent();
		content.setExOrderNo("518tvg1000085477830226432425148");
		content.setPayOrderNo("10011504101502005933");
		content.setAppId("20150403095112273263");
		content.setAmount(1000);
		content.setPayType(2);
		content.setTransTime("2015-04-10 15:03:47");
		content.setCounts(1);
		content.setPayPoint("1581");
		content.setResult(0);
		content.setCpPrivateInfo("9-1000001492");*/
		
		//通知合作商
		NotifyCustomerContent content = new NotifyCustomerContent();
		content.setCommandid("106555613200100000100");
		content.setMo_msg("DC1*202010255800010");
		content.setMobile("18574342150");
		content.setResult(0);
		content.setTransTime("2015-09-10 10:34:35");

		//本地PHP
//		NotifyEntity entity = new NotifyEntity("http://localhost/paysync/verifydemo.php", content.generateContent());
		//本地JAVA
//		NotifyEntity entity = new NotifyEntity("http://localhost:8080/cpsyncserver/cpReceiveNotify.do", content.generateContent());
//		NotifyEntity entity = new NotifyEntity("http://wapi.gdbattle.com:8080/webApi/woPayment.do", content.generateContent());
		//滑雪大冒险TV版
//		NotifyEntity entity = new NotifyEntity("http://payment.api.yodo1.cn/payment/channel/ATET/callback", content.generateContent());
		//滑雪大冒险
//		NotifyEntity entity = new NotifyEntity("http://123.125.174.133:8080/payment/channel/ATET/callback", content.generateContent());
		//古剑奇缘
//		NotifyEntity entity = new NotifyEntity("http://int.zxy.mxy.mobi/atet/pay_zxy.php", content.generateContent());
		//仙剑奇缘
//		NotifyEntity entity = new NotifyEntity("http://manager.xjqy.ffcai.com/api/charge/atet.php", content.generateContent());
		//火力少年王
//		NotifyEntity entity = new NotifyEntity("http://api.tvsdk.cn/tvsdk/notify/notify_518.ashx", content.generateContent());
		
		//完美
		NotifyEntity entity = new NotifyEntity("http://notify.tg52.com/onss/syncatetsmsorder", content.generateContent());
//		NotifyEntity entity = new NotifyEntity(ConstantData.PERFECT_NOTIFY_URL, content.generateContent());

		HttpAsyncConnExecutor.submitNotifyTask(entity);

		/*pool.schedule(new Runnable()
		{	

			@Override
			public void run()
			{
				NotifyContent content = new NotifyContent();
				content.setAppId("123");
				content.setAmount(120);
				content.setPayOrderNo("24321");

				NotifyEntity entity = new NotifyEntity("http://10.1.1.199:8080/test.do", content.generateContent());

				HttpAsyncConnExecutor.submitNotifyTask(entity);
			}
		}, 30, TimeUnit.SECONDS);
*/
	}
}