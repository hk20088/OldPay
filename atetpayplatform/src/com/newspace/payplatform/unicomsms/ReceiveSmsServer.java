package com.newspace.payplatform.unicomsms;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.PropertiesUtils;

/**
 * ReceiveSmsServer.java 
 * 描 述:  接收联通发送的上行短信服务  
 * 作 者:  liushuai
 * 历 史： 2014-7-14 创建
 */
public class ReceiveSmsServer extends Ice.Application implements Runnable
{
	/**
	 * 服务地址ip
	 */
	private static final String RECEIVE_SERVER_IP;

	/**
	 * 服务监听端口
	 */
	private static final String RECEIVE_SERVER_PORT;

	/**
	 * 服务适配器名称
	 */
	private static final String RECEIVE_SERVICE_ADAPTER;

	/**
	 * 服务名
	 */
	private static final String RECEIVE_SERVICE_NAME;

	private static final JLogger logger = LoggerUtils.getLogger(ReceiveSmsServer.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();

	/**
	 * 是否已经启动的标识
	 */
	private static volatile AtomicBoolean isStarted = new AtomicBoolean(false);

	static
	{
		Properties prop = PropertiesUtils.getProps("configuration");
		RECEIVE_SERVER_IP = prop.getProperty("receive_sms_server_ip");
		RECEIVE_SERVER_PORT = prop.getProperty("receive_sms_server_port");
		RECEIVE_SERVICE_ADAPTER = prop.getProperty("receive_sms_service_adapter");
		RECEIVE_SERVICE_NAME = prop.getProperty("receive_sms_service_name");
	}

	/**
	 * 静态方法，用来在外部调用（比如项目启动之时），启动短信接收服务。
	 * 从外部只能启动一次，防止启动多次个服务，出现问题。
	 */
	public static void startServer()
	{
		if (isStarted.compareAndSet(false, true))
			pool.execute(new ReceiveSmsServer());
	}

	/**
	 * 从Ice.Application继承的run()
	 */
	@Override
	public int run(String[] args)
	{
		int state = 0;
		try
		{
			Ice.ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints(RECEIVE_SERVICE_ADAPTER,
					"default -h " + RECEIVE_SERVER_IP + " -p " + RECEIVE_SERVER_PORT);
			Ice.Object object = new ReceiveMsgServiceServant();
			adapter.add(object, Ice.Util.stringToIdentity(RECEIVE_SERVICE_NAME));
			adapter.activate();
			logger.info("短消息上行（MO）接收端启动，监听端口：" + RECEIVE_SERVER_PORT);
			communicator().waitForShutdown();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			state = -1;
		}
		finally
		{
			communicator().destroy();
		}
		return state;
	}

	/**
	 * 线程的run()
	 */
	@Override
	public void run()
	{
		main("AtetReceiveService", new String[0]);
		// reStartServer();
		logger.error("接收联通发送的上行短信服务出现异常已经关闭...");
	}

	/**
	 * 重新启动一个接收短信服务线程。
	 * 在重新启动之前，一定要保证ICE通信器已经被销毁，也就说已经释放掉端口。
	 * 不然会报端口已经被占用的异常。
	 */
	private void reStartServer()
	{
		pool.execute(new ReceiveSmsServer());
	}
}