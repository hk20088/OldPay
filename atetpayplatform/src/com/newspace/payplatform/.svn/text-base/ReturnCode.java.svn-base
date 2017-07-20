package com.newspace.payplatform;

import java.util.HashMap;
import java.util.Map;

/**
 * ReturnCode.java
 * 描 述:  <pre>响应状态码。
 * 		   0-100  ：接口通用响应码
 * 		   101-200：终端请求支付接口响应码
 *         201-300：联通SMS短信相关操作响应码			
 *		   401-500：CP信息同步接口响应码
 *		   501-600：快钱快捷支付接口响应码
 *		   601-700: 盒子支付绑定手机操作
 *		   70000+ : 联通VAC支付返回值
 *		   3103：联通支付操作，话费不足。暂时按照相应测试条件得到的返回码
 * 		   </pre>
 * 作 者:  liushuai
 * 历 史： 2014-4-22 创建
 * 		   2014-5-19 为了方便获取描述信息，由int常量修改为枚举方式实现。
 */
public enum ReturnCode
{
	/**
	 * 操作成功
	 */
	SUCCESS(0, "操作成功"),

	/**
	 * 通用操作失败
	 */
	ERROR(1, "操作失败"),

	/**
	 * 请求参数错误
	 */
	REQUEST_PARAM_ERROR(2, "请求参数错误"),

	/**
	 * 不存在匹配的信息：例如不存在appId对应的密钥信息
	 */
	NOT_EXIST_MATCHED_INFO(3, "不存在匹配的信息"),

	/**
	 * 校验签名操作失败
	 */
	VERIFY_SIGN_ERROR(4, "校验签名操作失败"),

	/**
	 * 校验签名结果不一致
	 */
	VERIFY_SIGN_DATACHANGED(5, "校验签名结果不一致"),

	/**
	 * 要查询的数据为空，不存在相关数据
	 */
	DATA_NOT_EXIST(6, "要查询的数据为空，不存在相关数据"),

	/**
	 * 某些参数不能为空，但是值却为空
	 */
	REQUEST_PARAM_EXIST_NULL(7, "某些参数不能为空，但是值却为空！"),

	/**
	 * 记录订单操作失败
	 */
	RECORD_ORDER_FAIL(8, "记录订单操作失败，订单号可能重复、部分参数为空或其他原因！"),

	/**
	 * 校验验证码失败或参数传入不正确，请重新获取验证码
	 */
	VERIFY_CHECKCODE_FAIL(9, "校验验证码失败或参数传入不正确，请重新获取验证码"),

	/**
	 * 校验码输入不一致，或校验码已失效，请重新获取
	 */
	VERIFY_CHECKCODE_NOTEQUAL(10, "校验码输入不一致，或校验码已失效，请重新获取"),

	/**
	 * 连接或读取数据超时
	 */
	CONN_OR_READ_TIMEOUT(11, "连接或读取数据超时"),
	
	/**
	 * 根据订单号获取对象失败，请重新获取订单号
	 */
	ORDERNO_OBJECT_FAIL(12,"根据订单号获取对象失败，请重新获取订单号"),

	/**
	 * 支付金额不一致，支付金额和通过支付点金额和数量得到的不一致
	 */
	AMOUNT_NOT_EQUAL(101, "支付金额不一致，支付金额和通过支付点金额和数量得到的不一致"),

	/**
	 * 支付点不存在，传入的appId并不存在传入的支付点
	 */
	PAYPOINT_NOT_EXIST(102, "支付点不存在，传入的appId并不存在传入的支付点"),

	/**
	 * 支付点的金额不正确 
	 */
	PAYPOINT_MONEY_NOT_EQUAL(103, "支付点的金额不正确 "),

	/**
	 * 绑定手机号操作失败
	 */
	BIND_TEL_ERROR(104, "绑定手机号操作失败"),
	
	/**
	 * CPID不匹配
	 */
	APPID_NOT_MATCH(105,"应用信息不匹配"),

	/**
	 * 登录失败 IP、用户名、密码错误
	 */
	SMS_LOGIN_FAIL(201, "登录失败 IP、用户名、密码错误"),

	/**
	 * 未登录进行消息下发
	 */
	SMS_SEND_WITHOUT_LOGIN(202, "未登录进行消息下发"),

	/**
	 * 下发消息客户端IP错误 
	 */
	SMS_SENDCLIENT_IPERROR(203, "下发消息客户端IP错误 "),

	/**
	 * 下发消息源号码错误
	 */
	SMS_SOURCENUMBER_ERROR(204, "下发消息源号码错误 "),

	/**
	 * 短信长度错误
	 */
	SMS_MSGLENGTH_ERROR(205, "短信长度错误 "),

	/**
	 * SMServer拒绝服务，大网关拒绝服务
	 */
	SMS_SMSERVER_REFUSE(206, "SMServer拒绝服务，大网关拒绝服务 "),

	/**
	 * 客户端超流量
	 */
	SMS_CLIENT_OUTOF_FLOW(207, "客户端超流量"),

	/**
	 * CP信息同步失败
	 */
	CP_SYNC_FAILED(401, "CP信息同步失败"),

	/**
	 * 不存在对应的卡号信息
	 */
	QM_NOTEXIST_CARDINFO(501, "不存在对应的卡号信息"),

