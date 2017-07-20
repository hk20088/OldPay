package com.newspace.payplatform.unicomhttp.param;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 联通VAC渠道通知地址信息类
 * @author huqili
 * @date 2017年7月7日
 *
 */
public enum VacNotifyUrlEnum {
	
	notify_url_01("2000","给力富","http://notify.tg52.com/onss/syncatetsmsorder"),
	notify_url_02("3000","","http://120.76.24.173/payment-ws/paymentCall/tianxu"),
	notify_url_03("3100","","http://syn.qipagame.cn:8088/syn/tianxu/notify.jsp"),
	notify_url_04("3200","","http://inf.vsoyou.net/inf/huifen/txhfsync.htm"),
	notify_url_05("4000","","http://wfwf.appleflying.info/sp/payszsx_dx_mr"),
	notify_url_06("5000","杭州平治","http://if.unidian.com/PayChannel/SMS/OrderNotify_SZSXHL_VAC.aspx");
	
	private String corpId;  //企业ID
	private String corpName;   //企业名称
	private String notifyUrl;   //通知地址
	
	public static final Map<String, String> getUrlMap = new HashMap<String, String>();
	
	static
	{
		for(VacNotifyUrlEnum vacNotifyUrlEnum : VacNotifyUrlEnum.values())
		{
			getUrlMap.put(vacNotifyUrlEnum.getCorpId(), vacNotifyUrlEnum.getNotifyUrl());
		}
	}
	
	VacNotifyUrlEnum(String corpId,String corpName,String notifyUrl)
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
