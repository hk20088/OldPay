package com.newspace.payplatform.unicomsms;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.IPUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.unicomsms.param.SendMsgReq;

/**
 * SendMsgServlet.java
 * @describe 下发短信接口：
 * 			1、提供给业务平台预警系统使用，当订单异常时发送预警短信到相关的管理员。
 * 			2、因为只有214服务器才有请求联通短信网关下发短信的权限，故如果下发短信的请求（如获取验证码、订单预警）
 * 				发生在215、229服务器上，自动转到214上此接口下发短信。
 * @author huqili
 * @date 2014年11月12日
 */
@SuppressWarnings("serial")
public class SendMsgServlet extends BaseServlet
{
	
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	private static final JLogger logger = LoggerUtils.getLogger(SendMsgServlet.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		/**
		 * 1、有可能向多个人发送短信，所以请求参数为数组，遍历手机号分别提交到下发短信的任务中执行。
		 * 2、短信内容可能超过一条短信所支持的长度，所以将短信内容分割成多条发送。
		 */
		logger.info(String.format("进入下发短信接口...此请求来自：%s", IPUtils.getRemoteAddress(request)));
		SendMsgReq reqVo = new SendMsgReq();
		String reqJsonStr = getStrFromRequest(request);
		int returnCode = padRequestVo(reqJsonStr, reqVo);
		
		if(returnCode == ReturnCode.SUCCESS.getCode()){
			String[] tels = reqVo.getTelephone().split(",");
			for(String telephone : tels){
				pool.execute(new SendMsgTask(telephone, reqVo.getMsgContent()));
			}
		}
		
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}