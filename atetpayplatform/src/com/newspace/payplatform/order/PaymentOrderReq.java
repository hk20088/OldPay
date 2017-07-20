package com.newspace.payplatform.order;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.base.JsonVo;

/**
 * PaymentOrderTo.java 
 * 描 述:  封装订单相关参数传输对象  
 * 作 者:  huqili
 * 历 史： 2014-7-17 创建
 */
public class PaymentOrderReq implements JsonVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 订单信息json串
	 */
	@Expose
	private String data;

	/**
	 * 订单信息json串的签名
	 */
	@Expose
	private String sign;
	
	private PaymentOrderReqData dataObj = new PaymentOrderReqData();

	private static final JLogger logger = LoggerUtils.getLogger(PaymentOrderReq.class);

	/**
	 * 从Json字符串中解析并初始化PaymentOrderTo对象
	 */
	public static PaymentOrderReq getInstanceFromJson(String json)
	{
		try
		{
			PaymentOrderReq req = JsonUtils.fromJson(json, PaymentOrderReq.class);
			req.dataObj = JsonUtils.fromJson(req.getData(), PaymentOrderReqData.class);
			return req;
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
		if (StringUtils.isExistNullOrEmpty(getPayPoint(), getOrderNo(),getGameName(), getChannelId(), getCpId(), getDeviceId(), getPartnerId(), getPackageName())
				|| getAmount() == null || getUserId() == null || getCounts() == null || getDeviceType() == null)
			return false;
		return true;
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

	public String getPartnerId() {
		return dataObj.getPartnerId();
	}

	public void setPartnerId(String partnerId) {
		dataObj.setPartnerId(partnerId);
	}

	public Integer getUserId() {
		return dataObj.getUserId();
	}

	public void setUserId(Integer userId) {
		dataObj.setUserId(userId);
	}

	public String getAtetId() {
		return dataObj.getAtetId();
	}

	public void setAtetId(String atetId) {
		dataObj.setAtetId(atetId);
	}
	
	public String getDeviceId() {
		return dataObj.getDeviceId();
	}

	public void setDeviceId(String deviceId) {
		dataObj.setDeviceId(deviceId);
	}

	public String getProductId() {
		return dataObj.getProductId();
	}

	public void setProductId(String productId) {
		dataObj.setProductId(productId);
	}

	public String getDeviceCode() {
		return dataObj.getDeviceCode();
	}

	public void setDeviceCode(String deviceCode) {
		dataObj.setDeviceCode(deviceCode);
	}

	public Integer getDeviceType() {
		return dataObj.getDeviceType();
	}

	public void setDeviceType(Integer deviceType) {
		dataObj.setDeviceType(deviceType);
	}

	public String getGameName() {
		return dataObj.getGameName();
	}

	public void setGameName(String gameName) {
		dataObj.setGameName(gameName);
	}

	public String getChannelId() {
		return dataObj.getChannelId();
	}

	public void setChannelId(String channelId) {
		dataObj.setChannelId(channelId);
	}

	public String getCpId() {
		return dataObj.getCpId();
	}

	public void setCpId(String cpId) {
		dataObj.setCpId(cpId);
	}

	public String getCpOrderNo() {
		return dataObj.getCpOrderNo();
	}

	public void setCpOrderNo(String cpOrderNo) {
		dataObj.setCpOrderNo(cpOrderNo);
	}

	public String getCpNotifyUrl() {
		return dataObj.getCpNotifyUrl();
	}

	public void setCpNotifyUrl(String cpNotifyUrl) {
		dataObj.setCpNotifyUrl(cpNotifyUrl);
	}

	public String getPackageName() {
		return dataObj.getPackageName();
	}

	public void setPackageName(String packageName) {
		dataObj.setPackageName(packageName);
	}

	public String getCpPrivateInfo() {
		return dataObj.getCpPrivateInfo();
	}

	public void setCpPrivateInfo(String cpPrivateInfo) {
		dataObj.setCpPrivateInfo(cpPrivateInfo);
	}

	public String getAppId() {
		return dataObj.getAppId();
	}

	public void setAppId(String appId) {
		dataObj.setAppId(appId);
	}

	public String getPayPoint() {
		return dataObj.getPayPoint();
	}

	public void setPayPoint(String payPoint) {
		dataObj.setPayPoint(payPoint);
	}

	public Integer getCounts() {
		return dataObj.getCounts();
	}

	public void setCounts(Integer counts) {
		dataObj.setCounts(counts);
	}

	public Integer getAmount() {
		return dataObj.getAmount();
	}

	public void setAmount(Integer amount) {
		dataObj.setAmount(amount);
	}

	public String getOrderNo() {
		return dataObj.getOrderNo();
	}

	public void setOrderNo(String orderNo) {
		dataObj.setOrderNo(orderNo);
	}

	
	
	/**
	 * 静态内部类，用来封闭支付请求参数
	 * @author huqili
	 *
	 */
	private static class PaymentOrderReqData{
		
		// 签约的代理商ID
		private String partnerId;
		
		// 用户id
		private Integer userId;
		
		//服务器下发的设备唯一Id
		private String atetId;
		
		// 设备Id，后台数据库生成
		private String deviceId;

		// 设备唯一标识
		private String productId;

		// 设备代码
		private String deviceCode;
		
		// 设备类型
		private Integer deviceType;
		
		// 游戏名称
		private String gameName;
		
		// 渠道id
		private String channelId;

		// cpid
		private String cpId;
		
		// CP端订单号
		private String cpOrderNo;

		// 通知CP地址
		private String cpNotifyUrl;
		
		// 包名
		private String packageName;
		
		//商户的私有信息
		private String cpPrivateInfo;
		
		// 密钥id
		private String appId;

		// 支付点
		private String payPoint;
		
		// 数量
		private Integer counts;

		// 金额，单位：分
		private Integer amount;

		// 订单号
		private String orderNo;

		public String getPartnerId() {
			return partnerId;
		}

		public void setPartnerId(String partnerId) {
			this.partnerId = partnerId;
		}

		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getAtetId() {
			return atetId;
		}

		public void setAtetId(String atetId) {
			this.atetId = atetId;
		}

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public String getDeviceCode() {
			return deviceCode;
		}

		public void setDeviceCode(String deviceCode) {
			this.deviceCode = deviceCode;
		}

		public Integer getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(Integer deviceType) {
			this.deviceType = deviceType;
		}

		public String getGameName() {
			return gameName;
		}

		public void setGameName(String gameName) {
			this.gameName = gameName;
		}

		public String getChannelId() {
			return channelId;
		}

		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}

		public String getCpId() {
			return cpId;
		}

		public void setCpId(String cpId) {
			this.cpId = cpId;
		}

		public String getCpOrderNo() {
			return cpOrderNo;
		}

		public void setCpOrderNo(String cpOrderNo) {
			this.cpOrderNo = cpOrderNo;
		}

		public String getCpNotifyUrl() {
			return cpNotifyUrl;
		}

		public void setCpNotifyUrl(String cpNotifyUrl) {
			this.cpNotifyUrl = cpNotifyUrl;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		public String getCpPrivateInfo() {
			return cpPrivateInfo;
		}

		public void setCpPrivateInfo(String cpPrivateInfo) {
			this.cpPrivateInfo = cpPrivateInfo;
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getPayPoint() {
			return payPoint;
		}

		public void setPayPoint(String payPoint) {
			this.payPoint = payPoint;
		}

		public Integer getCounts() {
			return counts;
		}

		public void setCounts(Integer counts) {
			this.counts = counts;
		}

		public Integer getAmount() {
			return amount;
		}

		public void setAmount(Integer amount) {
			this.amount = amount;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		
		
	}
}