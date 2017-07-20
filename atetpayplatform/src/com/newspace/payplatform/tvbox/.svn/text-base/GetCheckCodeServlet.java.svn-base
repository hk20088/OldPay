package com.newspace.payplatform.tvbox;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.PropertiesUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.bindtelephone.bo.BindTelephoneBo;
import com.newspace.payplatform.bindtelephone.vo.BindTelephoneVo;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.tvbox.param.GetCheckCodeReq;
import com.newspace.payplatform.tvbox.param.GetCheckCodeResp;
import com.newspace.payplatform.unicomhttp.HttpSendMsgTask;

/**
 * GetCheckCodeServlet.java 
 * 描 述:  盒子支付，获取验证码的接口。				<br/>
 * 			1、生成六位随机验证码;					<br/>
 * 			2、将请求参数和随机验证码存入数据库中;		<br/>
 * 			3、调用下行短信接口，将验证码发送到手机;	<br/>
 * 作 者:  liushuai
 * 历 史： 2014-7-21 创建
 */
@SuppressWarnings("serial")
public class GetCheckCodeServlet extends BaseServlet
{
	private static final BindTelephoneBo bindTelBo = SpringBeanUtils.getBean(BindTelephoneBo.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();

	private static final String NOTICE_MSG;

	static
	{
		Properties prop = PropertiesUtils.getProps("configuration");
		NOTICE_MSG = prop.getProperty("checkcode_notice_msg");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		String requestStr = getStrFromRequest(request);
		GetCheckCodeReq req = GetCheckCodeReq.getInstanceFromJson(requestStr);
		GetCheckCodeResp resp = new GetCheckCodeResp();
		String checkCode = getRandomNumber();
		String checkCodeMsg = "校验码：" + checkCode + NOTICE_MSG;

		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());

		if (key != null && req != null && req.isLegal())
		{
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());
			if (returnCode == ReturnCode.SUCCESS.getCode())
			{
				returnCode = bindTelBo.bindTelephone(fillBindTel(req, checkCode));
			}
		}

		if (returnCode == ReturnCode.SUCCESS.getCode())
		{   // 发送短信
			//银贝壳接口
//			if(ConstantData.SERVER_IP.equals(ConstantData.UNICOM_SMS_SERVER_IP)){
//				pool.execute(new SendMsgTask(req.getTelephone(),checkCodeMsg));
//			}else{
//				pool.execute(new AssignSendMsgTask(req.getTelephone(), checkCodeMsg));
//			}
			//游戏中心接口
			pool.execute(new HttpSendMsgTask(req.getTelephone(), checkCodeMsg));
		}
		else
		{ // 记录日志
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_GET_CHECKCODE, req == null ? null : req.getData(), req == null ? null : req
					.getSign(), returnCode, resp.getDesc());
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
	 * 生成六位随机码作为校验码
	 */
	private String getRandomNumber()
	{
		int num = (int) (Math.random() * 900000 + 100000);
		return String.valueOf(num);
	}

	/**
	 * 组装BindTelephoneVo对象
	 */
	private BindTelephoneVo fillBindTel(GetCheckCodeReq req, String checkCode)
	{
		BindTelephoneVo vo = new BindTelephoneVo();
		vo.setUserId(req.getUserId());
		vo.setAtetId(req.getAtetId());
		vo.setProductId(req.getProductId());
		vo.setDeviceCode(req.getDeviceCode());
		vo.setDeviceType(BindTelephoneVo.DEVICETYPE_TV);
		vo.setTelephone(req.getTelephone());
		vo.setExt(checkCode);
		vo.setState(BindTelephoneVo.STATE_NOT_VERIFY);
		return vo;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}