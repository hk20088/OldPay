package com.newspace.payplatform.unicomhttp.observer;

import java.util.Observable;

import com.newspace.payplatform.unicomhttp.param.UnicomNotifyReq;

/**
 * {@link UnicomSubject.java}
 * @description: 被观察者类（主题对象）： 一个 observable 对象可以有一个或多个观察者。观察者可以是实现了 Observer 接口的任意对象。
 * 					一个 observable 实例改变后，调用 Observable 的 notifyObservers 方法的应用程序会通过调用观察者的 update 方法来通知观察者该实例发生了改变。
 * @author huqili
 * @date 2015年4月27日
 *
 */
public class UnicomSubject extends Observable{

	/**
	 * 处理接收到的通知
	 * @param orderNo
	 * @param responeCode
	 */
	public void handlerMsg(UnicomNotifyReq reqVo)
	{
		setChanged();
		notifyObservers(reqVo);
	}
	
}
