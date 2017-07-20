package com.newspace.payplatform.order.vo;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.base.BaseResponseJsonVo;

/**
 * @describe 确认订单响应实体类
 * @author huqili
 * @date 2014年10月7日
 *
 */
public class ConfirmOrderResp {

	@Expose
	private String data;
	
	@Expose
	private String sign;
	
	VerifyOrderRespData dataObj = new VerifyOrderRespData();
	
	
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
	
	public void setData(String data) {
		this.data = data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public int getCode()
	{
		return dataObj.getCode();
	}

	public void setCode(int code)
	{
		dataObj.setCode(code);
	}
	
	/**
	 * 静态内部类：封闭data字符串对应的实体类
	 */
	private static class VerifyOrderRespData extends BaseResponseJsonVo
	{

		private static final long serialVersionUID = 1L;
		
	}
	
}
