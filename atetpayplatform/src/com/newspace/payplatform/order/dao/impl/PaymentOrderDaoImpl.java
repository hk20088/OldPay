package com.newspace.payplatform.order.dao.impl;

import com.newspace.common.dao.impl.GenericDaoImpl;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.order.dao.PaymentOrderDao;
import com.newspace.payplatform.order.vo.PaymentOrderVo;

/**
 * PaymentOrderDaoImpl.java 
 * 描 述:  PaymentOrdeVo的Dao层实现类
 * 作 者:  liushuai
 * 历 史： 2014-4-26 创建
 */
public class PaymentOrderDaoImpl extends GenericDaoImpl<PaymentOrderVo> implements PaymentOrderDao
{
	/**
	 * 清除缓存
	 * @param vo 要清除的PaymentOrderVo对象
	 */
	public void evictCache(PaymentOrderVo vo)
	{
		if (null != vo && !StringUtils.isNullOrEmpty(vo.getId()))
		{
			getSession().evict(vo);
			getSessionFactory().evict(PaymentOrderVo.class, vo.getId());
		}
	}
}
