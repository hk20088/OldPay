package com.newspace.common.coder;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AESCoder.java 
 * 描 述:  采用AES算法的加密、解密类， java api自带了对AES算法的支持，而且还使用了Base64进行了2次加密。
 * 		   在对称加密算法中，数据发信方将明文（原始数据）和加密密钥一起经过特殊加密算法处理后，使其变成复杂的加密密文发送出去。
 * 		   收信方收到密文后，若想解读原文，则需要使用加密用过的密钥及相同算法的逆算法对密文进行解密，才能使其恢复成可读明文。
 *         在对称加密算法中，使用的密钥只有一个，发收信双方都使用这个密钥对数据进行加密和解密，这就要求解密方事先必须知道加密密钥。
 * 				
 * 作 者:  liushuai
 * 历 史： 2014-4-15 创建
 */
public class AESCoder extends Coder
{
	/**
	 * 算法名称
	 */
	public static final String ALGORITHM_TYPE_AES = "AES";

	/**
	 * 向量。(密钥和向量通信双方事先需要沟通好，因为使用的是同一个密钥)
	 * iv的作用主要是用于产生密文的第一个block，以使最终生成的密文产生差异（明文相同的情况下），
	 * 使密码攻击变得更为困难，除此之外iv并无其它用途。因此iv通过随机方式产生是一种十分简便、有效的途径。
	 */
	public static byte[] AES_IV = new byte[] { 0x10, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b,
			0x0c, 0x0d, 0x0e, 0x0f };

	/**
	 * 加密,对原数据进行AES加密，并采用BASE64二次加密
	 * @param code 要加密的byte数组
	 * @param key 密钥
	 */
	public static String encrypt(byte[] data, String key) throws Exception
	{
		SecretKeySpec keySpec = toKey(key);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// algorithm 加密算法/mode 块模式/padding 填充算法
		IvParameterSpec iv = new IvParameterSpec(AES_IV); // CBC模式需要一个初始化向量
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

		return encryptBASE64(cipher.doFinal(data));
	}

	/**
	 * 解密，对加密过的数据先进行BASE64解密，再采用AES解密
	 * @param data 要解密的数据
	 * @param key 密钥
	 */
	public static String decrypt(String data, String key) throws Exception
	{
		SecretKeySpec skeySpec = toKey(key);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(AES_IV);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

		byte[] original = cipher.doFinal(decryptBASE64(data));
		String originalString = new String(original);
		return originalString;
	}

	/**
	 * 生成密钥字符串
	 * @param seed  随机种子
	 * @return 密钥字符串
	 */
	public static String initKeyStr(String seed) throws Exception
	{
		SecureRandom secureRandom = null;
		if (seed != null)
			secureRandom = new SecureRandom(decryptBASE64(seed));
		else
			secureRandom = new SecureRandom();
		KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM_TYPE_AES);
		generator.init(secureRandom);
		SecretKey secretKey = generator.generateKey();

		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * 重载方法：生成密钥字符串
	 */
	public static String initKeyStr() throws Exception
	{
		return initKeyStr(null);
	}

	/**
	 * 私有方法：转换密钥。
	 * 密钥的长度必须为16.如果不等于16则进行相关填充或舍弃。
	 * @param strKey 密钥字符串
	 * @return Key对象
	 */
	private static SecretKeySpec toKey(String strKey) throws UnsupportedEncodingException
	{
		byte[] strBytes = strKey.getBytes(DEFAULT_CHARSET);
		byte[] keyBytes = new byte[16];
		for (int i = 0; i < strBytes.length && i < keyBytes.length; i++)
		{
			keyBytes[i] = strBytes[i];
		}
		// 根据一个字节数组构造一个 SecretKey，而无须通过一个（基于 provider的）SecretKeyFactory。
		SecretKeySpec spec = new SecretKeySpec(keyBytes, ALGORITHM_TYPE_AES);
		return spec;
	}

	public static void main(String[] args) throws Exception
	{
		String code = "测试agbc";

		// 生成密钥字符串
		String keyStr = initKeyStr();
		System.out.println("密钥:" + keyStr);

		// 密钥字符串，也可以自己随意输入
		String str = encrypt(code.getBytes(), keyStr);
		System.out.println("加密后数据：" + str);

		String str2 = decrypt(str, keyStr);

		System.out.println("解密后数据：" + str2);
	}
}