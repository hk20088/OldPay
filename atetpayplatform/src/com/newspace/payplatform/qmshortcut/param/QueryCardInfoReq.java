package com.newspace.payplatform.qmshortcut.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * QueryCardInfoReq.java 
 * 描 述:  根据卡号查询银行卡信息请求参数实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-12 创建
 */
public class QueryCardInfoReq
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private QueryCardInfoReqData dataObj = new QueryCardInfoReqData();

	private static final JLogger logger = LoggerUtils.getLogger(QueryCardInfoReq.class);

	/**
	 * 静态工厂方法：用来生成并初始化QueryCardInfoReq对象
	 */
	public static QueryCardInfoReq getInstanceFromJson(String json)
	{
		try
		{
			QueryCardInfoReq content = JsonUtils.fromJson(json, QueryCardInfoReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), QueryCardInfoReqData.class);
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
		if (StringUtils.isExistNullOrEmpty(getAppId(), getCardNo()))
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

	public String getCardNo()
	{
		return dataObj.getCardNo();
	}

	public void setCardNo(String cardNo)
	{
		dataObj.setCardNo(cardNo);
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

	private static class QueryCardInfoReqData
	{
		private String appId;

		private String cardNo;

		public String getAppId()
		{
			return appId;
		}

		public void setAppId(String appId)
		{
			this.appId = appId;
		}

		public String getCardNo()
		{
			return cardNo;
		}

		public void setCardNo(String cardNo)
		{
			this.cardNo = cardNo;
		}
	}
}
