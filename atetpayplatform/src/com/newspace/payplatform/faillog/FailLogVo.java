package com.newspace.payplatform.faillog;

import java.util.Date;

import com.newspace.common.vo.BaseVo;

/**
 * FailLogVo.java 
 * 描 述:  支付请求失败日志类  
 * 作 者:  liushuai
 * 历 史： 2014-5-19 创建
 */
public class FailLogVo extends BaseVo
{

	private static final long serialVersionUID = 1L;

	/**
	 * 失败码，采用ReturnCode中相关值
	 */
	private int failCode;

	/**
	 * 操作类型
	 */
	private int operaType;

	/**
	 * 操作类型， 0 : 联通支付
	 */
	public static final int OPERATYPE_UNICOM_VAC_PAYMENT = 0;

	/**
	 * 操作类型，1：短信支付
	 */
	public static final int OPERATYPE_UNICOM_MSG_PAYMENT = 1;

	/**
	 * 操作类型，2 : 终端查询订单
	 */
	public static final int OPERATYPE_TERMINAL_QUERYORDER = 2;

	/**
	 * 操作类型，3 ：支付宝支付成功之后接收通知操作
	 */
	public static final int OPERATYPE_ALIPAY_NOTIFY = 3;

	/**
	 * 操作类型，4：快钱支付成功之后接收通知操作
	 */
	public static final int OPERATYPE_QUICKMONEY_NOTIFY = 4;

	/**
	 * 操作类型，5：绑定手机号
	 */
	public static final int OPERATYPE_BIND_TEL = 5;

	/**
	 * 操作类型，6：获取验证码
	 */
	public static final int OPERATYPE_GET_CHECKCODE = 6;

	/**
	 * 操作类型，7：验证校验码
	 */
	public static final int OPERATYPE_VERIFY_CHECKCODE = 7;

	/**
	 * 操作类型，8：cp查询订单
	 */
	public static final int OPERATYPE_CP_QUERYORDER = 8;

	/**
	 * 操作类型，9：生成订单号
	 */
	public static final int OPERATYPE_GENERATE_ORDERNO = 9;

	/**
	 * 操作类型，10：校验支付点
	 */
	public static final int OPERATYPE_VERIFY_PAYPOINT = 10;

	/**
	 * 操作类型，11：快钱快捷支付，手机动态鉴权操作
	 */
	public static final int OPERATYPE_QM_GET_DYNNUM = 11;

	/**
	 * 操作类型，12：快钱快捷支付，首次快捷支付操作
	 */
	public static final int OPERATYPE_QM_SHORTCUT_FIRST = 12;

	/**
	 * 操作类型，13：快钱快捷支付，快捷支付操作
	 */
	public static final int OPERATYPE_QM_SHORTCUT = 13;

	/**
	 * 操作类型，14：快钱快捷支付，查询已绑定的银行卡信息
	 */
	public static final int OPERATYPE_QM_QUERYBOUNDCARD = 14;

	/**
	 * 操作类型，15：快钱快捷支付，解绑银行卡操作
	 */
	public static final int OPERATYPE_QM_UNBINDCARD = 15;

	/**
	 * 操作类型，16：块钱快捷支付，查询银行卡信息
	 */
	public static final int OPERATYPE_QM_QUERYCARDINFO = 16;

	/**
	 * 失败原因描述
	 */
	private String failDesc;

	/**
	 * 接收到的原数据
	 */
	private String receivedDataMsg;

	/**
	 * 接收到的数字签名
	 */
	private String receivedSignMsg;

	/**
	 * 记录日志事件
	 */
	private Date logTime;

	/**
	 * 本次请求对应订单id
	 */
	private String payOrderId;

	/**
	 * 得到生成FailLogVo对象。
	 * @param operaType 操作类型
	 */
	public static FailLogVo getFailLogInstance(int operaType)
	{
		FailLogVo vo = new FailLogVo();
		vo.setOperaType(operaType);
		return vo;
	}

	@Override
	public String logString()
	{
		StringBuilder str = new StringBuilder("{id : ");
		str.append(id).append(", failCode : ");
		str.append(failCode).append(", failDesc : ");
		str.append(failDesc).append(" }");
		return str.toString();
	}

	public int getFailCode()
	{
		return failCode;
	}

	public void setFailCode(int failCode)
	{
		this.failCode = failCode;
	}

	public String getFailDesc()
	{
		return failDesc;
	}

	public void setFailDesc(String failDesc)
	{
		this.failDesc = failDesc;
	}

	public String getReceivedDataMsg()
	{
		return receivedDataMsg;
	}

	public void setReceivedDataMsg(String receivedDataMsg)
	{
		this.receivedDataMsg = receivedDataMsg;
	}

	public String getReceivedSignMsg()
	{
		return receivedSignMsg;
	}

	public void setReceivedSignMsg(String receivedSignMsg)
	{
		this.receivedSignMsg = receivedSignMsg;
	}

	public Date getLogTime()
	{
		return logTime;
	}

	public void setLogTime(Date logTime)
	{
		this.logTime = logTime;
	}

	public String getPayOrderId()
	{
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId)
	{
		this.payOrderId = payOrderId;
	}

	public int getOperaType()
	{
		return operaType;
	}

	public void setOperaType(int operaType)
	{
		this.operaType = operaType;
	}
}