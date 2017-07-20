package com.newspace.payplatform.paypoint.bo;

import com.newspace.common.bo.GenericBo;
import com.newspace.payplatform.paypoint.dao.PayPointDao;
import com.newspace.payplatform.paypoint.vo.PayPointVo;

/**
 * PayPointBo.java 
 * 描 述:  支付点Business接口
 * 作 者:  liushuai
 * 历 史： 2014-8-4 创建
 */
public interface PayPointBo extends GenericBo<PayPointVo, PayPointDao>
{
	/**
	 * 校验支付点信息是否正确
	 * @param appId 应用id
	 * @param payPoint 支付点
	 * @param counts 数量
	 * @param amount 金额
	 * @return returnCode 0：支付点信息正确。
	 */
	public abstract int verifyPaypoint(String appId, String payPoint, int counts, int amount);
}
