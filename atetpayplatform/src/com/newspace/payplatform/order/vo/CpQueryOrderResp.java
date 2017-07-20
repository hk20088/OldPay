package com.newspace.payplatform.order.vo;

import com.newspace.payplatform.base.BaseResponseJsonVo;

/**
 * CpQueryOrderResp.java 
 * 描 述:  CP查询订单信息的响应Vo类  
 * 作 者:  liushuai
 * 历 史： 2014-7-14 创建
 */
public class CpQueryOrderResp extends BaseResponseJsonVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 支付点名称（商品名称）
	 */
	private String payPointName;

	/**
	 * 个数
	 */
	private Integer counts;

	/**
	 * 金额，单位：分
	 */
	private Integer amount;

	/**
	 * 金额类型。0 ：RMB
	 */
	private Integer amountType;

	/**
	 * 订单状态
	 */
	private Integer state;

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getPayPointName()
	{
		return payPointName;
	}

	public void setPayPointName(String payPointName)
	{
		this.payPointName = payPointName;
	}

	public Integer getCounts()
	{
		return counts;
	}

	public void setCounts(Integer counts)
	{
		this.counts = counts;
	}

	public Integer getAmount()
	{
		return amount;
	}

	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}

	public Integer getAmountType()
	{
		return amountType;
	}

	public void setAmountType(Integer amountType)
	{
		this.amountType = amountType;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	public static long getSerialVersionUID()
	{
		return serialVersionUID;
	}
}