package com.newspace.payplatform.paypoint.bo.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.bo.impl.GenericBoImpl;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.paypoint.bo.PayPointBo;
import com.newspace.payplatform.paypoint.dao.PayPointDao;
import com.newspace.payplatform.paypoint.vo.PayPointVo;

/**
 * PayPointBoImpl.java 
 * 描 述:  支付点Business类  
 * 作 者:  huqili
 * 历 史： 2014年11月6日　修改
 */
public class PayPointBoImpl extends GenericBoImpl<PayPointVo, PayPointDao> implements PayPointBo
{
	/**
	 * 校验支付点信息是否正确
	 * @param appId 应用id
	 * @param payPoint 支付点
	 * @param counts 数量
	 * @param amount 金额
	 * @return returnCode 0：支付点信息正确。
	 */
	@Override
	public int verifyPaypoint(String appId, String payPoint, int counts, int amount)
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(PayPointVo.class);
			criteria.add(Restrictions.eq("appId", appId));
			criteria.add(Restrictions.eq("payCode", payPoint));
			List<PayPointVo> list = query(criteria);

			if (list != null && list.size() > 0)
			{
				logger.info(String.format("支付点单价：%s,总金额：%s", list.get(0).getMoney(),amount));
				if (list.get(0).getMoney() * counts == amount) // payPoint中getMoney的单位是也是　分。
					return ReturnCode.SUCCESS.getCode();
				return ReturnCode.PAYPOINT_MONEY_NOT_EQUAL.getCode();
			}
			return ReturnCode.DATA_NOT_EXIST.getCode();
		}
		catch (Exception e)
		{
			logger.error("校验支付点失败", e);
		}
		return ReturnCode.ERROR.getCode();
	}
}