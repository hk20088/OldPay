package com.newspace.payplatform.unicomsms.param;

import com.newspace.payplatform.base.JsonVo;

/**
 * SendMsgReq.java
 * @description 下发短信请求参数类
 * @author huqili
 * @date 2014年11月21日
 *
 */
public class SendMsgReq implements JsonVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 接收短信的手机号，如果有多个，会用英文逗号隔开。
	 */
	private String telephone;
	
	/**
	 * 下发的短信内容
	 */
	private String msgContent;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
}