	/**
	 * 不支持此银行此种类型的银行卡
	 */
	QM_NOT_SUPPORT_BANKCARD(502, "不支持此银行此种类型的银行卡"),
	
	/**
	 * 手机号不匹配 - 手机号不符
	 */
	QM_NOTMATCH_TEL(503,"手机号不匹配 - 手机号不符"),

	/**
	 * 当天绑定手机号操作已经超过三次
	 */
	BINDTEL_OVERTOP_COUNT(601, "当天此手机号绑定操作已经超过三次"),

	/**
	 * 验证码已经失效，有效期10min
	 */
	BINDTEL_EXPIRED(602, "验证码已经失效，有效期10min"),

	/**
	 * 创建令牌失败
	 */
	TOKEN_CREATEERR(100001, "创建令牌失败"),

	/**
	 * 令牌无效
	 */
	TOKEN_INVALID(100002, "令牌无效"),

	/**
	 * VAC鉴权批价与集团服务器发生未知错误
	 */
	UNICOM_VAC_UNKOWN_ERROR(77777, "VAC鉴权批价与集团服务器发生未知错误"),

	/**
	 * 手机号码为空
	 */
	UNICOM_VAC_SIM_ISNULL(70010, "手机号码为空"),

	/**
	 * 不是合法的手机号码
	 */
	UNICOM_VAC_SIM_ISILLEGAL(70011, "不是合法的手机号码"),

	/**
	 * 不是合法的请求IP地址
	 */
	UNICOM_VAC_REQADDR_ISILLEGAL(70012, "不是合法的请求IP地址"),

	/**
	 * 商品id为空
	 */
	UNICOM_VAC_PRODUCTID_ISNULL(70020, "商品id为空"),

	/**
	 * 计费点id为空
	 */
	UNICOM_VAC_PAYPRODUCTID_ISNULL(70030, "计费点id为空"),

	/**
	 * 不是有效的计费点产品id
	 */
	UNICOM_VAC_PAYPRODUCTID_ISILLEGAL(70031, "不是有效的计费点产品id"),

	/**
	 * 不存在对应金额的产品
	 */
	UNICOM_VAC_MATCHED_PRODUCT_NOEXIST(70039, "不存在对应金额的产品"),

	/**
	 * 商品类型为空
	 */
	UNICOM_VAC_PRODUCTTYPE_ISNULL(70040, "商品类型为空"),

	/**
	 * 不是有效的商品类型
	 */
	UNICOM_VAC_PRODUCTTYPE_ISILLEGAL(70041, "不是有效的商品类型"),

	/**
	 * 商品类型 和 计费点产品id 不相符
	 */
	UNICOM_VAC_PRODUCTINFO_NOTMATCHED(70042, "'商品类型'和'计费点产品id'不相符"),

	/**
	 * 不商品单价为空
	 */
	UNICOM_VAC_PRODUCTPRICE_ISNULL(70050, "商品单价为空"),

	/**
	 * 不是有效的商品单价
	 */
	UNICOM_VAC_PRODUCTPRICE_ISILLEGAL(70051, "不是有效的商品单价"),

	/**
	 * 交易序列号为空
	 */
	UNICOM_VAC_PAYSEQ_ISNULL(70060, "交易序列号为空"),

	/**
	 * VAC鉴权计费时错误
	 */
	UNICOM_VAC_PAYMENT_ERROR(70070, "VAC鉴权计费时错误"),

	/**
	 * VAC鉴权批价时网络读取错误
	 */
	UNICOM_VAC_NETWORK_ERROR(70071, "VAC鉴权批价时网络读取错误"),

	/**
	 * VAC绑定请求时错误
	 */
	UNICOM_VAC_BINDREQ_ERROR(70072, "VAC绑定请求时错误"),

	/**
	 * VAC鉴权批价等待返回超时
	 */
	UNICOM_VAC_REQ_TIMEOUT(70073, "VAC鉴权批价等待返回超时"),

	/**
	 * 	插入交易记录时错误
	 */
	UNICOM_VAC_RECORD_ERROR(70080, "插入交易记录时错误"),

	/**
	 * 话费不足
	 */
	UNICOM_VAC_CHARGE_NOTENOUGH(3103, "话费不足"),
	
	/**
	 * 联通沃邮箱下发扣费确认短信失败，停止计费
	 */
	UNICOM_WO_STOP_PAY(3104,"扣费短信下发失败，停止计费");
	

	/**
	 * 返回码
	 */
	private int returnCode;

	/**
	 * 返回码描述信息
	 */
	private String desc;

	/**
	 * 包含返回码和对应描述信息的map
	 */
	private final static Map<Integer, String> map = new HashMap<Integer, String>();

	static
	{
		for (ReturnCode returnCode : ReturnCode.values())
			map.put(returnCode.getCode(), returnCode.getDesc());
	}

	ReturnCode(int returnCode, String desc)
	{
		this.returnCode = returnCode;
		this.desc = desc;
	}

	/**
	 * 通过返回码得到描述信息
	 * @param returnCode 返回码
	 * @return String  描述信息
	 */
	public static String getDesc(int returnCode)
	{
		String desc = map.get(returnCode);
		return desc == null ? "不存在该返回码" : desc;
	}

	public int getCode()
	{
		return returnCode;
	}

	public String getDesc()
	{
		return desc;
	}
}