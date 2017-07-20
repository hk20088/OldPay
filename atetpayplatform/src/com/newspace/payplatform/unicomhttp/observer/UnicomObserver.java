package com.newspace.payplatform.unicomhttp.observer;

import java.util.Observable;
import java.util.Observer;

import com.newspace.payplatform.unicomhttp.param.UnicomNotifyReq;

/**
 * {@link UnicomObserver.java}
 * @description: 观察者类，当主题改变时会通知此观察者做出相应的更新。
 * 					在此项目中，联通平台的异步通知为主题对象，当接收到通知结果时就通知联通计费接口，
 * 					计费接口根据计费结果做出相应的业务逻辑处理。
 * @author Huqili
 * @date 2015年4月27日
 *
 */
public class UnicomObserver implements Observer{

	/**
	 * 是否已经接收到主题对象通知消息
	 */
	private volatile boolean isReceived = false;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 响应码
	 */
	private String responseCode;
	
	/**
	 * 扣费手机号
	 */
	private String telephone;
	
	/**
	 * 短信接入号
	 */
	private String commandid;
	
	/**
	 * 构造函数
	 */
	public UnicomObserver(String orderNo){
		this.orderNo = orderNo;
	}
	
	/**
	 * 只要改变了 observable 对象就调用此方法。
	 * 当调用 Observable 对象的 notifyObservers 方法，就向所有该对象的观察者通知此改变。
	 */
	@Override
	public void update(Observable subject, Object obj) {
		System.out.println("update called...");
		UnicomNotifyReq reqVo = (UnicomNotifyReq) obj;
		if(orderNo.equals(reqVo.getOrderNo()))
		{
			this.isReceived = true;
			this.responseCode = reqVo.getResponeCode();
			this.telephone = reqVo.getTelephone();
			this.commandid = reqVo.getCommandid();
			
			//唤起在此对象监视器上等待的所有线程。
			synchronized (this)
			{
				this.notifyAll();
			}
		}
		
	}

	public boolean isReceived() {
		return isReceived;
	}

	public void setReceived(boolean isReceived) {
		this.isReceived = isReceived;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
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
