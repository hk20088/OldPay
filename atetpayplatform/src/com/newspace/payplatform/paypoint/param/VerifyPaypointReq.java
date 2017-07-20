package com.newspace.payplatform.paypoint.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * VerifyPaypointReq.java 
 * 描 述:  校验支付点请求参数实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-2 创建
 */
public class VerifyPaypointReq
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private VerifyPaypointReqData dataObj = new VerifyPaypointReqData();

	private static final JLogger logger = LoggerUtils.getLogger(VerifyPaypointReq.class);

	public static VerifyPaypointReq getInstanceFromJson(String json)
	{
		try
		{
			VerifyPaypointReq content = JsonUtils.fromJson(json, VerifyPaypointReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), VerifyPaypointReqData.class);
			return content;
		}
		catch (Exception e)
		{
			logger.error("解析json字符串失败\r\njson:" + json, e);
		}
		return null;
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

	/**
	 * 判断对象是否合法（是否必须的参数都进行了赋值） 
	 */
	public boolean isLegal()
	{
		if (StringUtils.isExistNullOrEmpty(getAppId(), getPayPoint()) || getAmount() == null || getCounts() == null)
			return false;
		return true;
	}

	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
	}

	public String getPayPoint()
	{
		return dataObj.getPayPoint();
	}

	public void setPayPoint(String payPoint)
	{
		dataObj.setPayPoint(payPoint);
	}

	public Integer getCounts()
	{
		return dataObj.getCounts();
	}

	public void setCounts(Integer counts)
	{
		dataObj.setCounts(counts);
	}

	public Integer getAmount()
	{
		return dataObj.getAmount();
	}

	public void setAmount(Integer amount)
	{
		dataObj.setAmount(amount);
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

	private static class VerifyPaypointReqData
	{
		private String appId;

		private String payPoint;

		private Integer counts;

		private Integer amount;

		public String getAppId()
		{
			return appId;
		}

		public void setAppId(String appId)
		{
			this.appId = appId;
		}

		public String getPayPoint()
		{
			return payPoint;
		}

		public void setPayPoint(String payPoint)
		{
			this.payPoint = payPoint;
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
	}
}
