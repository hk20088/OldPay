package com.newspace.payplatform.order.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import com.newspace.common.utils.StringUtils;
import com.newspace.common.vo.BaseVo;
import com.newspace.payplatform.PayUtils;

/**
 * PaymentOrderVo.java 
 * 描 述:  支付平台生成的用于记录的订单实体类。  
 * 作 者:  liushuai
 * 历 史： 2014-4-23 创建
 */
public class PaymentOrderVo extends BaseVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 合作商id
	 */
	private String partnerId;

	/**
	 * 合作商id, 0 : ATET
	 */
	public static final String PARTNERID_ATET = "0";

	/**
	 * 本地生成的订单号
	 */
	private String orderNo;

	/**
	 * 用户标识符
	 */
	private Integer userId;
	
	/**
	 * 服务器下发的设备唯一Id
	 */
	private String atetId;

	/**
	 * 设备唯一标识
	 */
	private String productId;

	/**
	 * 后台数据库生成的设备类型的唯一标识
	 */
	private String deviceId;

	/**
	 * 设备code
	 */
	private String deviceCode;

	/**
	 * 设备类型， 1：TV; 2: 手机  3：平板
	 */
	private Integer deviceType;

	/**
	 * 渠道id
	 */
	private String channelId;

	/**
	 * cpId
	 */
	private String cpId;

	/**
	 * CP端创建的对应于本次支付请求的订单号
	 */
	private String cpOrderNo;

	/**
	 * 应用id
	 */
	private String appId;

	/**
	 * 应用包名
	 */
	private String packageName;

	/**
	 * 游戏名
	 */
	private String gameName;

	/**
	 * 支付点
	 */
	private String payPoint;

	/**
	 * 支付点名称
	 */
	private String payPointName;

	/**
	 * 支付点单价，单价：分
	 */
	private Integer payPointMoney;

	/**
	 * 数量
	 */
	private Integer counts;

	/**
	 * 支付金额，单位：分。
	 */
	private Integer amount;

	/**
	 * 支付币种。默认为0：RMB.
	 */
	private Integer amountType = AMOUNT_TYPE_RMB;

	/**
	 * 支付币种：RMB
	 */
	public final static Integer AMOUNT_TYPE_RMB = 0;

	/**
	 * 支付方式
	 */
	private Integer payType;

	/**
	 * 支付方式： 联通
	 */
	public final static Integer PAYTYPE_UNICOM = 0;
	
	/**
	 * 支付方式：联通沃邮箱
	 */
	public final static Integer PAYTYPE_UNICOM_WO = 10;
	
	/**
	 * 支付方式：联通IPTV
	 */
	public final static Integer PAYTYPE_UNICOM_IPTV = 11;
	
	/**
	 * 支付方式：联通沃橙
	 */
	public final static Integer PAYTYPE_UNICOM_WOCHENG = 12;

	/**
	 * 支付方式：支付宝
	 */
	public final static Integer PAYTYPE_ALIPAY = 1;

	/**
	 * 支付方式：快钱快捷支付
	 */
	public final static Integer PAYTYPE_QM_SHORTCUT = 2;

	/**
	 * CP通知地址
	 */
	private String cpNotifyUrl;

	/**
	 * 创建订单时间
	 */
	private Timestamp createTime;

	/**
	 * 更新订单时间
	 */
	private Timestamp updateTime;
	
	/**
	 * 订单状态: 0 支付成功，1 支付失败； 2 未处理
	 */
	private Integer state;

	/**
	 * 订单状态：支付及确认订单成功
	 */
	public final static Integer PAY_STATE_SUCCESS = 0;

	/**
	 * 订单状态：支付失败
	 */
	public final static Integer PAY_STATE_FAIL = 1;

	/**
	 * 订单状态：未进行支付操作
	 */
	public final static Integer PAY_STATE_NONPROCESS = 2;
	
	/**
	 * 订单状态：联通支付通道：上行（扣费）短信发送成功 ，但尚未收到联通平台异步通知的支付结果。
	 */
	public final static Integer PAY_STATE_SENDMSGSUC = 3; 
	

	/**
	 * 支付生成的订单号：比如走联通通过或支付宝等支付时生成的订单号。
	 */
	private String paymentCreatedOrderNo;

	/**
	 * 商户私有信息，该字段不存入数据库
	 */
	private String cpPrivateInfo;

	/**
	 * 是否需要发回执短信给用户（默认为0不需要，1 需要），该字段不存入数据库
	 */
	private int isReceipt = 0;
	
	/**
	 * 是否为扫码支付。用户可能在电视客户端产生支付形为，但将订单扫码到手机上支付，
	 * 这时候订单属于电视渠道，并且记录此订单为扫码支付，方便以后统计（比如扫码支付率等）
	 * 0  代表是扫码支付； 1  代表非扫码支付。
	 */
	private Integer isScanPay = NO_SCAN_PAY;
	
	/**
	 * 表示此订单为扫码支付。
	 */
	public final static Integer IS_SCAN_PAY = 0;
	
	/**
	 * 表示此订单非扫码支付。
	 */
	public final static Integer NO_SCAN_PAY = 1;
	
	/**
	 * CP是否发送物品给用户
	 * 当客户端接收到支付成功的通知时有两个操作：
	 * 1、通知CP支付成功，让CP下发物品给用户
	 * 2、通知服务器，告诉服务器CP已下发物品给用户（不考虑是否下发成功）
	 * 目的：根据此字段判断用户是否支付成功但未收到物品，如果确认是，需要给用户补单 。
	 */
	private Integer isSendGood = NO_SEND_GOOD;
	
	/**
	 * 表示已发放物品
	 */
	public final static Integer IS_SEND_GOOD = 0;
	
	/**
	 * 表示未发放物品
	 */
	public final static Integer NO_SEND_GOOD = 1;
	
	/**
	 * 支付APK版本号，自定义，例如第一个版本为1,第二个版本为2。用来兼容不同版本时使用。
	 */
	private String versionCode;
	
	/**
	 * 客户端IP
	 */
	private String clientIP;

	
	/**
	 * 扣费手机号：当使用联通计费通道时，记录用户的扣费手机号
	 */
	private String telephone;
	
	/**
	 * 短信接入号：当使用联通计费通道时，记录短信上行的接入号（可根据接入号规则来判断是谁发起的订单）
	 */
	private String commandid;
	
	/**
	 * 渠道短代码：当使用联通计费通道时，记录发起订单的渠道号，可用来统计。
	 */
	private String channelShortCode;
	
	/**
	 * 用于订单号自增的原子类
	 */
	private static volatile AtomicInteger incrementInt = new AtomicInteger(0);

	/**
	 * 生成订单号。20位：4位地区代码+10位时间戳(yyMMddHHmm)+6位递增数字。
	 */
	public static String generateOrderNo()
	{
		try
		{
			StringBuilder sb = new StringBuilder(PayUtils.AREA_CODE);
			sb.append(new SimpleDateFormat("yyMMddHHmm").format(new Date()));
			int orderNo = 1;
			if (!incrementInt.compareAndSet(990000, 1))
				orderNo = incrementInt.incrementAndGet();
			sb.append(padWithZero(orderNo));
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断对象是否是合法的（是否必须的参数都进行了赋值）。
	 * @return boolean  true：合法。
	 */
	public boolean isLegal()
	{
		if (StringUtils.isExistNullOrEmpty(partnerId, orderNo, productId, deviceCode, cpId, packageName, appId, payPoint, gameName,
				channelId, partnerId, deviceId)
				|| payType == null || counts == null || amount == null || userId == null)
			return false;
		return true;
	}

	/**
	 * 生成数字字符串，不足6位的用0填充。 
	 */
	private static String padWithZero(int num)
	{
		String numStrValue = String.valueOf(num);
		StringBuilder sb = new StringBuilder(numStrValue);
		for (int i = 0; i < 6 - numStrValue.length(); i++)
			sb.insert(0, "0");
		return sb.toString();
	}

	@Override
	public String logString()
	{
		StringBuilder str = new StringBuilder();
		str.append("{ id: ").append(id);
		str.append(", cpOrderNo: ").append(cpOrderNo);
		str.append(", state : ").append(state).append("}");
		return toString();
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
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

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
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

	public Integer getAmount()
	{
		return amount;
	}

	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}

	public Integer getAmountType()
	{
		return amountType;
	}

	public void setAmountType(Integer amountType)
	{
		this.amountType = amountType;
	}

	public Integer getPayType()
	{
		return payType;
	}

	public void setPayType(Integer payType)
	{
		this.payType = payType;
	}

	public Timestamp getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getState()
	{
		return state;
	}

	public void setState(Integer state)
	{
		this.state = state;
	}

	public String getOrderNo()
	{
		return orderNo;
	}

	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}

	public String getCpId()
	{
		return cpId;
	}

	public void setCpId(String cpId)
	{
		this.cpId = cpId;
	}

	public String getPayPointName()
	{
		return payPointName;
	}

	public void setPayPointName(String payPointName)
	{
		this.payPointName = payPointName;
	}

	public Integer getPayPointMoney()
	{
		return payPointMoney;
	}

	public void setPayPointMoney(Integer payPointMoney)
	{
		this.payPointMoney = payPointMoney;
	}

	public String getPaymentCreatedOrderNo()
	{
		return paymentCreatedOrderNo;
	}

	public void setPaymentCreatedOrderNo(String paymentCreatedOrderNo)
	{
		this.paymentCreatedOrderNo = paymentCreatedOrderNo;
	}

	public Integer getDeviceType()
	{
		return deviceType;
	}

	public void setDeviceType(Integer deviceType)
	{
		this.deviceType = deviceType;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}

	public String getPartnerId()
	{
		return partnerId;
	}

	public void setPartnerId(String partnerId)
	{
		this.partnerId = partnerId;
	}

	public String getCpNotifyUrl()
	{
		return cpNotifyUrl;
	}

	public void setCpNotifyUrl(String cpNotifyUrl)
	{
		this.cpNotifyUrl = cpNotifyUrl;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getGameName()
	{
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}

	public String getCpPrivateInfo()
	{
		return cpPrivateInfo;
	}

	public void setCpPrivateInfo(String cpPrivateInfo)
	{
		this.cpPrivateInfo = cpPrivateInfo;
	}

	public int getIsReceipt()
	{
		return isReceipt;
	}

	public void setIsReceipt(int isReceipt)
	{
		this.isReceipt = isReceipt;
	}

	public String getAtetId() {
		return atetId;
	}

	public void setAtetId(String atetId) {
		this.atetId = atetId;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public Integer getIsScanPay() {
		return isScanPay;
	}

	public void setIsScanPay(Integer isScanPay) {
		this.isScanPay = isScanPay;
	}

	public Integer getIsSendGood() {
		return isSendGood;
	}

	public void setIsSendGood(Integer isSendGood) {
		this.isSendGood = isSendGood;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCommandid() {
		return commandid;
	}

	public void setCommandid(String commandid) {
		this.commandid = commandid;
	}

	public String getChannelShortCode() {
		return channelShortCode;
	}

	public void setChannelShortCode(String channelShortCode) {
		this.channelShortCode = channelShortCode;
	}
	
}