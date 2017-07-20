package com.newspace.payplatform.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newspace.common.coder.RSACoder;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.StringUtils;

public class AlipayNotifyUtils
{
	/**
	 * 支付宝消息验证地址
	 */
	private static final String HTTPS_VERIFY_URL = "https://mapi.alipay.com/gateway.do?service=notify_verify&";

	/**
	 * 支付宝（RSA）公钥 用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
	 */
	public static final String ALI_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	/**
	 * 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	 */
	public static final String PARTNER = "2088901574104678";

	private static JLogger logger = LoggerUtils.getLogger(AlipayNotifyUtils.class);

	/**
	 * 验证消息是否是支付宝发出的合法消息
	 * @param params 通知返回来的参数数组
	 * @return 验证结果
	 */
	public static boolean verify(Map<String, String> params)
	{
		String responseTxt = "true";
		if (params.get("notify_id") != null)
		{
			String notify_id = params.get("notify_id");
			responseTxt = verifyResponse(notify_id);
		}

		String sign = StringUtils.isNullOrEmpty(params.get("sign")) ? "" : params.get("sign");
		boolean isSign = signVeryfy(params, sign);
		logger.info(String.format("\r\n【校验结果：%s，是否是支付宝的异步通知：%s】", isSign, responseTxt));
		if (isSign && responseTxt.equals("true"))
			return true;
		return false;
	}

	/**
	 * 根据反馈回来的信息，生成签名结果
	 * @param Params 通知返回来的参数数组
	 * @param sign 比对的签名结果
	 * @return 生成的签名结果
	 */
	private static boolean signVeryfy(Map<String, String> Params, String sign)
	{
		Map<String, String> sParaNew = paraFilter(Params);
		String preSignStr = createLinkString(sParaNew);
		return RSACoder.verify(preSignStr, ALI_PUBLIC_KEY, sign);
	}

	/** 
	 * 除去Map中的空值和签名参数
	 * @param sArray 签名参数Map
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	private static Map<String, String> paraFilter(Map<String, String> map)
	{
		Map<String, String> result = new HashMap<String, String>();

		if (map == null || map.size() <= 0)
			return result;

		for (String key : map.keySet())
		{
			String value = map.get(key);
			if (StringUtils.isNullOrEmpty(value) || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type"))
				continue;
			result.put(key, value);
		}
		return result;
	}

	/** 
	 * 把数组所有元素排序，并按照"参数=参数值"的模式用"&"字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private static String createLinkString(Map<String, String> params)
	{
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		StringBuilder prestr = new StringBuilder();

		for (int i = 0; i < keys.size(); i++)
		{
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1)
				prestr.append(key).append("=").append(value);
			else
				prestr.append(key).append("=").append(value).append("&");
		}

		return prestr.toString();
	}

	/**
	* 获取远程服务器ATN结果,验证返回URL
	* @param notify_id 通知校验ID
	* @return 服务器ATN结果
	* 验证结果集：
	* invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
	* true 返回正确信息
	* false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	*/
	private static String verifyResponse(String notify_id)
	{
		// 获取远程服务器ATN结果，验证是否是支付宝服务器发来的请求
		String veryfy_url = HTTPS_VERIFY_URL + "partner=" + PARTNER + "&notify_id=" + notify_id;
		return checkUrl(veryfy_url);
	}

	/**
	* 获取远程服务器ATN结果
	* @param urlvalue 指定URL路径地址
	* @return 服务器ATN结果
	* 验证结果集：
	* invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 
	* true 返回正确信息
	* false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
	*/
	private static String checkUrl(String urlvalue)
	{
		String inputLine;

		try
		{
			URL url = new URL(urlvalue);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			inputLine = in.readLine();
			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inputLine = "";
		}

		return inputLine;
	}
}