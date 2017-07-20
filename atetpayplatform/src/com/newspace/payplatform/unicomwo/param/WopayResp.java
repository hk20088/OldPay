package com.newspace.payplatform.unicomwo.param;

import com.google.gson.annotations.Expose;
import com.newspace.payplatform.base.JsonVo;

public class WopayResp implements JsonVo{

	private static final long serialVersionUID = 1L;
	
	@Expose
	private String messCode;
	
	@Expose
	private String res_code;
	
	@Expose
	private String res_desc;
	
	private String transaction_id;  //联通WO邮箱流水号
	
	
	public String getMessCode() {
		return messCode;
	}
	public void setMessCode(String messCode) {
		this.messCode = messCode;
	}
	public String getRes_code() {
		return res_code;
	}
	public void setRes_code(String resCode) {
		res_code = resCode;
	}
	public String getRes_desc() {
		return res_desc;
	}
	public void setRes_desc(String resDesc) {
		res_desc = resDesc;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transactionId) {
		transaction_id = transactionId;
	}
	
}
