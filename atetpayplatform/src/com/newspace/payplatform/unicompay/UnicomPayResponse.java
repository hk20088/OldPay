package com.newspace.payplatform.unicompay;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.base.BaseResponseJsonVo;
import com.newspace.payplatform.base.JsonVo;

/**
 * UnicomPayResponse.java 
 * 描 述:  联通VAC支付响应结果  
 * 作 者:  liushuai
 * 历 史： 2014-7-17 创建
 */
public class UnicomPayResponse implements JsonVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 响应数据
	 */
	@Expose
	private String data;

	/**
	 * 对响应数据加密产生的签名
	 */
	@Expose
	private String sign;

	/**
	 * 响应数据对象
	 */
	private UnicomPayRespData dataObj = new UnicomPayRespData();

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

	public String getDesc()
	{
		return dataObj.getDesc();
	}

	public void setDesc(String desc)
	{
		dataObj.setDesc(desc);
	}

	public String getOrderNo()
	{
		return dataObj.getOrderNo();
	}

	public void setOrderNo(String orderNo)
	{
		dataObj.setOrderNo(orderNo);
	}

	public int getCode()
	{
		return dataObj.getCode();
	}

	public void setCode(int code)
	{
		dataObj.setCode(code);
	}

	
	private static class UnicomPayRespData extends BaseResponseJsonVo
	{
		private static final long serialVersionUID = 1L;

		/**
		 * 描述
		 */
		private String desc;

		/**
		 * 订单号
		 */
		private String orderNo;
		
		public String getDesc()
		{
			return desc;
		}

		public void setDesc(String desc)
		{
			this.desc = desc;
		}

		public String getOrderNo()
		{
			return orderNo;
		}

		public void setOrderNo(String orderNo)
		{
			this.orderNo = orderNo;
		}

	
	}
}