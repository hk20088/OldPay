package com.newspace.payplatform.unicompay;

import java.util.HashMap;
import java.util.Map;

/**
 * UnicomProduct.java 
 * 描 述:  联通计费产品枚举
 * 作 者:  liushuai
 * 历 史： 2014-7-9 创建
 */
public enum UnicomProduct
{
	BYCOUNT_1("20201010", "63000034", "联通多屏游戏1元按次", 1),

	BYCOUNT_2("20202020", "63000035", "联通多屏游戏2元按次", 2),

	BYCOUNT_4("20203040", "63000036", "联通多屏游戏4元按次", 4),

	BYCOUNT_5("20204050", "63000037", "联通多屏游戏5元按次", 5),

	BYCOUNT_6("20205060", "63000038", "联通多屏游戏6元按次", 6),

	BYCOUNT_8("20206080", "63000039", "联通多屏游戏8元按次", 8),

	BYCOUNT_10("20207100", "63000040", "联通多屏游戏10元按次", 10),

	BYCOUNT_15("20208150", "63000041", "联通多屏游戏15元按次", 15),

	BYCOUNT_20("20209200", "63000042", "联通多屏游戏20元按次", 20),

	BYCOUNT_25("20210250", "63000043", "联通多屏游戏25元按次", 25),

	BYCOUNT_30("20211300", "63000044", "联通多屏游戏30元按次", 30),

	BYMONTH_8("10201080", "63000004","联通多屏游戏流量8元按月", 8),
	
	BYMONTH_12("10202120", "63000005","联通多屏游戏流量12元按月", 12),
	
	BYMONTH_20("10203200", "63000006","联通多屏游戏流量20元按月", 20);

	/**
	 * 计费点产品id
	 */
	private String payproductid;

	/**
	 * 商品id
	 */
	private String productid;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 价格
	 */
	private double price;

	private static final Map<Double, UnicomProduct> byCountMap = new HashMap<Double, UnicomProduct>();

	private static final Map<String, UnicomProduct> byProductIdMap = new HashMap<String, UnicomProduct>();

	static
	{
		for (UnicomProduct product : UnicomProduct.values())
		{
			byCountMap.put(product.getPrice(), product);
			byProductIdMap.put(product.getProductid(), product);
		}
	}

	UnicomProduct(String payproductid, String productid, String productName, double price)
	{
		this.payproductid = payproductid;
		this.productid = productid;
		this.productName = productName;
		this.price = price;
	}

	/**
	 * 通过计费产品价格获取产品对象 
	 */
	public static UnicomProduct getProductByPrice(Double price)
	{
		return byCountMap.get(price);
	}

	/**
	 * 通过计费产品id获取产品对象
	 * @param productId
	 * @return
	 */
	public static UnicomProduct getProductById(String productId)
	{
		return byProductIdMap.get(productId);
	}

	public String getPayproductid()
	{
		return payproductid;
	}

	public String getProductid()
	{
		return productid;
	}

	public String getProductName()
	{
		return productName;
	}

	public double getPrice()
	{
		return price;
	}
}
