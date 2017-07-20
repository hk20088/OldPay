package com.newspace.payplatform.order.vo;

import java.util.ArrayList;
import java.util.List;

import com.newspace.payplatform.base.BaseResponseJsonVo;
import com.newspace.payplatform.base.JsonVo;

/**
 * TerminalQueryOrderResp.java 
 * 描 述:  中断查询订单接口的请求Vo类  
 * 作 者:  liushuai
 * 历 史： 2014-7-14 创建
 */
public class TerminalQueryOrderResp extends BaseResponseJsonVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 订单集合。
	 */
	private List<TerminalRespOrder> data = new ArrayList<TerminalRespOrder>();

	public List<TerminalRespOrder> getData()
	{
		return data;
	}

	/**
	 * 静态内部类。TerminalQueryOrderResp中订单集合中的实体类。
	 */
	public static class TerminalRespOrder implements JsonVo
	{
		private static final long serialVersionUID = 1L;

		private String orderNo;

		private String cpOrderNo;

		private String payPointName;

		private Integer counts;

		private Integer amount;

		private Integer deviceType;

		private Integer amountType;

		public String getOrderNo()
		{
			return orderNo;
		}

		public void setOrderNo(String orderNo)
		{
			this.orderNo = orderNo;
		}

		public String getCpOrderNo()
		{
			return cpOrderNo;
		}

		public void setCpOrderNo(String cpOrderNo)
		{
			this.cpOrderNo = cpOrderNo;
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

		public Integer getDeviceType()
		{
			return deviceType;
		}

		public void setDeviceType(Integer deviceType)
		{
			this.deviceType = deviceType;
		}

		public Integer getAmountType()
		{
			return amountType;
		}

		public void setAmountType(Integer amountType)
		{
			this.amountType = amountType;
		}
	}
}
