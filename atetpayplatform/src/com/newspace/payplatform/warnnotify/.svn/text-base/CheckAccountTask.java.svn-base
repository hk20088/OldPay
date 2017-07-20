package com.newspace.payplatform.warnnotify;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.payplatform.order.bo.PaymentOrderBo;
import com.newspace.payplatform.order.vo.PaymentOrderVo;


/**
 * CheckAccountTack.java
 * @describe 检查此订单是否异常的任务
 * @author huqili
 * @date 2014年11月12日
 */
public class CheckAccountTask implements Runnable{

	private static final PaymentOrderBo orderBo = SpringBeanUtils.getBean(PaymentOrderBo.class);
	
	private PaymentOrderVo order;
	
	public CheckAccountTask(PaymentOrderVo order){
	
		this.order = order;
	}
	
	@Override
	public void run() {

		orderBo.checkAccount(order);
	}

}
