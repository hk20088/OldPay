package com.newspace.payplatform.unicomsms;

import java.util.Map;

import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.ConstantData;
import com.newspace.payplatform.unicomsms.ice.Mt;

/**
 * SendMsgTask.java 
 * 描 述:  发送短信任务  
 * 作 者:  liushuai
 * 历 史： 2014-7-25 创建
 */
public class SendMsgTask implements Runnable
{
	private String telephone;
	
	private String msgContent;

	public SendMsgTask(String telephone,String msgContent)
	{
		this.telephone = telephone;
		this.msgContent = msgContent;
	}

	@Override
	public void run()
	{
		if(msgContent.length() > 70){ //如果短信长度大于70则需要分多条发送
			Map<Integer, String> map = StringUtils.splitStr(msgContent, 65);
			for(int i = 0; i < map.size(); i++){
				Mt mt = Mt.fillMt(telephone, map.get(i) + ("(" + (i+1) + "/"+ map.size() + ")") + ConstantData.APPEND_BLANK);
				SendMsgClient.send(mt);
			}
		}else{
			Mt mt = Mt.fillMt(telephone, msgContent + ConstantData.APPEND_BLANK);
			SendMsgClient.send(mt);
		}
		
	}
}