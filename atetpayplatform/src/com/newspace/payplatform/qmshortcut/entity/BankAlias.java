package com.newspace.payplatform.qmshortcut.entity;

import static com.newspace.payplatform.qmshortcut.QMShortcutUtils.CARD_TYPE_CREDIT;
import static com.newspace.payplatform.qmshortcut.QMShortcutUtils.CARD_TYPE_DEBIT;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.newspace.common.utils.PropertiesUtils;
import com.newspace.common.utils.StringUtils;

/**
 * BankAlias.java 
 * 描 述:  银行别名信息  
 * 作 者:  liushuai
 * 历 史： 2014-8-22 创建
 */
public final class BankAlias
{
	private static final Properties prop = PropertiesUtils.getProps("shortcutpayment");

	/**
	 * 存放支持信用卡的银行编码
	 */
	private static final Set<String> supportCreditBankAlias = new HashSet<String>();

	/**
	 * 存放支持借记卡的银行编码
	 */
	private static final Set<String> supportDebitBankAlias = new HashSet<String>();

	/**
	 * 全部的银行别名信息
	 */
	private static final Map<String, String> bankAliasMap = new HashMap<String, String>();

	static
	{
		// 快钱文档中给出的支持银行信息.
		bankAliasMap.put("CMB", "招商银行,招行,CMB");
		bankAliasMap.put("ICBC", "工商银行,中国工商银行,工行,ICBC");
		bankAliasMap.put("ABC", "农业银行,中国农业银行,农行,ABC");
		bankAliasMap.put("CCB", "建设银行,中国建设银行,CCB");
		bankAliasMap.put("BOC", "中国银行,中行,BOC");
		bankAliasMap.put("SPDB", "浦发银行,上海浦发银行,SPDB");
		bankAliasMap.put("BCOM", "中国交通银行,交通银行,交行,BCOM");
		bankAliasMap.put("CMBC", "民生银行,中国民生银行,CMBC");
		bankAliasMap.put("SDB", "深圳发展银行,深发银行,SDB");
		bankAliasMap.put("GDB", "广东发展银行,广发银行,GDB");
		bankAliasMap.put("CITIC", "中信银行,CITIC");
		bankAliasMap.put("HXB", "华夏银行,HXB");
		bankAliasMap.put("CIB", "兴业银行,CIB");
		bankAliasMap.put("GZRCC", "广州农村商业银行,广州农商行,GZRCC");
		bankAliasMap.put("GZCB", "广州银行,GZCB");
		bankAliasMap.put("CUPS", "中国银联,CUPS");
		bankAliasMap.put("SRCB", "上海农村商业银行,上海农商行,SRCB");
		bankAliasMap.put("POST", "中国邮政,POST");
		bankAliasMap.put("BOB", "北京银行,BOB");
		bankAliasMap.put("CBHB", "渤海银行,CBHB");
		bankAliasMap.put("BJRCB", "北京农村商业银行, 北京农商行,BJRCB");
		bankAliasMap.put("NJCB", "南京银行,NJCB");
		bankAliasMap.put("CEB", "中国光大银行,光大银行,CEB");
		bankAliasMap.put("BEA", "BEA,东亚银行");
		bankAliasMap.put("NBCB", "NBCB,宁波银行");
		bankAliasMap.put("HZB", "杭州银行,HZB");
		bankAliasMap.put("PAB", "PAB,平安银行");
		bankAliasMap.put("HSB", "徽商银行,HSB");
		bankAliasMap.put("CZB", "浙商银行,CZB");
		bankAliasMap.put("SHB", "SHB,上海银行");
		bankAliasMap.put("PSBC", "中国邮政储蓄银行,邮政储汇,PSBC");
		bankAliasMap.put("JSB", "JSB,江苏银行");
		bankAliasMap.put("DLB", "DLB,大连银行");

		// ATET支持的银行信息
		String[] supportCreditBankArr = prop.getProperty("qm_support_credit_bank").split(",");
		for (String bankCode : supportCreditBankArr)
		{
			String[] aliases = bankAliasMap.get(bankCode).split(",");
			for (String alias : aliases)
				supportCreditBankAlias.add(alias);
		}
		String[] supportDebitBankArr = prop.getProperty("qm_support_debit_bank").split(",");
		for (String bankCode : supportDebitBankArr)
		{
			String[] aliases = bankAliasMap.get(bankCode).split(",");
			for (String alias : aliases)
				supportDebitBankAlias.add(alias);
		}
	}

	/**
	 * 是否支持某个银行
	 * @param bankInfo 银行名称信息
	 * @param cardType 卡类型
	 * @return true:支持,false:不支持
	 */
	public static boolean isSupportBank(String bankInfo, String cardType)
	{
		if (StringUtils.isExistNullOrEmpty(bankInfo, cardType))
			return false;
		if (CARD_TYPE_CREDIT.equalsIgnoreCase(cardType))
			return supportCreditBankAlias.contains(bankInfo.toUpperCase());
		else if (CARD_TYPE_DEBIT.equalsIgnoreCase(cardType))
			return supportDebitBankAlias.contains(bankInfo.toUpperCase());
		return false;
	}
}
