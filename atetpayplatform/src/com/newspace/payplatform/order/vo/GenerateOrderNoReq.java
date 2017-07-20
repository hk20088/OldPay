package com.newspace.payplatform.order.vo;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * GenerateOrderNoReq.java 
 * 描 述:  生成订单号请求实体类  
 * 作 者:  liushuai
 * 历 史： 2014-7-22 创建
 */
public class GenerateOrderNoReq
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private GenerateOrderNoReqData dataObj = new GenerateOrderNoReqData();

	private static final JLogger logger = LoggerUtils.getLogger(GenerateOrderNoReq.class);

	/**
	 * 静态工厂方法：用来生成并初始化GenerateOrderNoReq对象
	 */
	public static GenerateOrderNoReq getInstanceFromJson(String json)
	{
		try
		{
			GenerateOrderNoReq content = JsonUtils.fromJson(json, GenerateOrderNoReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), GenerateOrderNoReqData.class);
			return content;
		}
		catch (Exception e)
		{
			logger.error("解析json字符串失败!\r\njson: " + json, e);
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

	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
	}

	public void setAtetId(String atetId){
		dataObj.setAtetId(atetId);
	}
	
	public String getAtetId()
	{
		return dataObj.getAtetId();
	}
	
	public void setData(String data)
	{
		this.data = data;
	}

	public String getDeviceId() {
		return dataObj.getDeviceId();
	}

	public void setDeviceId(String deviceId) {
		dataObj.setDeviceId(deviceId);
	}

	public String getChannelId() {
		return dataObj.getChannelId();
	}

	public void setChannelId(String channelId) {
		dataObj.setChannelId(channelId);
	}

	public Integer getDeviceType()
	{
		return dataObj.getDeviceType();
	}
	
	public void setDeviceType(Integer deviceType)
	{
		dataObj.setDeviceType(deviceType);
	}
	
	public String getVersionCode() {
		return dataObj.getVersionCode();
	}

	public void setVersionCode(String versionCode) {
		dataObj.setVersionCode(versionCode);
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
	 * 静态内部类：data字符串对应的实体类 
	 */
	private static class GenerateOrderNoReqData
	{
		/**
		 * 密钥Id,用来获取密钥
		 */
		String appId;
		
		/**
		 * 服务器下发的设备唯一Id
		 */
		String atetId;
		
		/**
		 * 平台数据库生成的唯一Id,代表设备型号
		 */
		String deviceId;
		
		/**
		 * 渠道，例如：TCL,九州
		 */
		String channelId;
		
		/**
		 * 设备类型。1 TV  2  手机   3 平板
		 */
		Integer deviceType;
		
		/**
		 * 支付APK版本号，自定义，例如第一个版本为1,第二个版本为2。用来兼容不同版本时使用。
		 */
		String versionCode;
		
		public String getAppId()
		{
			return appId;
		}

		public void setAppId(String appId)
		{
			this.appId = appId;
		}

		public String getAtetId() {
			return atetId;
		}

		public void setAtetId(String atetId) {
			this.atetId = atetId;
		}

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public String getChannelId() {
			return channelId;
		}

		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}

		public Integer getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(Integer deviceType) {
			this.deviceType = deviceType;
		}

		public String getVersionCode() {
			return versionCode;
		}

		public void setVersionCode(String versionCode) {
			this.versionCode = versionCode;
		}
		
	}
}
