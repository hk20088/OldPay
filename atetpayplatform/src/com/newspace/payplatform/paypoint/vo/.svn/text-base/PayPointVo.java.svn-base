package com.newspace.payplatform.paypoint.vo;

import java.util.Date;

import com.newspace.common.vo.BaseVo;

/**
 * PayPointVo.java 描 述: 支付点Vo类 作 者: liushuai 历 史： 2014-7-15 创建
 */
public class PayPointVo extends BaseVo {
	private static final long serialVersionUID = 1L;

	/**
	 * 应用、游戏 id
	 */
	private String appId;

	/**
	 * 支付点
	 */
	private String payCode;

	/**
	 * 支付点名称
	 */
	private String payName;

	/**
	 * 支付点单价，单价：分
	 */
	private Double money;

	/**
	 * 支付点状态 0代表正常 1代表删除
	 */
	private Integer state;

	/**
	 * 同步时间
	 */
	private Date syncTime;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	@Override
	public String logString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{id: ").append(id);
		sb.append(", appId: ").append(appId);
		sb.append(", payCode: ").append(payCode);
		sb.append(", money: ").append(money).append(" }");
		return sb.toString();
	}
}