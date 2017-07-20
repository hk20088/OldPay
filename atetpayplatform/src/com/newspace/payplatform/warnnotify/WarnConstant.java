package com.newspace.payplatform.warnnotify;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.PropertiesUtils;

/**
 * WarnConstant.java
 * @describe 预警阀值或手机号可能会有修改，按照固定频率，每天加载一次配置文件。
 * @author huqili
 * @date 2014年11月12日
 *
 */
public class WarnConstant implements Runnable{

	private static ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
	
	private static final JLogger logger = LoggerUtils.getLogger(WarnConstant.class);
	
	public static Properties pro;

	private static int count = 1;
	
	@Override
	public void run() {
		logger.info("加载预警配置文件服务启动成功！");
		// 按照固定频率， 每隔5秒执行一次加载任务。
		pool.scheduleAtFixedRate(new Runnable()
		{
			@Override
			public void run()
			{
				pro = PropertiesUtils.getProps("prewarning",false);
				logger.info("第" + count + "次加载预警配置文件成功！");
				String a = pro.getProperty("threshold");
				logger.info("阀值为：" + a);
				count ++;
			}
		}, 10, 10, TimeUnit.SECONDS);
		
	}
	
	/**
	 * 静态方法，用来外部调用，启动加载配置文件的服务
	 */
	public static void startServer(){
		pool.execute(new WarnConstant());
	}
}
