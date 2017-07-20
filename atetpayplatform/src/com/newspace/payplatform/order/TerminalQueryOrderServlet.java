package com.newspace.payplatform.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.order.bo.PaymentOrderBo;
import com.newspace.payplatform.order.bo.impl.PaymentOrderBoImpl;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.order.vo.TerminalQueryOrderReq;
import com.newspace.payplatform.order.vo.TerminalQueryOrderResp;
import com.newspace.payplatform.order.vo.TerminalQueryOrderResp.TerminalRespOrder;

/**
 * TerminalQueryOrderServlet.java 
 * 描 述:  终端查询订单信息接口  
 * 作 者:  liushuai
 * 历 史： 2014-6-9 创建
 */
@SuppressWarnings("serial")
public class TerminalQueryOrderServlet extends BaseServlet
{
	private static final PaymentOrderBo orderBo = SpringBeanUtils.getBean(PaymentOrderBoImpl.class);

	private static final JLogger logger = LoggerUtils.getLogger(TerminalQueryOrderServlet.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String data = getStrFromRequest(request);

		TerminalQueryOrderReq queryInfo = new TerminalQueryOrderReq();
		TerminalQueryOrderResp rspInfo = new TerminalQueryOrderResp();
		int returnCode = padRequestVo(data, queryInfo);

		if (returnCode == ReturnCode.SUCCESS.getCode())
		{
			if (isReqNull(queryInfo))
			{
				logger.error("终端查询订单信息接口_请求参数错误，userId atetId均为空！");
				returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
			}
			else
			{
				// 查询条件：如果userId存在，则根据userId查询，忽略掉atetId; 若userId不存在则根据atetId查询
				DetachedCriteria criteria = DetachedCriteria.forClass(PaymentOrderVo.class);
				criteria.add(Restrictions.eq("state", PaymentOrderVo.PAY_STATE_SUCCESS));
				if (queryInfo.getUserId() != null && queryInfo.getUserId() != 0)
				{
					criteria.add(Restrictions.eq("userId", queryInfo.getUserId()));
				}
				else
				{
					if (!StringUtils.isNullOrEmpty(queryInfo.getAtetId()))
						criteria.add(Restrictions.eq("atetId", queryInfo.getAtetId()));
				}
				List<PaymentOrderVo> list = orderBo.query(criteria);
				if (list.size() == 0)
					returnCode = ReturnCode.DATA_NOT_EXIST.getCode();
				for (PaymentOrderVo order : list)
				{
					TerminalRespOrder rspOrder = new TerminalRespOrder();
					rspOrder.setOrderNo(order.getOrderNo());
					if (!StringUtils.isNullOrEmpty(order.getCpOrderNo()))
						rspOrder.setCpOrderNo(order.getCpOrderNo());
					rspOrder.setPayPointName(order.getPayPointName());
					rspOrder.setCounts(order.getCounts());
					rspOrder.setAmount(order.getAmount());
					if (order.getDeviceType() != null)
						rspOrder.setDeviceType(order.getDeviceType());
					rspOrder.setAmountType(order.getAmountType());
					rspInfo.getData().add(rspOrder);
				}
			}
		}

		// 查询失败需要记录日志
		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_CP_QUERYORDER, data, null, returnCode);
		}

		// 输出响应
		rspInfo.setCode(returnCode);
		outputResult(response, rspInfo);
	}

	/**
	 * 判断QueryOrderReq对象是否为null或属性值都为空
	 */
	private boolean isReqNull(TerminalQueryOrderReq queryInfo)
	{
		if (queryInfo == null)
			return true;
		if (queryInfo.getUserId() == null && StringUtils.isNullOrEmpty(queryInfo.getAtetId()))
			return true;
		return false;
	}
}