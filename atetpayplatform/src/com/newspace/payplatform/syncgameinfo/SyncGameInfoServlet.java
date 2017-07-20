package com.newspace.payplatform.syncgameinfo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseResponseJsonVo;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.syncgameinfo.bo.SyncGameBo;
import com.newspace.payplatform.syncgameinfo.vo.SyncGameReq;

/**
 * SyncGameInfoServlet.java 
 * 描 述:  同步游戏信息接口  
 * 作 者:  liushuai
 * 历 史： 2014-9-16 创建
 */
@SuppressWarnings("serial")
public class SyncGameInfoServlet extends BaseServlet
{
	private static final SyncGameBo syncGameBo = SpringBeanUtils.getBean(SyncGameBo.class);

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		SyncGameReq req = new SyncGameReq();
		int returnCode = padRequestVo(request, req);

		if (returnCode == ReturnCode.SUCCESS.getCode() && req.isLegal())
			returnCode = syncGameBo.saveGameInfo(req);
		else
			returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();

		BaseResponseJsonVo respVo = new BaseResponseJsonVo();
		respVo.setCode(returnCode);

		outputResult(response, respVo);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}
