package com.newspace.payplatform.unicomwo.vo;

import java.sql.Timestamp;

import com.newspace.common.vo.BaseVo;

/**
 * @description 沃邮箱交易轨迹实体类
 * @author huqili
 * @date 2016年7月12日
 * 
 */
public class WoTraceVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	private String transactionId;
	private String phone;
	private String trace;
	private Timestamp createTime;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String logString() {
		return null;
	}
}
