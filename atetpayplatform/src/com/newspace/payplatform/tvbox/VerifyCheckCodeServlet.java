package com.newspace.payplatform.tvbox;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.bindtelephone.bo.BindTelephoneBo;
import com.newspace.payplatform.bindtelephone.vo.BindTelephoneVo;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.tvbox.param.VerifyCheckCodeReq;
import com.newspace.payplatform.tvbox.param.VerifyCheckCodeResp;

/**
 * VerifyCheckCodeServlet.java 
 * 描 述:  校验验证码接口，验证成功则盒子绑定手机号成功。  
 * 作 者:  liushuai
 * 历 史： 2014-7-21 创建
 */
@SuppressWarnings("serial")
public class VerifyCheckCodeServlet extends BaseServlet
{
	private static final BindTelephoneBo bindTelBo = SpringBeanUtils.getBean(BindTelephoneBo.class);

	/**
	 * 校验验证码是否输入正确，如果正确，就绑定成功(修改绑定状态)。
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		String requestStr = getStrFromRequest(request);
		VerifyCheckCodeReq req = VerifyCheckCodeReq.getInstanceFromJson(requestStr);
		VerifyCheckCodeResp resp = new VerifyCheckCodeResp();

		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());
		if (req != null && key != null && req.isLegal())
		{
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());
			if (returnCode == ReturnCode.SUCCESS.getCode())
			{
				returnCode = bindTelBo.verifyCheckCode(fillBindTel(req));
			}
		}

		// 记录失败日志
		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_VERIFY_CHECKCODE, req == null ? null : req.getData(), req == null ? null : req
					.getSign(), returnCode);
			resp.setDesc(ReturnCode.getDesc(returnCode));
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
	 * 生成BindTelephoneVo对象 
	 */
	private BindTelephoneVo fillBindTel(VerifyCheckCodeReq req)
	{
		BindTelephoneVo vo = new BindTelephoneVo();
		vo.setUserId(req.getUserId());
		vo.setAtetId(req.getAtetId());
		vo.setProductId(req.getProductId());
		vo.setDeviceCode(req.getDeviceCode());
		vo.setTelephone(req.getTelephone());
		vo.setExt(req.getCaptcha());
		return vo;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}