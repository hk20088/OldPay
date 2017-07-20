package com.newspace.payplatform.syncdevice.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.syncdevice.bo.SyncDeviceBo;

/**
 * 同步设备信息Servlet
 * 1、后台设备审批完成后调用此接口将设备信息同步过来
 * 2、弱联网方式支付时通过deviceId查询渠道等信息，记录到订单表中。
 * @author huqili
 *
 */
public class SyncDeviceServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SyncDeviceReqVo reqVo = new SyncDeviceReqVo();
		SyncDeviceRespVo respVo = new SyncDeviceRespVo();
		int returnCode = padRequestVo(request, reqVo);
		
		if(returnCode == ReturnCode.SUCCESS.getCode()){
			
			respVo = getBo(SyncDeviceBo.class).saveDeviceInfo(reqVo);
		}else{
			
			respVo.setCode(ReturnCode.REQUEST_PARAM_ERROR.getCode());
		}
		outputResult(response, respVo);
	}

}
