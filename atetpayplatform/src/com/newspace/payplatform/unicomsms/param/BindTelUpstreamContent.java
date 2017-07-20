package com.newspace.payplatform.unicomsms.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * BindTelUpstreamContent.java 
 * 描 述:  强联网时，绑定手机号上行短信内容
 * 作 者:  liushuai
 * 历 史： 2014-7-16 创建
 */
public class BindTelUpstreamContent
{
	/**
	 * 短信数据
	 */
	@Expose
	private String data;

	/**
	 * 对短信数据加密产生的签名
	 */
	@Expose
	private String sign;

	/**
	 * data属性的值转成的数据对象
	 */
	private BindTelUpstreamData dataObj = new BindTelUpstreamData();

	private static final JLogger logger = LoggerUtils.getLogger(BindTelUpstreamContent.class);

	/**
	 * 静态工厂方法：用来生成并初始化BindTelUpstreamContent对象
	 */
	public static BindTelUpstreamContent getInstanceFromJson(String json)
	{
		try
		{
			BindTelUpstreamContent content = JsonUtils.fromJson(json, BindTelUpstreamContent.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), BindTelUpstreamData.class);
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

	public Integer getUserId()
	{
		return dataObj.getUserId();
	}

	public void setUserId(Integer userId)
	{
		dataObj.setUserId(userId);
	}
	
	public String getAtetId() {
		return dataObj.getAtetId();
	}

	public void setAtetId(String atetId) {
		dataObj.setAtetId(atetId);
	}
	
	public String getProductId()
	{
		return dataObj.getProductId();
	}

	public void setProductId(String productId)
	{
		dataObj.setProductId(productId);
	}

	public String getDeviceCode()
	{
		return dataObj.getDeviceCode();
	}

	public void setDeviceCode(String deviceCode)
	{
		dataObj.setDeviceCode(deviceCode);
	}

	/**
	 * 静态内部类：用来封装 绑定手机号上行参数 
	 */
	private static class BindTelUpstreamData extends UpstreamData
	{
		/**
		 * 用户id
		 */
		private Integer userId;
		
		/**
		 * 服务器下下发的设备唯一Id
		 */
		private String atetId;

		/**
		 * 设备唯一标识
		 */
		private String productId;

		/**
		 * 设备编码
		 */
		private String deviceCode;

		public Integer getUserId()
		{
			return userId;
		}

		public void setUserId(Integer userId)
		{
			this.userId = userId;
		}

		public String getAtetId() {
			return atetId;
		}

		public void setAtetId(String atetId) {
			this.atetId = atetId;
		}

		public String getProductId()
		{
			return productId;
		}

		public void setProductId(String productId)
		{
			this.productId = productId;
		}

		public String getDeviceCode()
		{
			return deviceCode;
		}

		public void setDeviceCode(String deviceCode)
		{
			this.deviceCode = deviceCode;
		}
	}
}