package com.newspace.payplatform.unicomsms.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.base.BaseResponseJsonVo;

/**
 * MsgPaymentDownstreamContent.java 
 * 描 述:  短信支付下行短信内容
 * 作 者:  liushuai
 * 历 史： 2014-7-20 创建
 */
public class MsgPaymentDownstreamContent
{
	/**
	 * 短信内容数据data
	 */
	@Expose
	private String data;

	/**
	 * 短信内容数据data加密产生的签名
	 */
	@Expose
	private String sign;

	private MsgPaymentDownstreamData dataObj = new MsgPaymentDownstreamData();

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

	/**
	 * 静态内部类：短信内容中data对应的实体类
	 */
	private static class MsgPaymentDownstreamData extends BaseResponseJsonVo
	{
		private static final long serialVersionUID = 1L;
	}
}
