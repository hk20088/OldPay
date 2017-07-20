package com.newspace.payplatform.qmshortcut.observer;

import java.util.Observable;

import com.newspace.common.utils.TwoTuple;

/**
 * TR3Observer.java 
 * 描 述:  接收TR3消息的主题对象  
 * 作 者:  liushuai
 * 历 史： 2014-8-7 创建
 */
public class TR3Subject extends Observable
{
	/**
	 * 处理接收到的信息 
	 */
	public void handlerReceivedMsg(String orderNo, String responseCode)
	{
		TwoTuple<String, String> tuple = new TwoTuple<String, String>(orderNo, responseCode);
		setChanged();
		notifyObservers(tuple);
	}
}
