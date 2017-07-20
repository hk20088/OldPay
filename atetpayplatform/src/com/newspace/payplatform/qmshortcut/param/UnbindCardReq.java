package com.newspace.payplatform.qmshortcut.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * UnbindCardReq.java 
 * 描 述:  解绑银行卡信息请求实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-12 创建
 */
public class UnbindCardReq
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private UnbindCardReqData dataObj = new UnbindCardReqData();

	private static final JLogger logger = LoggerUtils.getLogger(UnbindCardReq.class);

	/**
	 * 静态工厂方法：用来生成并初始化UnbindCardReq对象
	 */
	public static UnbindCardReq getInstanceFromJson(String json)
	{
		try
		{
			UnbindCardReq content = JsonUtils.fromJson(json, UnbindCardReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), UnbindCardReqData.class);
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
		if (StringUtils.isExistNullOrEmpty(getAppId(), getShortCardNo(), getBankId()) || getUserId() == null)
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

	public Integer getUserId()
	{
		return dataObj.getUserId();
	}

	public void setUserId(Integer userId)
	{
		dataObj.setUserId(userId);
	}

	public String getShortCardNo()
	{
		return dataObj.getShortCardNo();
	}

	public void setShortCardNo(String shortCardNo)
	{
		dataObj.setShortCardNo(shortCardNo);
	}

	public String getBankId()
	{
		return dataObj.getBankId();
	}

	public void setBankId(String bankId)
	{
		dataObj.setBankId(bankId);
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

	private static class UnbindCardReqData
	{
		private String appId;

		private Integer userId;

		private String shortCardNo;

		private String bankId;

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

		public String getShortCardNo()
		{
			return shortCardNo;
		}

		public void setShortCardNo(String shortCardNo)
		{
			this.shortCardNo = shortCardNo;
		}

		public String getBankId()
		{
			return bankId;
		}

		public void setBankId(String bankId)
		{
			this.bankId = bankId;
		}
	}
}