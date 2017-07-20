package com.newspace.payplatform.unicomwo.param;

import java.util.List;

import com.newspace.payplatform.base.JsonVo;

/**
 * @description 沃邮箱计费轨迹图
 * @author huqili
 * @since JDK1.6
 * 
 */
public class WoTraceResp implements JsonVo{

	private static final long serialVersionUID = 1L;
	private String resultCode;
	private String resultDescription;
	private String transactionId;
	private List<traceInfos> traceInfos;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDescription() {
		return resultDescription;
	}

	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public List<traceInfos> getTraceInfos() {
		return traceInfos;
	}

	public void setTraceInfos(List<traceInfos> traceInfos) {
		this.traceInfos = traceInfos;
	}

	/**
	 * 静态内部类，用来封装用户的轨迹图
	 * 
	 * @author huqili
	 * 
	 */
	public static class traceInfos {
		
		private String time;
		
		  /**操作
		   *1、用户获取支付验证码（说明：用户确认发起计费验证请求）
		   *2、平台向用户下发验证码短信（说明：平台下发计费需用户回填的验证码）
		   *3、用户确认支付（说明：用户在应用内填写验证码并确认计费）
		   *4、计费成功通知短信（说明：平台向用户下发计费成功通知结果）
		  **/
		private String action; 
		private String smsFromTo;  //短信发送方号码到接收方号码，示例：18616917263->106477003
		private String smsContent;  // 用户发送给平台或平台发送给用户的短信内容
		private String messageContext;  //消息内容，无短信内容时，此项必填
		private String description;

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getAction() {
			return action;
		}

		public void setAction(String action) {
			this.action = action;
		}

		public String getSmsFromTo() {
			return smsFromTo;
		}

		public void setSmsFromTo(String smsFromTo) {
			this.smsFromTo = smsFromTo;
		}

		public String getSmsContent() {
			return smsContent;
		}

		public void setSmsContent(String smsContent) {
			this.smsContent = smsContent;
		}

		public String getMessageContext() {
			return messageContext;
		}

		public void setMessageContext(String messageContext) {
			this.messageContext = messageContext;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}
}
