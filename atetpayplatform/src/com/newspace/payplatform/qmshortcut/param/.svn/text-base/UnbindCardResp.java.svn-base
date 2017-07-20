package com.newspace.payplatform.qmshortcut.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * UnbindCardResp.java  
 * 描 述:  解绑银行卡信息响应实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-12 创建
 */
public class UnbindCardResp
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private UnbindCardRespData dataObj = new UnbindCardRespData();

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

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public Integer getCode()
	{
		return dataObj.getCode();
	}

	public void setCode(Integer code)
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

	private static class UnbindCardRespData
	{
		private Integer code;

		private String desc;

		public Integer getCode()
		{
			return code;
		}

		public void setCode(Integer code)
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
