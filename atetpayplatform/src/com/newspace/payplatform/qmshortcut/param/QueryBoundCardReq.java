package com.newspace.payplatform.qmshortcut.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * QueryBoundCardReq.java 
 * 描 述:  查询已经绑定的银行卡请求实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-8 创建
 */
public class QueryBoundCardReq
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private QueryBoundCardReqData dataObj = new QueryBoundCardReqData();

	private static final JLogger logger = LoggerUtils.getLogger(FirstQuickPaymentReq.class);

	/**
	 * 静态工厂方法：用来生成并初始化QueryBoundCardReq对象
	 */
	public static QueryBoundCardReq getInstanceFromJson(String json)
	{
		try
		{
			QueryBoundCardReq content = JsonUtils.fromJson(json, QueryBoundCardReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), QueryBoundCardReqData.class);
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
	 * 判断是否合法：必须的参数是否都进行了设置 
	 */
	public boolean isLegal()
	{
		if (StringUtils.isNullOrEmpty(getAppId()) || getUserId() == null)
			return false;
		return true;
	}

	public String getCardType()
	{
		return dataObj.getCardType();
	}

	public void setCardType(String cardType)
	{
		dataObj.setCardType(cardType);
	}

	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
	}

	public Integer getUserId()
	{
		return dataObj.getUserId();
	}

	public void setUserId(Integer userId)
	{
		dataObj.setUserId(userId);
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

	private static class QueryBoundCardReqData
	{
		private String appId;

		private Integer userId;

		private String cardType;

		public String getCardType()
		{
			return cardType;
		}

		public void setCardType(String cardType)
		{
			this.cardType = cardType;
		}

		public String getAppId()
		{
			return appId;
		}

		public void setAppId(String appId)
		{
			this.appId = appId;
		}

		public Integer getUserId()
		{
			return userId;
		}

		public void setUserId(Integer userId)
		{
			this.userId = userId;
		}
	}
}
