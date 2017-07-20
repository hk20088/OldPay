package com.newspace.payplatform.qmshortcut;

import java.io.File;
import java.util.Properties;

import com.newspace.common.utils.PropertiesUtils;
import com.newspace.payplatform.qmshortcut.entity.BankAlias;

/**
 * QMShortcutUtils.java  
 * 描 述:  快钱快捷支付的常量类、工具类
 * 作 者:  liushuai
 * 历 史： 2014-8-5 创建
 */
public final class QMShortcutUtils
{
	private static final Properties prop = PropertiesUtils.getProps("shortcutpayment");

	/**
	 * 接收TR3消息的URL
	 */
	public static final String RECEIVE_TR3_URL = PropertiesUtils.getProps("configuration").getProperty("qm_quickpay_tr3Url");

	/**
	 * 商户信息：商户号
	 */
	public static final String MERCHANTINFO_ID = prop.getProperty("qm_merchantId");

	/**
	 * 商户信息：私钥密码
	 */
	public static final String MERCHANTINFO_CERTPASSWORD = prop.getProperty("qm_certPassword");

	/**
	 * 商户信息：私钥证书路径。jks文件路径
	 */
	public static final String MERCHANTINFO_CERTFILENAME = System.getProperty("user.dir") + File.separator + "atetpayplatform"
			+ File.separator + "certificate" + File.separator + "81231007375005090.jks";

	/**
	 * 公钥证书路径。cer文件路径。
	 */
	public static final String PUBLICKEY_FILE_PATH = System.getProperty("user.dir") + File.separator + "atetpayplatform" + File.separator
			+ "certificate" + File.separator + "mgw.cer";

	/**
	 * 商户信息：初始分配密码
	 */
	public static final String MERCHANTINFO_MERCHANTLOGINKEY = prop.getProperty("qm_merchantLoginKey");

	/**
	 * 商户信息：提交地址域名
	 */
	public static final String MERCHANTINFO_DOMAINNAME = prop.getProperty("qm_domainName");

	/**
	 * 商户信息：提交端口
	 */
	public static final String MERCHANTINFO_SSLPORT = prop.getProperty("qm_sslPort");

	/**
	 * 商户信息：连接超时时间
	 */
	public static final Integer MERCHANTINFO_TIMECOUT = Integer.parseInt(prop.getProperty("qm_timecout"));

	/**
	 * 商户信息：终端号
	 */
	public static final String MERCHANTINFO_TERMINALID = prop.getProperty("qm_terminalId");

	/**
	 * XML中命名空间属性
	 */
	public static final String XMLNS = "http://www.99bill.com/mas_cnp_merchant_interface";

	/**
	 * 交易类型：PUR
	 */
	public static final String TXN_TYPE_PUR = "PUR";

	/**
	 * 交易标志：QuickPay 第一次快捷支付
	 */
	public static final String SPFLAG_QUICKPAY = "QuickPay";

	/**
	 * 交易标志：QPay2 再次快捷支付
	 */
	public static final String SPFLAG_QUICKPAY_AGAIN = "QPay02";

	/**
	 * 保存卡信息标识：1 保存
	 */
	public static final String SAVE_PCI_FLAG = "1";

	/**
	 * 保存卡信息标识：0 不保存
	 */
	public static final String NOTSAVE_PCI_FLAG = "0";

	/**
	 * 快捷支付批次：1 首次支付
	 */
	public static final String PAYBATCH_FIRST = "1";

	/**
	 * 快捷支付批次：2 再次支付
	 */
	public static final String PAYBATCH_AGAIN = "2";

	/**
	 * 响应码：00 成功
	 */
	public static final String RESPONSECODE_SUCCESS = "00";
	
	/**
	 * 响应码：21011 手机号不匹配
	 */
	public static final String RESPONSECODE_TEL_NOTMATCH = "21011";

	/**
	 * 响应码：C0 正在处理
	 */
	public static final String RESPONSECODE_PROCESSING = "C0";

	/**
	 * 卡类型：0001 信用卡
	 */
	public static final String CARD_TYPE_CREDIT = "0001";

	/**
	 * 卡类型：0002 借记卡
	 */
	public static final String CARD_TYPE_DEBIT = "0002";

	// 私有化构造方法
	private QMShortcutUtils()
	{
	}

	/**
	 * 快钱快捷支付是否支持该银行卡
	 * @param bankInfo 银行代码或名称
	 * @param cardType 卡类型
	 * @return boolean true：支持；false：不支持
	 */
	public static boolean isSupport(String bankInfo, String cardType)
	{
		return BankAlias.isSupportBank(bankInfo, cardType);
	}
}