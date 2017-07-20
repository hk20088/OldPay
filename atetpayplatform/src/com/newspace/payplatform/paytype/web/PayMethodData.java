package com.newspace.payplatform.paytype.web;

import com.newspace.payplatform.base.JsonVo;

public class PayMethodData implements JsonVo{

	private static final long serialVersionUID = 1L;

	private String paymethodCode;

	public String getPaymethodCode() {
		return paymethodCode;
	}

	public void setPaymethodCode(String paymethodCode) {
		this.paymethodCode = paymethodCode;
	}
	
}
