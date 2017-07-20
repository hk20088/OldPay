package com.newspace.payplatform.unicomiptv;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @description 联通IPTV工具类，用来封装校验等方法
 * @author huqili
 * @date 2016年7月4日
 * 
 */
public class IptvUtils {

	private static final Logger logger = Logger.getLogger(IptvUtils.class);
	
	
	//联通IPTV分配的appId
	private static final String APPID = "sxhljf";
	
	//联通IPTV分配的appKey
	private static final String APPKEY = "sxhljf";
	
	
	/**
	 * 校验请求参数的合法性
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 */
	public static boolean verify(Map<String, String> map) throws NumberFormatException, UnsupportedEncodingException
	{
		//封装加密串
		String body =
			String.format("{%s}{%s}{%s}{%s}{%s}{%s}{%s}{%.2f}{%.2f}{%.2f}{%.2f}{%s}{%s}{%s}{%s}{%s}{%.2f}{%s}{%s}",
					map.get("Act"),
					map.get("AppId"),
					map.get("ThirdAppId"),
					map.get("Uin"),
					map.get("ConsumeStreamId"),
					map.get("TradeNo"),
					map.get("Subject"),
					
					Double.parseDouble(map.get("Amount")),
					Double.parseDouble(map.get("ChargeAmount")),
					Double.parseDouble(map.get("ChargeAmountIncVAT")),
					Double.parseDouble(map.get("ChargeAmountExclVAT")),
					
					map.get("Country"),
					map.get("Currency"),
					URLDecoder.decode(map.get("Note"),"utf-8"),
					map.get("TradeStatus"),
					map.get("CreateTime"),
					Double.parseDouble(map.get("Share")),
					map.get("IsTest"),
					APPKEY);
		
		//加密
		String sign = sign(body);
		
		//对比sign是否一致
		if(sign.equals(map.get("Sign")))
			return true;
		
		return false;
	}
	
	/**
	 * 联通IPTV提供的加密方法
	 * @param data
	 * @return
	 */
	public static String sign(String data)
	{
		
        /** 十六进制的255 */
        int MESSAGEID_0X000000FF = 0x000000ff;

        /** 十六进制的-256 */
        int MESSAGEID_0XFFFFFF00 = 0xffffff00;

		StringBuffer result = new StringBuffer("");

		try 
		{
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(data.getBytes("UTF8"));
			byte s[] = m.digest();

			// 转成十六进制字符串
			int size = s.length;
			for (int i = 0; i < size; i++) {
				result.append(Integer.toHexString((MESSAGEID_0X000000FF & s[i]) | MESSAGEID_0XFFFFFF00).substring(6));
			}
		}
		catch (Exception e) 
		{
			logger.error("sign failed", e);
			return null;
		}
		return result.toString();
	}
}
