package com.newspace.payplatform.unicomhttp.param;

public class UnicomNotifyReq {

	private String orderNo;   //订单号
	  
	private String responeCode;   //计费结果 
	
	private String telephone;   //扣费手机号
	
	private String commandid;   //短信接入号

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getResponeCode() {
		return responeCode;
	}

	public void setResponeCode(String responeCode) {
		this.responeCode = responeCode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCommandid() {
		return commandid;
	}

	public void setCommandid(String commandid) {
		this.commandid = commandid;
	}

	
	
}
