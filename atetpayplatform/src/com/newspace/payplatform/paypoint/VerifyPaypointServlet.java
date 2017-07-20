package com.newspace.payplatform.paypoint;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.paypoint.bo.PayPointBo;
import com.newspace.payplatform.paypoint.param.VerifyPaypointReq;
import com.newspace.payplatform.paypoint.param.VerifyPaypointResp;

/**
 * VerifyPaypointServlet.java 
 * 描 述:  验证支付点接口  
 * 作 者:  liushuai
 * 历 史： 2014-8-2 创建
 */
@SuppressWarnings("serial")
public class VerifyPaypointServlet extends BaseServlet
{
	private static final PayPointBo payPointBo = SpringBeanUtils.getBean(PayPointBo.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		String requestStr = getStrFromRequest(request);
		VerifyPaypointReq req = VerifyPaypointReq.getInstanceFromJson(requestStr);
		VerifyPaypointResp resp = new VerifyPaypointResp();

		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());
		// 校验支付点信息
		if (req != null && req.isLegal())
		{
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());
			if (returnCode == ReturnCode.SUCCESS.getCode())
				returnCode = payPointBo.verifyPaypoint(req.getAppId(), req.getPayPoint(), req.getCounts(), req.getAmount());
		}

		// 记录失败日志
		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_VERIFY_PAYPOINT, req == null ? null : req.getData(), req == null ? null : req
					.getSign(), returnCode);
		}

		resp.setCode(returnCode);
		if (StringUtils.isNullOrEmpty(key))
			resp.setSign(PayUtils.WRONG_SIGN);
		else
			resp.setSign(PayUtils.getSignByAESandMD5(resp.getData(), key));

		// 输出响应
		outputResult(response, resp.getJsonStr());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}