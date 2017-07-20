package com.newspace.payplatform.unicomwo.param;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 联通产品枚举类
 * @author huqili
 * @since JDK1.6
 * @date 2016年6月22日
 *
 */
public enum WoProduct {

	wo_business_2(2,"1012","A游学单点","101%_2_$"),
	
	wo_business_4(4,"9265","A游学示例","9%_265_$"),
	
	wo_business_6(6,"9433","A游学单课时","%_9433_$"),
	
	wo_business_8(8,"6215","A游学单次运营","6%_215_$"),
	
	wo_business_10(10,"1834","A游学1DAY","18%_34_$"),
	
	wo_business_12(12,"5326","A游学7DAY","53%_26_$"),
	
	wo_business_15(15,"5233","A游学15DAY","52%_33_$"),
	
	wo_business_20(20,"8924","A游学30DAY","89%_24_$");

	
	private Integer price;
	private String channelId;
	private String subject;
	private String key;
	
	public static final Map<Integer, String> getChannelIdMap = new HashMap<Integer, String>();
	public static final Map<String, Integer> getPriceMap = new HashMap<String, Integer>();
	public static final Map<String, String> getSubjectMap = new HashMap<String, String>();
	public static final Map<String, String> getKeyMap = new HashMap<String, String>();
	
	static
	{
		for(WoProduct product : WoProduct.values())
		{
			getChannelIdMap.put(product.getPrice(), product.getChannelId());
			getPriceMap.put(product.getChannelId(), product.getPrice());
			getSubjectMap.put(product.getChannelId(), product.getSubject());
			getKeyMap.put(product.getChannelId(), product.getKey());
		}
	}
	
	WoProduct(Integer price,String channelId,String subject,String key)
	{
		this.price = price;
		this.channelId = channelId;
		this.subject = subject;
		this.key = key;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	
}
