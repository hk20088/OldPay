package com.newspace.payplatform.qmshortcut.observer;

import java.util.Observable;
import java.util.Observer;

import com.newspace.common.utils.TwoTuple;

/**
 * TR3Observer.java 
 * 描 述:  TR3消息的观察者实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-7 创建
 */
public class TR3Observer implements Observer
{
	/**
	 * 是否已经接收到TR3消息
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
	 * 构造函数
	 */
	public TR3Observer(String orderNo)
	{
		this.orderNo = orderNo;
	}

	/**
	 * 处理接收到到的通知
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable subject, Object arg)
	{
		TwoTuple<String, String> tuple = (TwoTuple<String, String>) arg;
		if (orderNo.equals(tuple.first))
		{
			this.isReceived = true;
			this.responseCode = tuple.second;
			synchronized (this)
			{
				this.notifyAll();
			}
		}
	}

	public String getResponseCode()
	{
		return responseCode;
	}

	public void setResponseCode(String responseCode)
	{
		this.responseCode = responseCode;
	}

	public boolean isReceived()
	{
		return isReceived;
	}

	public String getOrderNo()
	{
		return orderNo;
	}
}