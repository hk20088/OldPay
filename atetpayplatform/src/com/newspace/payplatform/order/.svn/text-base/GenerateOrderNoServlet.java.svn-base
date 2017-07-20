package com.newspace.payplatform.order;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.utils.IPUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.order.vo.GenerateOrderNoReq;
import com.newspace.payplatform.order.vo.GenerateOrderNoResp;
import com.newspace.payplatform.order.vo.PaymentOrderVo;

/**
 * GenerateOrderNoServlet.java 
 * 描 述:  生成订单号接口  
 * 历 史： 2014-7-11 创建
 */
@SuppressWarnings("serial")
public class GenerateOrderNoServlet extends BaseServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		GenerateOrderNoResp resp = new GenerateOrderNoResp();
		PaymentOrderVo orderVo = null;
		String reqStr = getStrFromRequest(request);
		GenerateOrderNoReq req = GenerateOrderNoReq.getInstanceFromJson(reqStr);
		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());

		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		if (req != null && !StringUtils.isNullOrEmpty(key))
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());

		if (returnCode == ReturnCode.SUCCESS.getCode())
		{
			String orderNo = PaymentOrderVo.generateOrderNo();
			if (StringUtils.isNullOrEmpty(orderNo)){
				returnCode = ReturnCode.ERROR.getCode();
			}else{
				//将订单号等信息记录到数据库中。
				orderVo = generateOrderObj(req);
				orderVo.setOrderNo(orderNo);
				orderVo.setClientIP(IPUtils.getRemoteAddress(request));
				returnCode = PayUtils.saveOrUpdateOrder(orderVo);
				if(returnCode == ReturnCode.SUCCESS.getCode())
					resp.setOrderNo(orderNo);
			}
		}

		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_GENERATE_ORDERNO, reqStr, null, returnCode);
		}

		resp.setCode(returnCode);
		if (StringUtils.isNullOrEmpty(key))
			resp.setSign(PayUtils.WRONG_SIGN);
		else
			resp.setSign(PayUtils.getSignByAESandMD5(resp.getData(), key));

		// 输出响应
		outputResult(response, resp.getJsonStr());
	}

	/**
	 * 生成订单对象 
	 */
	private PaymentOrderVo generateOrderObj(GenerateOrderNoReq req)
	{
		PaymentOrderVo vo = new PaymentOrderVo();
		vo.setUserId(0);
		vo.setAppId(req.getAppId());
		vo.setAtetId(req.getAtetId());
		vo.setDeviceId(req.getDeviceId());
		vo.setChannelId(req.getChannelId());
		vo.setDeviceType(req.getDeviceType());
		vo.setVersionCode(req.getVersionCode());
		vo.setState(PaymentOrderVo.PAY_STATE_NONPROCESS);
		vo.setCreateTime(new Timestamp(new Date().getTime()));
		return vo;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}