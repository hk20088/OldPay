package com.newspace.payplatform.unicomwo.param;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 联通沃邮箱渠道
 * @author huqili
 * @date 2017年7月7日
 *
 */
public enum WoNotifyUrlEnum {

	notify_url_01("GLF00711","给力富","http://notify.tg52.com/onss/wokdatetmailorder"),
	notify_url_02("HZPZ0711","杭州平治","http://if.unidian.com/PayChannel/VCode/OrderNotify_SZSXHL_WYX.aspx");
	
	private String corpId;  //企业ID
	private String corpName;   //企业名称
	private String notifyUrl;   //通知地址
	
	public static final Map<String, String> getUrlMap = new HashMap<String, String>();
	
	static
	{
		for(WoNotifyUrlEnum notifyUrl : WoNotifyUrlEnum.values())
		{
			getUrlMap.put(notifyUrl.getCorpId(), notifyUrl.getNotifyUrl());
		}
	}
	
	WoNotifyUrlEnum(String corpId,String corpName,String notifyUrl)
	{
		this.corpId = corpId;
		this.corpName = corpName;
		this.notifyUrl = notifyUrl;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	
	
}
