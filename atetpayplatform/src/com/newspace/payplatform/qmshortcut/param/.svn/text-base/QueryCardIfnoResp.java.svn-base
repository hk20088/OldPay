package com.newspace.payplatform.qmshortcut.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * QueryCardIfnoResp.java 
 * 描 述:  根据卡号查询银行卡信息响应参数实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-12 创建
 */
public class QueryCardIfnoResp
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private QueryCardIfnoRespData dataObj = new QueryCardIfnoRespData();

	/**
	 * 获得对象Json串
	 */
	public String getJsonStr()
	{
		data = JsonUtils.toJson(dataObj);
		return JsonUtils.toJsonWithExpose(this);
	}

	/**
	 * 获得data，如果data为空，尝试将dataObj对象转成json串赋值给data 
	 */
	public String getData()
	{
		return StringUtils.isNullOrEmpty(data) ? getNewestData() : data;
	}

	/**
	 * 获取最新的data数据。因为可能dataObj已经发生改变。
	 */
	public String getNewestData()
	{
		data = JsonUtils.toJson(dataObj);
		return data;
	}

	public void setCode(Integer code)
	{
		dataObj.setCode(code);
	}

	public String getDesc()
	{
		return dataObj.getDesc();
	}

	public void setDesc(String desc)
	{
		dataObj.setDesc(desc);
	}

	public String getCardOrg()
	{
		return dataObj.getCardOrg();
	}

	public void setCardOrg(String carOrg)
	{
		dataObj.setCardOrg(carOrg);
	}

	public String getCardType()
	{
		return dataObj.getCardType();
	}

	public void setCardType(String cardType)
	{
		dataObj.setCardType(cardType);
	}

	public String getIssuer()
	{
		return dataObj.getIssuer();
	}

	public void setIssuer(String issuer)
	{
		dataObj.setIssuer(issuer);
	}

	public String getValidFlag()
	{
		return dataObj.getValidFlag();
	}

	public void setValidFlag(String validFlag)
	{
		dataObj.setValidFlag(validFlag);
	}

	public String getValidateType()
	{
		return dataObj.getValidateType();
	}

	public void setValidateType(String validateType)
	{
		dataObj.setValidateType(validateType);
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	private static class QueryCardIfnoRespData
	{
		private Integer code;

		private String desc;

		private String cardOrg;

		private String cardType;

		private String issuer;

		private String validFlag;

		private String validateType;

		public Integer getCode()
		{
			return code;
		}

		public void setCode(Integer code)
		{
			this.code = code;
		}

		public String getDesc()
		{
			return desc;
		}

		public void setDesc(String desc)
		{
			this.desc = desc;
		}

		public String getCardOrg()
		{
			return cardOrg;
		}

		public void setCardOrg(String cardOrg)
		{
			this.cardOrg = cardOrg;
		}

		public String getCardType()
		{
			return cardType;
		}

		public void setCardType(String cardType)
		{
			this.cardType = cardType;
		}

		public String getIssuer()
		{
			return issuer;
		}

		public void setIssuer(String issuer)
		{
			this.issuer = issuer;
		}

		public String getValidFlag()
		{
			return validFlag;
		}

		public void setValidFlag(String validFlag)
		{
			this.validFlag = validFlag;
		}

		public String getValidateType()
		{
			return validateType;
		}

		public void setValidateType(String validateType)
		{
			this.validateType = validateType;
		}
	}
}
