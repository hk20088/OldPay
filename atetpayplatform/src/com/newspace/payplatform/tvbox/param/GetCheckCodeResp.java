package com.newspace.payplatform.tvbox.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * GetCheckCodeResp.java 
 * 描 述:  获取验证码接口响应实体类
 * 作 者:  liushuai
 * 历 史： 2014-7-21 创建
 */
public class GetCheckCodeResp
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private GetCheckCodeRespData dataObj = new GetCheckCodeRespData();

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

	public String getDesc()
	{
		return dataObj.getDesc();
	}

	public void setDesc(String desc)
	{
		dataObj.setDesc(desc);
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
	private static class GetCheckCodeRespData
	{
		private int code;

		private String desc;

		public int getCode()
		{
			return code;
		}

		public void setCode(int code)
		{
			this.code = code;
		}

		public String getDesc()
		{
			return desc;
		}

		public void setDesc(String desc)
		{
			this.desc = desc;
		}
	}
}
