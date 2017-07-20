package com.newspace.payplatform.qmshortcut;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import com.newspace.common.coder.Coder;

/**
 * CertSignUtils.java 
 * 描 述:  使用JAAS校验签名工具类
 * 作 者:  liushuai
 * 历 史： 2014-8-7 创建
 */
public final class CertSignUtils
{
	/**
	 * 校验签名 
	 */
	public static boolean veriSign(byte[] data, byte[] signData, String certFile) throws RuntimeException
	{
		InputStream is = null;
		try
		{
			is = new FileInputStream(certFile);

			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			Certificate cert = cf.generateCertificate(is);

			PublicKey publicKey = cert.getPublicKey();

			Signature sig = Signature.getInstance("SHA1WithRSA");
			byte[] signed = Coder.decryptBASE64(new String(signData));
			sig.initVerify(publicKey);
			sig.update(data);
			return sig.verify(signed);

		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (Exception e)
				{
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 校验XML中的签名 
	 */
	public static boolean veriSignForXml(String tr3Xml, String certFile) throws RuntimeException
	{
		String dataBeforeSign = tr3Xml.replaceAll("<signature>.*</signature>", "");
		int beginIndex = tr3Xml.indexOf("<signature>");
		int endIndex = tr3Xml.indexOf("</signature>");
		String signData = tr3Xml.substring(beginIndex + 11, endIndex);

		try
		{
			return veriSign(dataBeforeSign.getBytes("UTF-8"), signData.getBytes("UTF-8"), certFile);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}