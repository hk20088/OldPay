package com.newspace.payplatform.unicomwo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.unicomwo.param.WoTraceReq;
import com.newspace.payplatform.unicomwo.param.WoTraceResp;
import com.newspace.payplatform.unicomwo.utils.TraceUtils;
import com.newspace.payplatform.unicomwo.vo.WoTraceVo;

/**
 * @description 用于查询每笔交易轨迹的servlet
 * @author huqili
 * @date 2016年7月12日
 *
 */
public class WoTraceServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	private static final JLogger logger = LoggerUtils.getLogger(WoTraceServlet.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		logger.info("进入查询沃邮箱交易轨迹接口...");
		
		WoTraceReq reqVo = new WoTraceReq();
		WoTraceResp respVo = new WoTraceResp();
		WoTraceVo woTrace = null;
		
		String reqStr = getStrFromRequest(request);
		int returnCode = padRequestVo(reqStr, reqVo);
		
		if(returnCode == ReturnCode.SUCCESS.getCode())
		{
			try 
			{
				woTrace = TraceUtils.getTrace(reqVo.getTransactionId());
				if(woTrace == null)
				{
					logger.error("联通沃邮箱交易轨迹接口_未查询出对应的信息...");
					returnCode = ReturnCode.NOT_EXIST_MATCHED_INFO.getCode();
				}
			}
			catch (Exception e) 
			{
				logger.error("联通沃邮箱交易轨迹接口出现异常...");
				returnCode = ReturnCode.ERROR.getCode();
			}
		}
		else
		{
			logger.error("联通沃邮箱交易轨迹接口请求参数不合法...");
			returnCode = ReturnCode.ERROR.getCode();
		}
		
		//根据状态返回响应信息
		if(returnCode == ReturnCode.SUCCESS.getCode())
		{
			outputResult(response, woTrace.getTrace());
		}
		else
		{
			respVo.setResultCode(String.valueOf(returnCode));
			respVo.setResultDescription(ReturnCode.getDesc(returnCode));
			
			outputResult(response, respVo);
		}
		
		
	}

}
