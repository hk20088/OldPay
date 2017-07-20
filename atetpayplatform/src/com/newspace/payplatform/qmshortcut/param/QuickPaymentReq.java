package com.newspace.payplatform.qmshortcut.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * QuickPaymentReq.java 
 * 描 述:  快钱快捷支付请求参数实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-6 创建
 */
public class QuickPaymentReq
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private QuickPaymentReqData dataObj = new QuickPaymentReqData();

	private static final JLogger logger = LoggerUtils.getLogger(QuickPaymentReq.class);

	/**
	 * 静态工厂方法：用来生成并初始化QuickPaymentReq对象
	 */
	public static QuickPaymentReq getInstanceFromJson(String json)
	{
		try
		{
			QuickPaymentReq content = JsonUtils.fromJson(json, QuickPaymentReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), QuickPaymentReqData.class);
			return content;
		}
		catch (Exception e)
		{
			logger.error("解析json字符串失败\r\njson:" + json, e);
		}
		return null;
	}

	/**
	 * 获得data，如果data为空，尝试将dataObj对象转成json串赋值给data 
	 */
	public String getData()
	{
		return StringUtils.isNullOrEmpty(data) ? getNewestData() : data;
	}

	/**
	 * 获取最新的data数据。因为可能dataObj已经发生改变。
	 */
	public String getNewestData()
	{
		data = JsonUtils.toJson(dataObj);
		return data;
	}

	/**
	 * 判断是否合法：必须的参数是否都进行了设置 
	 */
	public boolean isLegal()
	{
		if (StringUtils.isExistNullOrEmpty(getAppId(), getPayPoint(), getOrderNo(), getGameName(), getChannelId(), getCpId(),
				getDeviceId(), getDeviceCode(), getProductId(), getPartnerId(), getPackageName())
				|| getUserId() == null || getCounts() == null || getAmount() == null || getDeviceType() == null)
			return false;
		return true;
	}

	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
	}

	public Integer getUserId()
	{
		return dataObj.getUserId();
	}

	public void setUserId(Integer userId)
	{
		dataObj.setUserId(userId);
	}
	
	public String getAtetId() {
		return dataObj.getAtetId();
	}

	public void setAtetId(String atetId) {
		dataObj.setAtetId(atetId);
	}

	public String getShortCardNo()
	{
		return dataObj.getShortCardNo();
	}

	public void setShortCardNo(String shortCardNo)
	{
		dataObj.setShortCardNo(shortCardNo);
	}

	public Integer getAmount()
	{
		return dataObj.getAmount();
	}

	public void setAmount(Integer amount)
	{
		dataObj.setAmount(amount);
	}

	public String getPayPoint()
	{
		return dataObj.getPayPoint();
	}

	public void setPayPoint(String payPoint)
	{
		dataObj.setPayPoint(payPoint);
	}

	public Integer getCounts()
	{
		return dataObj.getCounts();
	}

	public void setCounts(Integer counts)
	{
		dataObj.setCounts(counts);
	}

	public String getOrderNo()
	{
		return dataObj.getOrderNo();
	}

	public void setOrderNo(String orderNo)
	{
		dataObj.setOrderNo(orderNo);
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public String getGameName()
	{
		return dataObj.getGameName();
	}

	public void setGameName(String gameName)
	{
		dataObj.setGameName(gameName);
	}

	public String getChannelId()
	{
		return dataObj.getChannelId();
	}

	public void setChannelId(String channelId)
	{
		dataObj.setChannelId(channelId);
	}

	public String getDeviceId()
	{
		return dataObj.getDeviceId();
	}

	public void setDeviceId(String deviceId)
	{
		dataObj.setDeviceId(deviceId);
	}

	public String getCpId()
	{
		return dataObj.getCpId();
	}

	public void setCpId(String cpId)
	{
		dataObj.setCpId(cpId);
	}

	public Integer getDeviceType()
	{
		return dataObj.getDeviceType();
	}

	public void setDeviceType(Integer deviceType)
	{
		dataObj.setDeviceType(deviceType);
	}

	public String getProductId()
	{
		return dataObj.getProductId();
	}

	public void setProductId(String productId)
	{
		dataObj.setProductId(productId);
	}

	public String getDeviceCode()
	{
		return dataObj.getDeviceCode();
	}

	public void setDeviceCode(String deviceCode)
	{
		dataObj.setDeviceCode(deviceCode);
	}

	public String getCpOrderNo()
	{
		return dataObj.getCpOrderNo();
	}

	public void setCpOrderNo(String cpOrderNo)
	{
		dataObj.setCpOrderNo(cpOrderNo);
	}

	public String getCpNotifyUrl()
	{
		return dataObj.getCpNotifyUrl();
	}

	public void setCpNotifyUrl(String cpNotifyUrl)
	{
		dataObj.setCpNotifyUrl(cpNotifyUrl);
	}

	public String getPartnerId()
	{
		return dataObj.getPartnerId();
	}

	public void setPartnerId(String partnerId)
	{
		dataObj.setPartnerId(partnerId);
	}

	public String getPackageName()
	{
		return dataObj.getPackageName();
	}

	public void setPackageName(String packageName)
	{
		dataObj.setPackageName(packageName);
	}
	
	public String getCpPrivateInfo() {
		return dataObj.getCpPrivateInfo();
	}

	public void setCpPrivateInfo(String cpPrivateInfo) {
		dataObj.setCpPrivateInfo(cpPrivateInfo);
	}

	private static class QuickPaymentReqData
	{
		// 应用id
		private String appId;

		// 用户id
		private Integer userId;
		
		//服务器下发的设备唯一Id
		private String atetId;

		// 短卡号
		private String shortCardNo;

		// 金额，单位：分
		private Integer amount;

		// 支付点
		private String payPoint;

		// 数量
		private Integer counts;

		// 订单号
		private String orderNo;

		// 游戏名称
		private String gameName;

		// 渠道id
		private String channelId;

		// 设备Id，后台数据库生成
		private String deviceId;

		// cpid
		private String cpId;

		// 设备类型
		private Integer deviceType;

		// 设备唯一标识
		private String productId;

		// 设备代码
		private String deviceCode;

		// CP端订单号
		private String cpOrderNo;

		// 通知CP地址
		private String cpNotifyUrl;

		// 签约的代理商ID
		private String partnerId;

		// 包名
		private String packageName;
		
		//商户的私有信息
		private String cpPrivateInfo;

		public String getAppId()
		{
			return appId;
		}

		public void setAppId(String appId)
		{
			this.appId = appId;
		}

		public Integer getUserId()
		{
			return userId;
		}

		public void setUserId(Integer userId)
		{
			this.userId = userId;
		}

		public String getAtetId() {
			return atetId;
		}

		public void setAtetId(String atetId) {
			this.atetId = atetId;
		}

		public String getShortCardNo()
		{
			return shortCardNo;
		}

		public void setShortCardNo(String shortCardNo)
		{
			this.shortCardNo = shortCardNo;
		}

		public Integer getAmount()
		{
			return amount;
		}

		public void setAmount(Integer amount)
		{
			this.amount = amount;
		}

		public String getPayPoint()
		{
			return payPoint;
		}

		public void setPayPoint(String payPoint)
		{
			this.payPoint = payPoint;
		}

		public Integer getCounts()
		{
			return counts;
		}

		public void setCounts(Integer counts)
		{
			this.counts = counts;
		}

		public String getOrderNo()
		{
			return orderNo;
		}

		public void setOrderNo(String orderNo)
		{
			this.orderNo = orderNo;
		}

		public String getGameName()
		{
			return gameName;
		}

		public void setGameName(String gameName)
		{
			this.gameName = gameName;
		}

		public String getChannelId()
		{
			return channelId;
		}

		public void setChannelId(String channelId)
		{
			this.channelId = channelId;
		}

		public String getDeviceId()
		{
			return deviceId;
		}

		public void setDeviceId(String deviceId)
		{
			this.deviceId = deviceId;
		}

		public String getCpId()
		{
			return cpId;
		}

		public void setCpId(String cpId)
		{
			this.cpId = cpId;
		}

		public Integer getDeviceType()
		{
			return deviceType;
		}

		public void setDeviceType(Integer deviceType)
		{
			this.deviceType = deviceType;
		}

		public String getProductId()
		{
			return productId;
		}

		public void setProductId(String productId)
		{
			this.productId = productId;
		}

		public String getDeviceCode()
		{
			return deviceCode;
		}

		public void setDeviceCode(String deviceCode)
		{
			this.deviceCode = deviceCode;
		}

		public String getCpOrderNo()
		{
			return cpOrderNo;
		}

		public void setCpOrderNo(String cpOrderNo)
		{
			this.cpOrderNo = cpOrderNo;
		}

		public String getCpNotifyUrl()
		{
			return cpNotifyUrl;
		}

		public void setCpNotifyUrl(String cpNotifyUrl)
		{
			this.cpNotifyUrl = cpNotifyUrl;
		}

		public String getPartnerId()
		{
			return partnerId;
		}

		public void setPartnerId(String partnerId)
		{
			this.partnerId = partnerId;
		}

		public String getPackageName()
		{
			return packageName;
		}

		public void setPackageName(String packageName)
		{
			this.packageName = packageName;
		}

		public String getCpPrivateInfo() {
			return cpPrivateInfo;
		}

		public void setCpPrivateInfo(String cpPrivateInfo) {
			this.cpPrivateInfo = cpPrivateInfo;
		}
	}
}