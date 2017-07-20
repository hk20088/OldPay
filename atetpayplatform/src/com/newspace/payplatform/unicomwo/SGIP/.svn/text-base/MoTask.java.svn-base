package com.newspace.payplatform.unicomwo.SGIP;

import org.apache.log4j.Logger;

import com.huawei.insa2.comm.sgip.message.SGIPMessage;

/**
 * @description 短信上行处理任务类
 * @author huqili
 * @date 2016年8月9日
 *
 */
public class MoTask implements Runnable{

	private static final Logger logger = Logger.getLogger(MoTask.class);
	
	private SGIPMessage msg;
	
	public MoTask(SGIPMessage msg)
	{
		this.msg = msg;
	}
	
	
	@Override
	public void run() {
		
		try 
		{
			MoProcess.ProcessRecvDeliverMsg(msg);
		}
		catch (Exception e) 
		{
			logger.error("SGIP执行上行逻辑时出现异常...",e);
		}
		
	}

}
