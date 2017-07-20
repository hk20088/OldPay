package com.newspace.payplatform.unicomwo.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.base.JsonVo;

public class WopayReq implements JsonVo {

	private static final long serialVersionUID = 1L;

	@Expose
	private String phoneNum;  //加密的手机号

	@Expose
	private String channelId;  //业务代码
	private String phone;
	private String orderId;   //渠道的订单号
	private String corpId;    //企业ID，用来区分渠道

	/**
	 * 校验请求参数是否合法
	 * 
	 * @return
	 */
	public boolean isLegal() {
		if (StringUtils.isExistNullOrEmpty(getPhone(), getChannelId(),
				getOrderId()))
			return false;
		return true;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

}