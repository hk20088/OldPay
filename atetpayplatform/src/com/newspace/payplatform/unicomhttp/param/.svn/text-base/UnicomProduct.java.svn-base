package com.newspace.payplatform.unicomhttp.param;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link UnicomProduct.java}
 * @description 枚举类型，用来描述录入的产品
 * @author huqili
 * @date 2015年4月13日
 *
 */
public enum UnicomProduct {

	BYCOUNT_1("202010100", "dc1","1065556132000001", 1),

    BYCOUNT_2("202010102", "dc2","1065556132000002", 2),

	BYCOUNT_4("202010109", "dc4","1065556132000004", 4),

	BYCOUNT_5("202010114", "dc5","1065556132000005", 5),

	BYCOUNT_6("202010120", "dc6","1065556132000006", 6),

	BYCOUNT_8("202010135", "dc8","1065556132000008", 8),
	
	BYCOUNT_10("202010154", "dc10","1065556132000010", 10),
	
	BYCOUNT_12("202010292", "dc12","1065556132000012", 12),
	
	BYCOUNT_15("202010204", "dc15","1065556132000015", 15),

	BYCOUNT_20("202010219", "dc20","1065556132000020", 20),

	BYCOUNT_25("202010235", "dc25","1065556132000025", 25),

	BYCOUNT_30("202010252", "dc30","1065556132000030", 30);

	/**
	 * 商品短代码
	 */
	private String shortCode;

	/**
	 * 订购指令
	 */
	private String payCommand;
	
	/**
	 * 短信接入号
	 */
	private String commandid;

	/**
	 * 价格
	 */
	private double price;
	
	private static final Map<Double, String> getShortCodeMap = new HashMap<Double, String>();
	
	private static final Map<Double, String> getPayCommandMap = new HashMap<Double, String>();
	
	private static final Map<Double, String> getCommandidMap = new HashMap<Double, String>();
	
	UnicomProduct(String shortCode, String payCommand,String commandid, double price)
	{
		this.shortCode = shortCode;
		this.payCommand = payCommand;
		this.commandid = commandid;
		this.price = price;
	}
	
	static{
		for(UnicomProduct product : UnicomProduct.values()){
			getShortCodeMap.put(product.getPrice(), product.getShortCode());
			getPayCommandMap.put(product.getPrice(), product.getPayCommand());
			getCommandidMap.put(product.getPrice(), product.getCommandid());
		}
	}
	
	/**
	 * 通过金额获取对应的商品短代码
	 * @return
	 */
	public static String getShortCode(Double price)
	{
		return getShortCodeMap.get(price);
	}
	
	/**
	 * 通过金额获取对应的商品认购指令
	 * @return
	 */
	public static String getPayCommand(Double price)
	{
		return getPayCommandMap.get(price);
	}
	
	/**
	 * 通过金额获取短信接入号
	 * @return
	 */
	public static String getCommandid(Double price)
	{
		return getCommandidMap.get(price);
	}
	
	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getPayCommand() {
		return payCommand;
	}

	public void setPayCommand(String payCommand) {
		this.payCommand = payCommand;
	}

	public String getCommandid() {
		return commandid;
	}

	public void setCommandid(String commandid) {
		this.commandid = commandid;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}

