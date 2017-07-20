package com.newspace.payplatform.tvbox.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * GetCheckCodeReq.java 
 * 描 述:  获取验证码接口请求实体类
 * 作 者:  liushuai
 * 历 史： 2014-7-21 创建
 */
public class GetCheckCodeReq
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private GetCheckCodeReqData dataObj = new GetCheckCodeReqData();

	private static final JLogger logger = LoggerUtils.getLogger(GetCheckCodeReq.class);

	/**
	 * 静态工厂方法：用来生成并初始化GetCheckCodeReq对象
	 */
	public static GetCheckCodeReq getInstanceFromJson(String json)
	{
		try
		{
			GetCheckCodeReq content = JsonUtils.fromJson(json, GetCheckCodeReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), GetCheckCodeReqData.class);
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
	 * 对象是否合法：not null的字段都进行了赋值。
	 * @return boolean  true：合法
	 */
	public boolean isLegal()
	{
		if (StringUtils.isExistNullOrEmpty(getAppId(), getDeviceCode(), getProductId(), getTelephone()))
			return false;
		return true;
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
	
	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
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

	public String getTelephone()
	{
		return dataObj.getTelephone();
	}

	public void setTelephone(String telephone)
	{
		dataObj.setTelephone(telephone);
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
	 * 静态内部类：data字符串对应的实体类 
	 */
	private static class GetCheckCodeReqData
	{
		private Integer userId;

		private String appId;

		private String atetId;
		
		private String productId;

		private String deviceCode;

		private String telephone;

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

		public String getAppId()
		{
			return appId;
		}

		public void setAppId(String appId)
		{
			this.appId = appId;
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

		public String getTelephone()
		{
			return telephone;
		}

		public void setTelephone(String telephone)
		{
			this.telephone = telephone;
		}
	}
}