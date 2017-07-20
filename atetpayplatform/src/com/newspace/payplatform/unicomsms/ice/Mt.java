// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `SmInt.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.newspace.payplatform.unicomsms.ice;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.newspace.common.utils.PropertiesUtils;

public class Mt implements java.lang.Cloneable, java.io.Serializable
{
	/**
	 * 源号码
	 */
	public String strSpServiceNumber;

	/**
	 * 计费号码
	 */
	public String strChargeNumber;

	/**
	 * 目标用户数，默认为1
	 */
	public byte cUserCount;

	/**
	 * 目的号码
	 */
	public String strUserNumber;

	/**
	 * 企业ID号码 应用层无需填写
	 */
	public String strSpNumber;

	/**
	 * 业务代码
	 */
	public String strServiceCode;

	/**
	 * 计费类型
	 */
	public byte cFeeType;

	/**
	 * 计费金额
	 */
	public String strFeeCode;

	/**
	 * 赠送话费
	 */
	public String strGivenCode;

	/**
	 * 代收费标志，0：应收；1：实收
	 */
	public byte cAgentFlag;

	/**
	 * 引起MT消息的原因：
	 * 0-MO点播引起的第一条MT消息;
	 * 1-MO点播引起的非第一条MT消息；
	 * 2-非MO点播引起的MT消息；
	 * 3-系统反馈引起的MT消息。
	 */
	public byte cMtReason;

	/**
	 * 优先级，暂时无用
	 */
	public byte cPriority;

	public String strExpireTime;

	public String strAtTime;

	/**
	 * 状态报告标记
	 * 0-该条消息只有最后出错时要返回状态报告
	 * 1-该条消息无论最后是否成功都要返回状态报告
	 * 2-该条消息不需要返回状态报告
	 * 3-该条消息仅携带包月计费信息，不下发给用户，要返回状态报告
	 */
	public byte cReportFlag;

	/**
	 * PID 默认0
	 */
	public byte cPID;

	/**
	 * UDHI 长短信时为1
	 */
	public byte cUDHI;

	/**
	 * 短信编码方式
	 */
	public byte cMsgFormat;

	/**
	 * 消息类型，默认0
	 */
	public byte cMsgType;

	/**
	 * 消息长度
	 */
	public int lMsgLen;

	/**
	 * 短信内容
	 */
	public byte[] strMsgContent;

	/**
	 * LinkId
	 */
	public String strReserve;

	public static final String MT_SPSERVICENUMBER;

	public static final byte MT_USERCOUNT;

	public static final String MT_SPNUMBER;

	public static final byte MT_FEETYPE;

	public static final String MT_SERVICECODE;

	public static final String MT_FEECODE;

	public static final String MT_GIVENCODE;

	public static final byte MT_AGENTFLAG;

	public static final byte MT_MTREASON;

	public static final String MT_EXPIRETIME;

	public static final byte MT_PRIORITY;

	public static final String MT_ATTIME;

	public static final byte MT_PID;

	public static final byte MT_REPORTFLAG;

	public static final byte MT_MSGTYPE;

	public static final String MT_RESERVE;

	static
	{
		Properties prop = PropertiesUtils.getProps("configuration");
		MT_SPSERVICENUMBER = prop.getProperty("mt_strSpServiceNumber");
		MT_USERCOUNT = Byte.parseByte(prop.getProperty("mt_cUserCount"));
		MT_SPNUMBER = prop.getProperty("mt_strSpNumber");
		MT_SERVICECODE = prop.getProperty("mt_strServiceCode");
		MT_FEETYPE = Byte.parseByte(prop.getProperty("mt_cFeeType"));
		MT_FEECODE = prop.getProperty("mt_strFeeCode");
		MT_GIVENCODE = prop.getProperty("mt_strGivenCode");
		MT_AGENTFLAG = Byte.parseByte(prop.getProperty("mt_cAgentFlag"));
		MT_MTREASON = Byte.parseByte(prop.getProperty("mt_cMtReason"));
		MT_EXPIRETIME = prop.getProperty("mt_strExpireTime");
		MT_PRIORITY = Byte.parseByte(prop.getProperty("mt_cPriority"));
		MT_ATTIME = prop.getProperty("mt_strAtTime");
		MT_PID = Byte.parseByte(prop.getProperty("mt_cPID"));
		MT_REPORTFLAG = Byte.parseByte(prop.getProperty("mt_cReportFlag"));
		MT_MSGTYPE = Byte.parseByte(prop.getProperty("mt_cMsgType"));
		MT_RESERVE = prop.getProperty("mt_strReserve");
	}

