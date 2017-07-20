package com.newspace.payplatform.unicomsms.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * GetOrderNoUpstreamContent.java 
 * 描 述:  获取订单号上行短信内容
 * 作 者:  liushuai
 * 历 史： 2014-7-16 创建
 */
public class GetOrderNoUpstreamContent
{
	/**
	 * 获取订单号上行短信data
	 */
	@Expose
	private String data;

	/**
	 * 对短信数据加密产生的签名
	 */
	@Expose
	private String sign;

	private GetOrderNoUpstreamData dataObj = new GetOrderNoUpstreamData();

	private static final JLogger logger = LoggerUtils.getLogger(GetOrderNoUpstreamContent.class);

	/**
	 * 静态工厂方法：用来生成并初始化BindTelUpstreamContent对象
	 */
	public static GetOrderNoUpstreamContent getInstanceFromJson(String json)
	{
		try
		{
			GetOrderNoUpstreamContent content = JsonUtils.fromJson(json, GetOrderNoUpstreamContent.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), GetOrderNoUpstreamData.class);
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

	public Integer getMsgType()
	{
		return dataObj.getMsgType();
	}

	public void setMsgType(Integer msgType)
	{
		dataObj.setMsgType(msgType);
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
	 * 静态内部类：获取订单号上行短信中data字符串对应的类 
	 */
	private static class GetOrderNoUpstreamData extends UpstreamData
	{
	}
}