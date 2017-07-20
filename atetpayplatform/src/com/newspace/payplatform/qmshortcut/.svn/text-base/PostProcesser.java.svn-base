package com.newspace.payplatform.qmshortcut;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.newspace.common.coder.Coder;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.payplatform.qmshortcut.entity.MerchantInfo;
import com.newspace.payplatform.qmshortcut.entity.MyX509TrustManager;

/**
 * PostProcesser.java 
 * 描 述:  数据发送类  
 * 作 者:  liushuai
 * 历 史： 2014-8-5 创建
 */
public class PostProcesser
{
	private static final JLogger logger = LoggerUtils.getLogger(PostProcesser.class);

	/**
	 * 发送数据
	 * @param merchant 商户信息对象
	 * @return InputStream 输入流对象
	 */
	public InputStream post(MerchantInfo merchant) throws Exception
	{
		File certFile = new File(merchant.getCertPath());
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(new FileInputStream(certFile), merchant.getCertPass().toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, merchant.getCertPass().toCharArray());

		TrustManager[] tm = { new MyX509TrustManager() };

		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(kmf.getKeyManagers(), tm, null);

		SSLSocketFactory factory = sslContext.getSocketFactory();

		URL url = new URL(merchant.getUrl());
		HttpsURLConnection urlc = (HttpsURLConnection) url.openConnection();
		urlc.setSSLSocketFactory(factory);
		urlc.setDoOutput(true);
		urlc.setDoInput(true);
		urlc.setReadTimeout(merchant.getTimeOut() * 1000);

		String authString = merchant.getMerchantId() + ":" + merchant.getPassword();
		String auth = "Basic " + Coder.encryptBASE64(authString.getBytes());
		urlc.setRequestProperty("Authorization", auth);

		OutputStream out = urlc.getOutputStream();
		out.write(merchant.getXml().getBytes("UTF-8"));
		out.flush();
		out.close();

		return urlc.getInputStream();
	}

	/**
	 * 发送数据
	 * @param merchant 商户信息对象
	 * @return xml xml格式的响应信息
	 * @throws ConnectException 
	 */
	public String send(MerchantInfo merchant) throws ConnectException
	{
		try
		{
			InputStream is = post(merchant);
			if (is != null)
			{
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] receiveBuffer = new byte[1024];
				int readBytesSize = is.read(receiveBuffer);
				while (readBytesSize != -1)
				{
					bos.write(receiveBuffer, 0, readBytesSize);
					readBytesSize = is.read(receiveBuffer);
				}
				return new String(bos.toByteArray(), "UTF-8");
			}
		}
		catch (Exception e)
		{
			logger.error("发送数据操作失败: " + e.getMessage(), e);
			if (e instanceof ConnectException || e instanceof SocketTimeoutException)
				throw new ConnectException(e.getMessage());
		}
		return null;
	}
}