	/**
	 * 生成下行短信所需Mt对象 
	 * @param telephone 要发送到的手机号
	 * @param content 短息内容
	 */
	public static Mt fillMt(String telephone, String content)
	{
		Mt mt = new Mt();
		mt.strSpServiceNumber = MT_SPSERVICENUMBER;
		mt.cUserCount = MT_USERCOUNT;
		mt.strSpNumber = MT_SPNUMBER;
		mt.strServiceCode = MT_SERVICECODE;
		mt.cFeeType = MT_FEETYPE;
		mt.strFeeCode = MT_FEECODE;
		mt.strGivenCode = MT_GIVENCODE;
		mt.cAgentFlag = MT_AGENTFLAG;
		mt.cMtReason = MT_MTREASON;
		mt.cPriority = MT_PRIORITY;
		mt.strExpireTime = MT_EXPIRETIME;
		mt.strAtTime = MT_ATTIME;
		mt.cPID = MT_PID;
		mt.cReportFlag = MT_REPORTFLAG;
		mt.cMsgType = MT_MSGTYPE;
		mt.strReserve = MT_RESERVE;

		mt.strChargeNumber = telephone;
		mt.strUserNumber = telephone;
		mt.cUDHI = 0;
		mt.lMsgLen = content.length();

		mt.cMsgFormat = 15;
		try
		{
			mt.strMsgContent = content.getBytes("GBK");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return mt;
	}

	public boolean equals(java.lang.Object rhs)
	{
		if (this == rhs)
		{
			return true;
		}
		Mt _r = null;
		if (rhs instanceof Mt)
		{
			_r = (Mt) rhs;
		}

		if (_r != null)
		{
			if (strSpServiceNumber != _r.strSpServiceNumber)
			{
				if (strSpServiceNumber == null || _r.strSpServiceNumber == null || !strSpServiceNumber.equals(_r.strSpServiceNumber))
				{
					return false;
				}
			}
			if (strChargeNumber != _r.strChargeNumber)
			{
				if (strChargeNumber == null || _r.strChargeNumber == null || !strChargeNumber.equals(_r.strChargeNumber))
				{
					return false;
				}
			}
			if (cUserCount != _r.cUserCount)
			{
				return false;
			}
			if (strUserNumber != _r.strUserNumber)
			{
				if (strUserNumber == null || _r.strUserNumber == null || !strUserNumber.equals(_r.strUserNumber))
				{
					return false;
				}
			}
			if (strSpNumber != _r.strSpNumber)
			{
				if (strSpNumber == null || _r.strSpNumber == null || !strSpNumber.equals(_r.strSpNumber))
				{
					return false;
				}
			}
			if (strServiceCode != _r.strServiceCode)
			{
				if (strServiceCode == null || _r.strServiceCode == null || !strServiceCode.equals(_r.strServiceCode))
				{
					return false;
				}
			}
			if (cFeeType != _r.cFeeType)
			{
				return false;
			}
			if (strFeeCode != _r.strFeeCode)
			{
				if (strFeeCode == null || _r.strFeeCode == null || !strFeeCode.equals(_r.strFeeCode))
				{
					return false;
				}
			}
			if (strGivenCode != _r.strGivenCode)
			{
				if (strGivenCode == null || _r.strGivenCode == null || !strGivenCode.equals(_r.strGivenCode))
				{
					return false;
				}
			}
			if (cAgentFlag != _r.cAgentFlag)
			{
				return false;
			}
			if (cMtReason != _r.cMtReason)
			{
				return false;
			}
			if (cPriority != _r.cPriority)
			{
				return false;
			}
			if (strExpireTime != _r.strExpireTime)
			{
				if (strExpireTime == null || _r.strExpireTime == null || !strExpireTime.equals(_r.strExpireTime))
				{
					return false;
				}
			}
			if (strAtTime != _r.strAtTime)
			{
				if (strAtTime == null || _r.strAtTime == null || !strAtTime.equals(_r.strAtTime))
				{
					return false;
				}
			}
			if (cReportFlag != _r.cReportFlag)
			{
				return false;
			}
			if (cPID != _r.cPID)
			{
				return false;
			}
			if (cUDHI != _r.cUDHI)
			{
				return false;
			}
			if (cMsgFormat != _r.cMsgFormat)
			{
				return false;
			}
			if (cMsgType != _r.cMsgType)
			{
				return false;
			}
			if (lMsgLen != _r.lMsgLen)
			{
				return false;
			}
			if (!java.util.Arrays.equals(strMsgContent, _r.strMsgContent))
			{
				return false;
			}
			if (strReserve != _r.strReserve)
			{
				if (strReserve == null || _r.strReserve == null || !strReserve.equals(_r.strReserve))
				{
					return false;
				}
			}

			return true;
		}

		return false;
	}

	public int hashCode()
	{
		int __h = 5381;
		__h = IceInternal.HashUtil.hashAdd(__h, "::SmModule::Mt");
		__h = IceInternal.HashUtil.hashAdd(__h, strSpServiceNumber);
		__h = IceInternal.HashUtil.hashAdd(__h, strChargeNumber);
		__h = IceInternal.HashUtil.hashAdd(__h, cUserCount);
		__h = IceInternal.HashUtil.hashAdd(__h, strUserNumber);
		__h = IceInternal.HashUtil.hashAdd(__h, strSpNumber);
		__h = IceInternal.HashUtil.hashAdd(__h, strServiceCode);
		__h = IceInternal.HashUtil.hashAdd(__h, cFeeType);
		__h = IceInternal.HashUtil.hashAdd(__h, strFeeCode);
		__h = IceInternal.HashUtil.hashAdd(__h, strGivenCode);
		__h = IceInternal.HashUtil.hashAdd(__h, cAgentFlag);
		__h = IceInternal.HashUtil.hashAdd(__h, cMtReason);
		__h = IceInternal.HashUtil.hashAdd(__h, cPriority);
		__h = IceInternal.HashUtil.hashAdd(__h, strExpireTime);
		__h = IceInternal.HashUtil.hashAdd(__h, strAtTime);
		__h = IceInternal.HashUtil.hashAdd(__h, cReportFlag);
		__h = IceInternal.HashUtil.hashAdd(__h, cPID);
		__h = IceInternal.HashUtil.hashAdd(__h, cUDHI);
		__h = IceInternal.HashUtil.hashAdd(__h, cMsgFormat);
		__h = IceInternal.HashUtil.hashAdd(__h, cMsgType);
		__h = IceInternal.HashUtil.hashAdd(__h, lMsgLen);
		__h = IceInternal.HashUtil.hashAdd(__h, strMsgContent);
		__h = IceInternal.HashUtil.hashAdd(__h, strReserve);
		return __h;
	}

	public java.lang.Object clone()
	{
		java.lang.Object o = null;
		try
		{
			o = super.clone();
		}
		catch (CloneNotSupportedException ex)
		{
			assert false; // impossible
		}
		return o;
	}

	public void __write(IceInternal.BasicStream __os)
	{
		__os.writeString(strSpServiceNumber);
		__os.writeString(strChargeNumber);
		__os.writeByte(cUserCount);
		__os.writeString(strUserNumber);
		__os.writeString(strSpNumber);
		__os.writeString(strServiceCode);
		__os.writeByte(cFeeType);
		__os.writeString(strFeeCode);
		__os.writeString(strGivenCode);
		__os.writeByte(cAgentFlag);
		__os.writeByte(cMtReason);
		__os.writeByte(cPriority);
		__os.writeString(strExpireTime);
		__os.writeString(strAtTime);
		__os.writeByte(cReportFlag);
		__os.writeByte(cPID);
		__os.writeByte(cUDHI);
		__os.writeByte(cMsgFormat);
		__os.writeByte(cMsgType);
		__os.writeInt(lMsgLen);
		strConentSeqHelper.write(__os, strMsgContent);
		__os.writeString(strReserve);
	}

	public void __read(IceInternal.BasicStream __is)
	{
		strSpServiceNumber = __is.readString();
		strChargeNumber = __is.readString();
		cUserCount = __is.readByte();
		strUserNumber = __is.readString();
		strSpNumber = __is.readString();
		strServiceCode = __is.readString();
		cFeeType = __is.readByte();
		strFeeCode = __is.readString();
		strGivenCode = __is.readString();
		cAgentFlag = __is.readByte();
		cMtReason = __is.readByte();
		cPriority = __is.readByte();
		strExpireTime = __is.readString();
		strAtTime = __is.readString();
		cReportFlag = __is.readByte();
		cPID = __is.readByte();
		cUDHI = __is.readByte();
		cMsgFormat = __is.readByte();
		cMsgType = __is.readByte();
		lMsgLen = __is.readInt();
		strMsgContent = strConentSeqHelper.read(__is);
		strReserve = __is.readString();
	}

	public static final long serialVersionUID = 1162889398L;
}
