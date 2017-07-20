package com.newspace.payplatform.paynotify;

import com.newspace.common.coder.RSACoder;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.DateUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.order.vo.PaymentOrderVo;

/**
 * NotifyContent.java 
 * 描 述:  通知内容类 ：一般通知CP时通用此类。通知使用商，比如完美时使用 NotifyCustomerContent.java类。 
 * 作 者:  huqili
 * 历 史： 2014-9-10 创建
 */
public class NotifyContent
{
	/**
	 * 交易数据，json串
	 */
	private String transdata;

	/**
	 * 加密串
	 */
	private String sign;
	
	/**
	 * 外部订单号
	 */
	private String exOrderNo;

	/**
	 * 支付订单号
	 */
	private String payOrderNo;

	/**
	 * 应用id
	 */
	private String appId;

	/**
	 * 交易金额，单位：分
	 */
	private int amount;

	/**
	 * 交易类型
	 */
	private int payType;

	/**
	 * 交易时间
	 */
	private String transTime;

	/**
	 * 交易数量
	 */
	private int counts;

	/**
	 * 支付点
	 */
	private String payPoint;

	/**
	 * 交易结果，0：成功；1：失败
	 */
	private int result;

	/**
	 * 商户私有信息
	 */
	private String cpPrivateInfo;

	private static final JLogger logger = LoggerUtils.getLogger(NotifyContent.class);
	/**
	 * 生成需要传输的内容字符串
	 */
	public String generateContent()
	{
		transdata = null;
		sign = null;
		transdata = JsonUtils.toJson(this);
		sign = RSACoder.sign(transdata, PayUtils.RSA_ATET_PRIVATEKEY);
		StringBuilder sb = new StringBuilder();
		sb.append("transdata=").append(transdata);
		sb.append("&sign=").append(sign);
		logger.info(String.format("\r\n【通知CP的内容为：%s】", sb.toString()));
		return sb.toString();
	}

	/**
	 * 通过订单生成NotifyContent的实例
	 */
	public static NotifyContent getInstanceByOrder(PaymentOrderVo order)
	{
		NotifyContent content = new NotifyContent();
		content.setAmount(order.getAmount());
		content.setCounts(order.getCounts());
		content.setAppId(order.getAppId());
		content.setExOrderNo(order.getCpOrderNo());
		content.setPayOrderNo(order.getOrderNo());
		content.setPayPoint(order.getPayPoint());
		content.setPayType(order.getPayType());
		content.setResult(order.getState());
		content.setCpPrivateInfo(order.getCpPrivateInfo());
		content.setTransTime((DateUtils.DATETIMEFORMAT_NORMAL.get().format(order.getUpdateTime())));
		return content;
	}

	public String getTransdata()
	{
		return transdata;
	}

	public void setTransdata(String transdata)
	{
		this.transdata = transdata;
	}

	public String getPayOrderNo()
	{
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo)
	{
		this.payOrderNo = payOrderNo;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public int getPayType()
	{
		return payType;
	}

	public void setPayType(int payType)
	{
		this.payType = payType;
	}

	public String getTransTime()
	{
		return transTime;
	}

	public void setTransTime(String transTime)
	{
		this.transTime = transTime;
	}

	public int getCounts()
	{
		return counts;
	}

	public void setCounts(int counts)
	{
		this.counts = counts;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public String getPayPoint()
	{
		return payPoint;
	}

	public void setPayPoint(String payPoint)
	{
		this.payPoint = payPoint;
	}

	public int getResult()
	{
		return result;
	}

	public void setResult(int result)
	{
		this.result = result;
	}

	public String getCpPrivateInfo()
	{
		return cpPrivateInfo;
	}

	public void setCpPrivateInfo(String cpPrivateInfo)
	{
		this.cpPrivateInfo = cpPrivateInfo;
	}

	public String getExOrderNo() {
		return exOrderNo;
	}

	public void setExOrderNo(String exOrderNo) {
		this.exOrderNo = exOrderNo;
	}
	
}