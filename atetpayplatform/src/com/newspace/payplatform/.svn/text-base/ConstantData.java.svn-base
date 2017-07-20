package com.newspace.payplatform;

import java.util.Properties;

import com.newspace.common.utils.PropertiesUtils;

/**
 * 常量数据类,保存项目中多处使用的常量
 * @author huqili
 * @since JDK 1.6
 * @date 2014年11月25日
 *
 */
public class ConstantData {

	
	/**
	 * 下发短信时需要补充的空格
	 */
	public static final String APPEND_BLANK = "                                                                      ";
	
	
	//---------------------HTTP-GET方式下行短信时所需参数--------------------------

	/**
	 * 短信网关地址
	 */
	public static final String HTTP_UNICOM_SEND_SMS_URL;
	
	/**
	 * 提交短信网关的公司代号，例如UNIVAC
	 */
	public static final String CORP_ID;
	
	/**
	 * 下行特服号码
	 */
	public static final String CMD_ID;
	
	/**
	 * 预留字段
	 */
	public static final String ACTION;
	
	//---------------------HTTP-GET方式上行短信时所需参数--------------------------
	
	/**
	 * 上行网关地址
	 */
	public static final String HTTP_UNICOM_RECEIVE_SMS_URL;
	
	
	/**
	 * 渠道代码
	 */
	public static final String SPNUMBER;
	
	//----------------------------完美接收异步通知地址------------------------------------------------
	public static final String PERFECT_NOTIFY_URL;
	
	
	//----------------------------联通沃邮箱计费请求地址-----------------------------------------------
	public static final String UNICOM_WO_PAY_URL;
	
	//沃+渠道计费开关
	public static final boolean WO_POWER;
	
	
	static{
		
		Properties prop = PropertiesUtils.getProps("configuration");
		
		//联通下发短信所需参数
		HTTP_UNICOM_SEND_SMS_URL = prop.getProperty("http_unicom_send_sms_url");
		CORP_ID = prop.getProperty("corp_id");
		CMD_ID = prop.getProperty("cmd_id");
		ACTION = prop.getProperty("action");
		
		//联通上行短信所需参数
		HTTP_UNICOM_RECEIVE_SMS_URL = prop.getProperty("http_unicom_receive_sms_url");
		SPNUMBER = prop.getProperty("sp_number");
		
		//完美接收异步通知地址
		PERFECT_NOTIFY_URL = prop.getProperty("perfect_notify_url");
		
		
		//联通沃邮箱计费请求地址
		UNICOM_WO_PAY_URL = prop.getProperty("unicom_wo_pay");
		
		//沃邮箱开关
		WO_POWER = Boolean.parseBoolean(prop.getProperty("wo_power"));
		
		
		
	}
	
}
