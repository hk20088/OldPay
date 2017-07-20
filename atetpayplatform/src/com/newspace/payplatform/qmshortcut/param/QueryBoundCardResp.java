package com.newspace.payplatform.qmshortcut.param;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.qmshortcut.entity.BankCardInfo;

/**
 * QueryBoundCardResp.java 
 * 描 述:  查询已经绑定的银行卡响应实体类  
 * 作 者:  liushuai
 * 历 史： 2014-8-11 创建
 */
public class QueryBoundCardResp
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private QueryBoundCardRespData dataObj = new QueryBoundCardRespData();

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

	public Integer getCode()
	{
		return dataObj.getCode();
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

	public List<BankCardInfo> getCards()
	{
		return dataObj.getCards();
	}

	public void setCards(List<BankCardInfo> cards)
	{
		dataObj.setCards(cards);
	}

	public String getSign()
	{
		return sign;
	}

	public void setSign(String sign)
	{
		this.sign = sign;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	private static class QueryBoundCardRespData
	{
		private Integer code;

		private String desc;

		private List<BankCardInfo> cards;

		public Integer getCode()
		{
			return code;
		}

		public void setCode(Integer code)
		{
			this.code = code;
		}

		public List<BankCardInfo> getCards()
		{
			return cards;
		}

		public void setCards(List<BankCardInfo> cards)
		{
			this.cards = cards;
		}

		public String getDesc()
		{
			return desc;
		}

		public void setDesc(String desc)
		{
			this.desc = desc;
		}
	}
}