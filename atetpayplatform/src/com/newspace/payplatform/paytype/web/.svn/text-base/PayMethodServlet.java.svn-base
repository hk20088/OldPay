package com.newspace.payplatform.paytype.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.paytype.bo.PayTypeBo;

/**
 * 获取某款设备所支持的支付方式
 * @author huqili
 *
 */
public class PayMethodServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		logger.info("\r\n进入获取设备支付方式接口...");
		
		PayMethodReqVo reqVo = new PayMethodReqVo();
		PayMethodRespVo respVo = new PayMethodRespVo();
		
		respVo.setCode(padRequestVo(req, reqVo));
		if(respVo.getCode() == ReturnCode.SUCCESS.getCode()){
			
			long beginTime = System.currentTimeMillis();
			respVo = getBo(PayTypeBo.class).getPayMethod(reqVo);
			long endTime = System.currentTimeMillis();
			logger.info(String.format("\r\n获取支付方式接口调用所耗时：%s ms，请求设备型号deviceID：%s",endTime - beginTime,reqVo.getDeviceId()));
		}
		
		outputResult(resp, respVo);
	}

}
