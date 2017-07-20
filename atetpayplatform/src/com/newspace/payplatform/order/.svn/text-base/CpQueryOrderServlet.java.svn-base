package com.newspace.payplatform.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.order.bo.PaymentOrderBo;
import com.newspace.payplatform.order.vo.CpQueryOrderReq;
import com.newspace.payplatform.order.vo.CpQueryOrderResp;
import com.newspace.payplatform.order.vo.PaymentOrderVo;

/**
 * CpQueryOrderServlet.java 
 * 描 述:  用于CP查询订单支付情况的接口
 * 作 者:  liushuai
 * 历 史： 2014-7-14 创建
 */
@SuppressWarnings("serial")
public class CpQueryOrderServlet extends BaseServlet
{
	private static final PaymentOrderBo orderBo = SpringBeanUtils.getBean(PaymentOrderBo.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		CpQueryOrderReq reqVo = new CpQueryOrderReq();
		CpQueryOrderResp rspVo = new CpQueryOrderResp();

		String receivedMsg = getStrFromRequest(request);
		int returnCode = padRequestVo(receivedMsg, reqVo);

		if (returnCode == ReturnCode.SUCCESS.getCode())
		{
			// 使用订单号进行查询
			if (!StringUtils.isNullOrEmpty(reqVo.getOrderNo()))
			{
				DetachedCriteria criteria = DetachedCriteria.forClass(PaymentOrderVo.class);
				if (!StringUtils.isNullOrEmpty(reqVo.getOrderNo()))
					criteria.add(Restrictions.eq("orderNo", reqVo.getOrderNo()));
				PaymentOrderVo orderVo = orderBo.uniqueResult(criteria);
				if (orderVo != null)
					genRespVo(orderVo, rspVo);
				else
					returnCode = ReturnCode.DATA_NOT_EXIST.getCode();
			}
			else
			{
				returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
			}
		}

		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_TERMINAL_QUERYORDER, receivedMsg, null, returnCode);
		}

		rspVo.setCode(returnCode);
		outputResult(response, rspVo);
	}

	/**
	 * 私有方法：生成响应对象内容
	 * @param orderVo 订单vo类
	 * @param rspVo  CP查询接口响应vo类
	 */
	private void genRespVo(PaymentOrderVo orderVo, CpQueryOrderResp rspVo)
	{
		rspVo.setOrderNo(orderVo.getOrderNo());
		rspVo.setPayPointName(orderVo.getPayPointName());
		rspVo.setCounts(orderVo.getCounts());
		rspVo.setAmount(orderVo.getAmount());
		rspVo.setAmountType(orderVo.getAmountType());
		rspVo.setState(orderVo.getState());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}