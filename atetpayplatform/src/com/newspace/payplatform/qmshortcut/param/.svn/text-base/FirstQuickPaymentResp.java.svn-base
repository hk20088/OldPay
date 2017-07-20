package com.newspace.payplatform.qmshortcut.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * FirstQuickPaymentResp.java 
 * 描 述:  首次快捷支付接口响应实体类
 * 作 者:  liushuai
 * 历 史： 2014-8-6 创建
 */
public class FirstQuickPaymentResp
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private FirstQuickPaymentRespData dataObj = new FirstQuickPaymentRespData();

	/**
	 * 获得对象Json串
	 */
	public String getJsonStr()
	{
		data = JsonUtils.toJson(dataObj);
		return JsonUtils.toJsonWithExpose(this);
	}

	/**
	 * 获得data，如果data为空，尝试将dataObj对象转成json串赋值给data 
	 */
	public String getData()
	{
		return StringUtils.isNullOrEmpty(data) ? getNewestData() : data;
	}

	/**
	 * 获取最新的data数据。因为可能dataObj已经发生改变。
	 */
	public String getNewestData()
	{
		data = JsonUtils.toJson(dataObj);
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public Integer getCode()
	{
		return dataObj.getCode();
	}

	public void setCode(Integer code)
	{
		dataObj.setCode(code);
	}

	public String getOrderNo()
	{
		return dataObj.getOrderNo();
	}

	public void setOrderNo(String orderNo)
	{
		dataObj.setOrderNo(orderNo);
	}

	public String getDesc()
	{
		return dataObj.getDesc();
	}

	public void setDesc(String desc)
	{
		dataObj.setDesc(desc);
	}

	public String getShortCardNo()
	{
		return dataObj.getShortCardNo();
	}

	public void setShortCardNo(String shortCardNo)
	{
		dataObj.setShortCardNo(shortCardNo);
	}

	private static class FirstQuickPaymentRespData
	{
		private Integer code;

		private String orderNo;

		private String desc;

		private String shortCardNo;

		public String getShortCardNo()
		{
			return shortCardNo;
		}

		public void setShortCardNo(String shortCardNo)
		{
			this.shortCardNo = shortCardNo;
		}

		public String getDesc()
		{
			return desc;
		}

		public void setDesc(String desc)
		{
			this.desc = desc;
		}

		public Integer getCode()
		{
			return code;
		}

		public void setCode(Integer code)
		{
			this.code = code;
		}

		public String getOrderNo()
		{
			return orderNo;
		}

		public void setOrderNo(String orderNo)
		{
			this.orderNo = orderNo;
		}
	}
}
