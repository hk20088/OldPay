package com.newspace.payplatform.order.vo;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * @describe 确认订单请求实体类
 * @author huqili
 * @date 2014年10月7日
 *
 */
public class ConfirmOrderReq {

	@Expose
	private String data;
	
	@Expose
	private String sign;
	
	VerifyOrderReqData dataObj = new VerifyOrderReqData();
	
	private static final JLogger logger = LoggerUtils.getLogger(ConfirmOrderReq.class);
	
	/**
	 * 静态工厂方法，初始化verifyOrderReq对象
	 * @param json
	 * @return
	 */
	public static ConfirmOrderReq getInstanceFromJson(String json){
		try {
			ConfirmOrderReq content = JsonUtils.fromJson(json, ConfirmOrderReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), VerifyOrderReqData.class);
			return content;
		} catch (Exception e) {
			logger.error("VerifyOrderReq 解析json字符串失败!\r\njson: " + json, e);
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
	
	public void setData(String data) {
		this.data = data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getOrderNo() {
		return dataObj.getOrderNo();
	}

	public void setOrderNo(String orderNo) {
		dataObj.setOrderNo(orderNo);
	}

	public String getAppId() {
		return dataObj.getAppId();
	}

	public void setAppId(String appId) {
		dataObj.setAppId(appId);
	}
	
	/**
	 * 静态内部类：封闭data字符串对应的实体类
	 */
	private static class VerifyOrderReqData
	{
		private String orderNo;

		private String appId;

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}
		
		
	}
	
}
