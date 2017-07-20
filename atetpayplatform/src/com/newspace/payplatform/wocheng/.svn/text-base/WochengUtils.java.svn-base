package com.newspace.payplatform.wocheng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newspace.common.coder.Coder;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;

/**
 * @description 沃橙工具类，用来存储沃橙AppKey等信息；校验请求参数合法性
 * @author huqili
 * @date 2016年9月28日
 *
 */
public class WochengUtils {

	private static final JLogger logger = LoggerUtils.getLogger(WochengUtils.class);
	
	private static String encryptvalue = "9067931cbb2069d2c6e39504ba2d4586";
	
	/**
	 * 校验签名是否合法
	 * 加密规则：非空参数按照ASCII排序后按照 “key=value&key=value”的格式拼接起来，最后再加上“&encryptkey=encryptvalue”。
	 * @param map
	 * @return
	 */
	public static boolean verify(Map<String, String> map)
	{
		//取出sign
		String sign = map.get("encryptdata");
		
		//根据规则得到拼装字符串
		String linkStr = createLinkString(map);
		
		//对拼装字符串进行加密
		String _sign = Coder.getHexStringByEncryptMD5(linkStr).toLowerCase();
		
		logger.info(String.format("沃橙传过来的签名为[%s]，生成的待加密字符串为[%s]，生成的签名为[%s]", sign,linkStr,_sign));
		
		if(sign.equals(_sign))
		{
			return true;
		}
		return false;
	}
	
	/** 
	 * 除去Map中的空值和签名参数
	 * @param map 签名参数Map
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	private static Map<String, String> paraFilter(Map<String, String> map)
	{
		Map<String, String> result = new HashMap<String, String>();

		if (map == null || map.size() <= 0)
		{
			return result;
		}

		for (String key : map.keySet())
		{
			String value =map.get(key).toString().replaceAll("\"", "");
			if (key.equalsIgnoreCase("encryptdata"))
				continue;
			result.put(key, value);
			System.out.println("key："+key +"  value："+value);
		}
		return result;
	}
	
	/** 
	 * 把数组所有元素排序，并按照"参数=参数值"的模式用"&"字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private static String createLinkString(Map<String, String> map)
	{
		Map<String, String> params = paraFilter(map);
		
		if(params == null || params.size() <= 0)
		{
			return null;
		}
		
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

		//拼接加密Key
		prestr.append("&encryptkey=").append(encryptvalue);
		return prestr.toString();
	}
}
