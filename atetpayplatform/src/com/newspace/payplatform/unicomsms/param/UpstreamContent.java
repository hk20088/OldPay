package com.newspace.payplatform.unicomsms.param;

import com.google.gson.JsonSyntaxException;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;

/**
 * UpstreamContent.java 
 * 描 述:  封装上行短信内容
 * 作 者:  liushuai
 * 历 史： 2014-7-16 创建
 */
public class UpstreamContent
{
	/**
	 * 上行短信数据
	 */
	private String data;

	/**
	 * 上线短信数据的数字签名
	 */
	private String sign;

	/**
	 * 上行短信数据解析成对象
	 */
	private UpstreamData dataObj = new UpstreamData();

	private static final JLogger logger = LoggerUtils.getLogger(UpstreamContent.class);

	/**
	 * 静态工厂方法：用来生成并初始化BindTelUpstreamContent对象
	 */
	public static UpstreamContent getInstanceFromJson(String json)
	{
		/**
		 * 1、先判断短信内容是json串还是拼接字符串。
		 * 2、根据判断结果不同选择不同的解析方式。
		 */
		UpstreamContent content = new UpstreamContent();
		try
		{
			if (json.startsWith("{"))
			{
				content = JsonUtils.fromJson(json, UpstreamContent.class);
				content.dataObj = JsonUtils.fromJson(content.getData(), UpstreamData.class);
			}
			else
			{
				content.setData(json.substring(0, json.lastIndexOf("&")));
				content.setSign(json.substring(json.lastIndexOf("=") + 1));

				String[] str = json.split("&");
				content.setAppId(str[2].substring(2));
				content.setMsgType(Integer.parseInt(str[8].substring(2)));
			}
			return content;
		}
		catch (JsonSyntaxException e)
		{
			logger.error("UpstreamContent：解析json字符串失败！\r\njson: " + json, e);
		}
		catch (IndexOutOfBoundsException e)
		{
			logger.error("UpstreamContent：解析拼接字符串出错！\r\ncontent：" + json, e);
		}
		return null;
	}

	public String getData()
	{
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
}