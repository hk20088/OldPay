package com.newspace.payplatform.unicomwo.SGIP;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.huawei.insa2.comm.sgip.message.SGIPDeliverMessage;
import com.huawei.insa2.comm.sgip.message.SGIPMessage;
import com.huawei.insa2.util.Args;
import com.huawei.smproxy.SGIPSMProxy;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;

/**
 * @description SMS->SP
 * @author huqili
 * @date 2016年7月20日
 *
 */
public class Mo extends SGIPSMProxy {
	
	private static final JLogger logger = LoggerUtils.getLogger(Mo.class);
	
	//定义一个线程池
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	
	//启动接收短信的服务
	public static void startSgipServer()
	{
		Args argstr = new Args();
		argstr.set("serHost", SGIPConstant.SGIP_SERVER_IP);
		argstr.set("serviceport", SGIPConstant.SGIP_SERVER_PORT);
		argstr.set("localhost", SGIPConstant.LOCAL_IP);
		argstr.set("localport", SGIPConstant.LOCAL_PORT);
		argstr.set("transaction-timeout", 10); // 操作超时时间(单位：秒)
		argstr.set("read-timeout", 15); // 物理连接读操作超时时间(单位：秒)

		logger.info("准备启动SGIP监听服务....");
		Mo mymo = new Mo(argstr);
	}

	public Mo(Args arg0) {
//		super(arg0);
		logger.info("启动SGIP服务监听...");
		
		startService(SGIPConstant.LOCAL_IP, SGIPConstant.LOCAL_PORT);
		
		logger.info("启动SGIP服务监听成功...端口号："+SGIPConstant.LOCAL_PORT);
	}


	/**
	 * 收到用户回复的短信(上行)。
	 * 
	 * @param msg
	 *            收到的消息
	 * @return 返回的相应消息。
	 */
	public SGIPMessage onDeliver(SGIPDeliverMessage msg) {

		try 
		{
			
			pool.execute(new MoTask(msg));
			//ProcessRecvDeliverMsg(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.onDeliver(msg);
	}


}
