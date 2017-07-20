package com.newspace.payplatform.secretkey.web;

import com.newspace.common.vo.BaseVo;
import com.newspace.payplatform.base.JsonVo;

public class PayPointList extends BaseVo implements JsonVo {
	private static final long serialVersionUID = 1L;

	private String payCode;

	private String payName;

	private Double money;

	private int state;

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String logString() {
		return null;
	}
}
