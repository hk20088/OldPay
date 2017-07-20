package com.newspace.payplatform.faillog;

import java.util.Date;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.DefaultBoImpl;

/**
 * FailLogUtils.java 
 * 描 述:  失败日志工具类  
 * 作 者:  liushuai
 * 历 史： 2014-7-24 创建
 */
public class FailLogUtils
{
	private static final DefaultBoImpl failLogBo = SpringBeanUtils.getBean(DefaultBoImpl.class);

	static
	{
		failLogBo.setRelateVo(FailLogVo.class);
	}

	/**
	 * 记录日志 
	 */
	public static void insertFaillog(int operaType, String data, String sign, int failCode)
	{
		insertFaillog(operaType, data, sign, failCode, ReturnCode.getDesc(failCode));
	}

	/**
	 * 记录日志 
	 */
	@SuppressWarnings("unchecked")
	public static void insertFaillog(int operaType, String data, String sign, int failCode, String desc)
	{
		FailLogVo vo = new FailLogVo();
		vo.setOperaType(operaType);
		vo.setFailCode(failCode);
		desc = desc == null ? ReturnCode.getDesc(failCode) : desc;
		vo.setFailDesc(desc);
		vo.setLogTime(new Date());
		vo.setReceivedDataMsg(data);
		vo.setReceivedSignMsg(sign);

		failLogBo.insert(vo);
	}
}