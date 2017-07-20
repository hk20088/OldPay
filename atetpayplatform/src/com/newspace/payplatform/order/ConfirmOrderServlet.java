package com.newspace.payplatform.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.order.vo.ConfirmOrderReq;
import com.newspace.payplatform.order.vo.ConfirmOrderResp;
import com.newspace.payplatform.order.vo.PaymentOrderVo;

/**
 * ConfirmOrderServlet.java 
 * 描 述:  确认订单接口，客户端接收到支付成功的通知后调用此接口通知服务器此订单已成功。
 * 作 者:  huqili
 * 历 史： 2014-10-07 创建
 */
@SuppressWarnings("serial")
public class ConfirmOrderServlet extends BaseServlet
{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ConfirmOrderResp resp = new ConfirmOrderResp();
		String reqStr = getStrFromRequest(request);
		ConfirmOrderReq req = ConfirmOrderReq.getInstanceFromJson(reqStr);
		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());

		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		if (req != null && !StringUtils.isNullOrEmpty(key))
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());
		
		if (returnCode == ReturnCode.SUCCESS.getCode())
		{
			// 使用订单号进行查询
			if (!StringUtils.isNullOrEmpty(req.getOrderNo()))
			{
				PaymentOrderVo orderVo = PayUtils.queryByOrderNo(req.getOrderNo(),Boolean.FALSE);
				if (orderVo != null){
					orderVo.setIsSendGood(PaymentOrderVo.IS_SEND_GOOD);
					returnCode = PayUtils.saveOrUpdateOrder(orderVo);
				}else{
					returnCode = ReturnCode.DATA_NOT_EXIST.getCode();
				}
				
			}else{
				returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
			}
		}

		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_TERMINAL_QUERYORDER, reqStr, req.getSign(), returnCode);
		}

		resp.setCode(returnCode);
		if (StringUtils.isNullOrEmpty(key))
			resp.setSign(PayUtils.WRONG_SIGN);
		else
			resp.setSign(PayUtils.getSignByAESandMD5(resp.getData(), key));
		
		outputResult(response, resp.getJsonStr());
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}