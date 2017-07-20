package com.newspace.payplatform.secretkey.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseResponseJsonVo;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.secretkey.bo.SecretKeyBo;

/**
 * SyncCpInfoServlet.java 
 * 描 述:  同步CP信息  
 * 作 者:  Huqili
 * 历 史： 2014-4-28 创建
 */
public class SyncCpInfoServlet extends BaseServlet
{
	private static final long serialVersionUID = 1L;
	
	private static final JLogger logger = LoggerUtils.getLogger(SyncCpInfoServlet.class);

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		logger.info("进入同步CP信息接口...");
		CpInfoReqVo reqVo = new CpInfoReqVo();
		BaseResponseJsonVo respVo = new BaseResponseJsonVo();
		String reqStr = getStrFromRequest(req);
		int returnCode = padRequestVo(reqStr, reqVo);
		if (returnCode == ReturnCode.SUCCESS.getCode())
		{
			respVo = getBo(SecretKeyBo.class).saveCpInfo(reqVo);
		}
		else
		{
			respVo.setCode(ReturnCode.REQUEST_PARAM_ERROR.getCode());
		}
		outputResult(resp, respVo);
	}
}