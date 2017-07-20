package com.newspace.payplatform.order.bo;

import com.newspace.common.bo.GenericBo;
import com.newspace.payplatform.order.dao.PaymentOrderDao;
import com.newspace.payplatform.order.vo.PaymentOrderVo;

/**
 * PaymentOrderBo.java 
 * 描 述:  PaymentOrderVo的Business层接口
 * 作 者:  liushuai
 * 历 史： 2014-4-26 创建
 */
public interface PaymentOrderBo extends GenericBo<PaymentOrderVo, PaymentOrderDao>
{
	/**
	 * 清除缓存，并返回对应数据库记录的最新状态
	 * @param vo 要清除缓存的PaymentOrderVo对象
	 * @return PaymentOrderVo 最新状态的vo对象
	 */
	public PaymentOrderVo evictCache(PaymentOrderVo vo);
	
	/**
	 * 检查订单是否异常接口
	 * @param vo
	 */
	public void checkAccount(PaymentOrderVo vo);
}
