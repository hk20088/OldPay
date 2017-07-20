package com.newspace.payplatform.secretkey;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.secretkey.bo.SecretKeyBo;
import com.newspace.payplatform.secretkey.vo.SecretKeyRspVo;
import com.newspace.payplatform.secretkey.vo.SecretKeyVo;

/**
 * SecretKeyServiceServlet.java 
 * 描 述:  获取密钥接口：用于接收CP端传过来的 appId，生成密钥储存在数据库中，同时返回密钥给CP。  
 * 作 者:  liushuai
 * 历 史： 2014-4-22 创建
 */
public class SecretKeyServiceServlet extends BaseServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		SecretKeyVo reqVo = new SecretKeyVo();
		SecretKeyRspVo respVo = new SecretKeyRspVo();
		int returnCode = padRequestVo(request, reqVo);
		if (returnCode == ReturnCode.SUCCESS.getCode())
		{
			respVo = getBo(SecretKeyBo.class).doAppKey(reqVo);
		}
		else
		{
			respVo.setCode(ReturnCode.REQUEST_PARAM_ERROR.getCode());
		}
		outputResult(response, respVo);
	}
}