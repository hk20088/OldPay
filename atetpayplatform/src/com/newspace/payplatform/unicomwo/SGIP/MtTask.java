package com.newspace.payplatform.unicomwo.SGIP;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

/**
 * @descripton 下行短信任务
 * @author huqili
 * @since SDK1.7
 * @date 2016年8月5日
 *
 */
public class MtTask implements Callable<String>{

	
	private String phone;
	private String msgContent;
	
	private static final Logger logger = Logger.getLogger(MtTask.class);
	
	public MtTask(String phone,String msgContent)
	{
		this.phone = phone;
		this.msgContent = msgContent;
	}
	
	//这种方法无返回值 
	/*@Override
	public void run() {
		try 
		{
			logger.info(String.format("准备下行请求计费短信给用户[%s]...", phone));
			//请求网关下行短信给用户
			int returnCode = Mt.sgipMt(phone, msgContent);
			
			logger.info(String.format("用户[%s]下行请求计费短信的结果是:[%s]", phone,returnCode));
			//请求网关下行短信给用户
		} 
		catch (UnsupportedEncodingException e)
		{
			logger.error("SGIP下行短信失败，编码异常...");
		}
	}*/

	
	/**
	 * 线程实现 Callable接口， 可返回自定义的泛型结果
	 */
	@Override
	public String call(){
		int returnCode = 0;
		try 
		{
			logger.info(String.format("用户[%s]准备下行请求计费短信...", phone));
			//请求网关下行短信给用户
			returnCode = Mt.sgipMt(phone, msgContent);
			
			logger.info(String.format("用户[%s]下行请求计费短信的结果是:[%s]", phone,returnCode));
			//请求网关下行短信给用户
		} 
		catch (UnsupportedEncodingException e)
		{
			logger.error("SGIP下行短信失败，编码异常...");
			returnCode = -1;
		}
		
		return String.valueOf(returnCode);
	}

}
