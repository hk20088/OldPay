package com.newspace.payplatform.unicomsms.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * GetOrderNoDownstreamContent.java 
 * 描 述:  获取订单号下行短信内容
 * 作 者:  liushuai
 * 历 史： 2014-7-18 创建
 */
public class GetOrderNoDownstreamContent
{
	/**
	 * 短信内容数据data
	 */
	@Expose
	private String data;

	/**
	 * 短信内容数据data加密产生的签名
	 */
	@Expose
	private String sign;

	private GetOrderNoDownstreamData dataObj = new GetOrderNoDownstreamData();

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

	public String getOrderNo()
	{
		return dataObj.getOrderNo();
	}

	public void setOrderNo(String orderNo)
	{
		dataObj.setOrderNo(orderNo);
	}

	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
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

	/**
	 * 静态内部类： 获取订单号下行短信内容data字符串对应的实体类
	 */
	private static class GetOrderNoDownstreamData
	{
		/**
		 * 订单号
		 */
		private String orderNo;

		/**
		 * 应用id
		 */
		private String appId;

		public String getOrderNo()
		{
			return orderNo;
		}

		public void setOrderNo(String orderNo)
		{
			this.orderNo = orderNo;
		}

		public String getAppId()
		{
			return appId;
		}

		public void setAppId(String appId)
		{
			this.appId = appId;
		}
	}
}