package com.newspace.payplatform.paypoint.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * VerifyPaypointResp.java 
 * 描 述:  校验支付点响应参数实体类 
 * 作 者:  liushuai
 * 历 史： 2014-8-2 创建
 */
public class VerifyPaypointResp
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private VerifyPaypointRespData dataObj = new VerifyPaypointRespData();

	/**
	 * 获得对象Json串
	 */
	public String getJsonStr()
	{
		data = JsonUtils.toJson(dataObj);
		return JsonUtils.toJsonWithExpose(this);
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

	public int getCode()
	{
		return dataObj.getCode();
	}

	public void setCode(int code)
	{
		dataObj.setCode(code);
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

	private static class VerifyPaypointRespData
	{
		private int code;

		public int getCode()
		{
			return code;
		}

		public void setCode(int code)
		{
			this.code = code;
		}
	}
}
