package com.newspace.payplatform.paytype.bo;

import java.util.List;

import com.newspace.common.bo.GenericBo;
import com.newspace.payplatform.paytype.dao.PayTypeDao;
import com.newspace.payplatform.paytype.vo.PayTypeVo;
import com.newspace.payplatform.paytype.web.PayMethodReqVo;
import com.newspace.payplatform.paytype.web.PayMethodRespVo;

public interface PayTypeBo extends GenericBo<PayTypeVo, PayTypeDao>{

	/**
	 * 获取设备所支持的支付方式
	 * @param reqVo
	 * @return
	 */
	public PayMethodRespVo getPayMethod(PayMethodReqVo reqVo);
	
	/**
	 * 获取没有被冻结的支付方式
	 * @return
	 */
	public List<PayTypeVo> getPayTypes();
}
