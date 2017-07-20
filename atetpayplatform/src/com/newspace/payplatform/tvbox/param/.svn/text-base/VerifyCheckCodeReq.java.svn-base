package com.newspace.payplatform.tvbox.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * VerifyCheckCodeReq.java 
 * 描 述:  校验验证码接口请求实体类
 * 作 者:  liushuai
 * 历 史： 2014-7-21 创建
 */
public class VerifyCheckCodeReq
{
	/**
	 * 请求json字符串中data属性
	 */
	@Expose
	private String data;

	/**
	 * 签名属性
	 */
	@Expose
	private String sign;

	private VerifyCheckCodeReqData dataObj = new VerifyCheckCodeReqData();

	private static final JLogger logger = LoggerUtils.getLogger(VerifyCheckCodeReq.class);

	/**
	 * 静态工厂方法：用来生成并初始化BindTelUpstreamContent对象
	 */
	public static VerifyCheckCodeReq getInstanceFromJson(String json)
	{
		try
		{
			VerifyCheckCodeReq content = JsonUtils.fromJson(json, VerifyCheckCodeReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), VerifyCheckCodeReqData.class);
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
		if (StringUtils.isExistNullOrEmpty(getCaptcha(),getAtetId(), getTelephone()))
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

	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
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

	public String getTelephone()
	{
		return dataObj.getTelephone();
	}

	public void setTelephone(String telephone)
	{
		dataObj.setTelephone(telephone);
	}

	public String getCaptcha()
	{
		return dataObj.getCaptcha();
	}

	public void setCaptcha(String captcha)
	{
		dataObj.setCaptcha(captcha);
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
	 * 静态内部类：data字段对应的实体类
	 */
	private static class VerifyCheckCodeReqData
	{
		private Integer userId;

		private String appId;

		private String atetId;
		
		private String productId;

		private String deviceCode;

		private String telephone;

		// 校验码
		private String captcha;

		public Integer getUserId()
		{
			return userId;
		}

		public void setUserId(Integer userId)
		{
			this.userId = userId;
		}

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

		public String getCaptcha()
		{
			return captcha;
		}

		public void setCaptcha(String captcha)
		{
			this.captcha = captcha;
		}
	}
}