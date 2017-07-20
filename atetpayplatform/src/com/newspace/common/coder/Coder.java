package com.newspace.common.coder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Coder.java 
 * 描 述:  加密解密类的基类，包含了基本的MD5、SHA、BASE64等加密解密方法。  
 * 作 者:  liushuai
 * 历 史： 2014-4-14 创建
 */
public class Coder
{
	/**
	 * 摘要算法：MD5
	 */
	private static final String DIGEST_TYPE_MD5 = "MD5";

	/**
	 * 散列算法：SHA
	 */
	private static final String DIGEST_TYPE_SHA = "SHA";

	/**
	 * 默认的编码格式
	 */
	protected static final String DEFAULT_CHARSET = "utf-8";

	/**
	 * BASE64加密
	 * @param data 要进行加密的字节数组
	 * @return String 加密之后的字符串
	 */
	public static String encryptBASE64(byte[] data)
	{
		return Base64.encode(data);
	}

	/**
	 * BASE64解密
	 * @param data 要进行解密的字符串
	 * @return byte[] 解密之后的原字节数组
	 */
	public static byte[] decryptBASE64(String data)
	{
		return Base64.decode(data);
	}

	/**
	 * MD5加密 
	 * @param data 要进行加密的字节数组
	 * @return byte[] 加密之后的字节数组
	 */
	public static byte[] encryptMD5(byte[] data)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance(DIGEST_TYPE_MD5);
			return digest.digest(data);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * SHA加密 
	 * @param data 要进行加密的字节数组
	 * @return byte[] 加密之后的字节数组
	 */
	public static byte[] encryptSHA(byte[] key)
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance(DIGEST_TYPE_SHA);
			return digest.digest(key);
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * MD5加密，并转换成16进制表示
	 * @param data  要加密的字节数组
	 * @return String 加密后的字符串
	 */
	public static String getHexStringByEncryptMD5(byte[] data)
	{
		byte[] md5Bytes = encryptMD5(data);
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < md5Bytes.length; i++)
		{
			int tmp = md5Bytes[i];
			while (tmp < 0)
				tmp += 256;
			if (tmp < 16)
				str.append("0");
			str.append(Integer.toString(tmp, 16));
		}
		return str.toString().toUpperCase();
	}

	/**
	 * 重载方法，MD5加密，并转成16进制表示
	 * @param str 要加密的字符串
	 * @return String 加密后的字符串
	 */
	public static String getHexStringByEncryptMD5(String str)
	{
		try
		{
			return getHexStringByEncryptMD5(str.getBytes(DEFAULT_CHARSET));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * SHA加密，并转换成16进制表示
	 * @param data  要加密的字节数组
	 * @return String 加密后的字符串
	 */
	public static String getHexStringByEncryptSHA(byte[] bytes)
	{
		byte[] shaBytes = encryptSHA(bytes);

		StringBuilder str = new StringBuilder();
		for (int i = 0; i < shaBytes.length; i++)
		{
			int tmp = shaBytes[i] & 0xff;
			if (tmp < 16)
				str.append("0");
			str.append(Integer.toString(tmp, 16));
		}
		return str.toString().toUpperCase();
	}

	/**
	 * 重载方法：SHA加密，并转换成16进制表示
	 * @param data  要加密的字节数组
	 * @return String 加密后的字符串
	 */
	public static String getHexStringByEncryptSHA(String str)
	{
		try
		{
			return getHexStringByEncryptSHA(str.getBytes(DEFAULT_CHARSET));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}