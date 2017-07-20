package com.newspace.payplatform.unicomiptv;

import java.util.HashMap;
import java.util.Map;

/**
 * @description IPTV响应码
 * @author huqili
 *
 */
public enum IptvReturnCode {

	/**
	 * 联通IPTV，响应失败 
	 */
	UNICOM_IPTV_FAIL(0,"失败"),
	
	/**
	 * 联通IPTV，响应成功
	 */
	UNICOM_IPTV_SUCCESS(1,"成功"),
	
	/**
	 * 联通IPTV，AppId无效
	 */
	UNICOM_IPTV_APPID_INVALID(2,"AppId无效"),
	
	/**
	 * 联通IPTV，Act无效
	 */
	UNICOM_IPTV_ACT_INVALID(3,"Act无效"),
	
	/**
	 * 联通IPTV，参数无效
	 */
	UNICOM_IPTV_PARAM_INVALID(4,"参数无效"),

	/**
	 * 联通IPTV，Sign无效
	 */
	UNICOM_IPTV_SIGN_INVALID(5,"sign无效"),
	
	/**
	 * 联通IPTV，订单号无效
	 */
	UNICOM_IPTV_ORDERNO_INVALID(6,"订单号无效");
	

	/**
	 * 返回码
	 */
	private int returnCode;

	/**
	 * 返回码描述信息
	 */
	private String desc;

	/**
	 * 包含返回码和对应描述信息的map
	 */
	private final static Map<Integer, String> map = new HashMap<Integer, String>();

	static
	{
		for (IptvReturnCode returnCode : IptvReturnCode.values())
			map.put(returnCode.getCode(), returnCode.getDesc());
	}

	IptvReturnCode(int returnCode, String desc)
	{
		this.returnCode = returnCode;
		this.desc = desc;
	}

	/**
	 * 通过返回码得到描述信息
	 * @param returnCode 返回码
	 * @return String  描述信息
	 */
	public static String getDesc(int returnCode)
	{
		String desc = map.get(returnCode);
		return desc == null ? "不存在该返回码" : desc;
	}

	public int getCode()
	{
		return returnCode;
	}

	public String getDesc()
	{
		return desc;
	}
}
