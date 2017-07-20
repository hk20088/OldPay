/*package com.newspace.payplatform.unicomsms;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.HttpUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.payplatform.ConstantData;
import com.newspace.payplatform.unicomsms.param.SendMsgReq;

*//**
 * AssignSendMsgTask.java
 * @description 转发下发短信请求任务：如果下发短信的请求发生在215、229服务器上，
 * 				将其转发到214服务器（只有214服务器有请求联通短信网关下发短信的权限）。
 * @author huqili
 * @date　2014年11月22日
 * 
 * 因为下发短信接口由ICE修改为HTTP协议，故所有支付平台服务器都有下发短信的权限，此类作废。 2015年4月13日  huqili
 * 
 *　
 *//*
public class AssignSendMsgTask implements Runnable{
	
	SendMsgReq reqVo = new SendMsgReq();
	
	private static final String REQUEST_URL = "http://"+ConstantData.UNICOM_SMS_SERVER_IP+":25000/atetpayplatform/sendMsg.do";
	
	private static final JLogger logger = LoggerUtils.getLogger(AssignSendMsgTask.class);
	
	public  AssignSendMsgTask(String telephone,String msgContent){
		this.reqVo.setTelephone(telephone);
		this.reqVo.setMsgContent(msgContent);
	}

	@Override
	public void run() {
		
		try {
			HttpUtils.post(REQUEST_URL, JsonUtils.toJson(reqVo).getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("请求下发短信服务器失败！");
		}
	}

	
}
*/