package com.newspace.payplatform.qmshortcut.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.StringUtils;

/**
 * GetMsgCheckCodeReq.java 
 * 描 述:  快钱快捷支付，获取短信验证码接口的请求参数类  
 * 作 者:  liushuai
 * 历 史： 2014-8-5 创建
 */
public class GetDynNumReq
{
	@Expose
	private String data;

	@Expose
	private String sign;

	private GetMsgCheckCodeReqData dataObj = new GetMsgCheckCodeReqData();

	private static final JLogger logger = LoggerUtils.getLogger(GetDynNumReq.class);

	/**
	 * 静态工厂方法：用来生成并初始化GetDynNumReq对象
	 */
	public static GetDynNumReq getInstanceFromJson(String json)
	{
		try
		{
			GetDynNumReq content = JsonUtils.fromJson(json, GetDynNumReq.class);
			content.dataObj = JsonUtils.fromJson(content.getData(), GetMsgCheckCodeReqData.class);
			return content;
		}
		catch (Exception e)
		{
			logger.error("解析json字符串失败\r\njson:" + json, e);
		}
		return null;
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

	/**
	 * 判断是否合法：必须的参数是否都进行了设置 
	 */
	public boolean isLegal()
	{
		if (StringUtils.isExistNullOrEmpty(getCardNo(), getPhoneNo(), getOrderNo()) || getAmount() == null || getUserId() == null)
			return false;
		return true;
	}

	public String getPhoneNo()
	{
		return dataObj.getPhoneNo();
	}

	public void setPhoneNo(String phoneNo)
	{
		dataObj.setPhoneNo(phoneNo);
	}

	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
	}

	public Integer getUserId()
	{
		return dataObj.getUserId();
	}

	public void setUserId(Integer userId)
	{
		dataObj.setUserId(userId);
	}

	public Integer getAmount()
	{
		return dataObj.getAmount();
	}

	public void setAmount(Integer amount)
	{
		dataObj.setAmount(amount);
	}

	public String getCardNo()
	{
		return dataObj.getCardNo();
	}

	public void setCardNo(String cardNo)
	{
		dataObj.setCardNo(cardNo);
	}

	public String getExpiredDate()
	{
		return dataObj.getExpiredDate();
	}

	public void setExpiredDate(String expiredDate)
	{
		dataObj.setExpiredDate(expiredDate);
	}

	public String getCvv()
	{
		return dataObj.getCvv();
	}

	public void setCvv(String cvv)
	{
		dataObj.setCvv(cvv);
	}

	public String getHolderName()
	{
		return dataObj.getHolderName();
	}

	public void setHolderName(String holderName)
	{
		dataObj.setHolderName(holderName);
	}

	public Integer getIdType()
	{
		return dataObj.getIdType();
	}

	public void setIdType(Integer idType)
	{
		dataObj.setIdType(idType);
	}

	public String getHolderId()
	{
		return dataObj.getHolderId();
	}

	public void setHolderId(String holderId)
	{
		dataObj.setHolderId(holderId);
	}

	public String getOrderNo()
	{
		return dataObj.getOrderNo();
	}

	public void setOrderNo(String orderNo)
	{
		dataObj.setOrderNo(orderNo);
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

	private static class GetMsgCheckCodeReqData
	{
		// 应用id
		private String appId;

		// 用户id
		private Integer userId;

		// 金额，单位：分
		private Integer amount;

		// 卡号
		private String cardNo;

		// 有效日期
		private String expiredDate;

		// 安全码
		private String cvv;

		// 持卡人姓名
		private String holderName;

		// 持卡人证件类型
		private Integer idType;

		// 持卡人证件号码
		private String holderId;

		// 银行卡预留手机号
		private String phoneNo;

		// 订单号
		private String orderNo;

		public String getAppId()
		{
			return appId;
		}

		public void setAppId(String appId)
		{
			this.appId = appId;
		}

		public Integer getUserId()
		{
			return userId;
		}

		public void setUserId(Integer userId)
		{
			this.userId = userId;
		}

		public Integer getAmount()
		{
			return amount;
		}

		public void setAmount(Integer amount)
		{
			this.amount = amount;
		}

		public String getCardNo()
		{
			return cardNo;
		}

		public void setCardNo(String cardNo)
		{
			this.cardNo = cardNo;
		}

		public String getExpiredDate()
		{
			return expiredDate;
		}

		public void setExpiredDate(String expiredDate)
		{
			this.expiredDate = expiredDate;
		}

		public String getCvv()
		{
			return cvv;
		}

		public void setCvv(String cvv)
		{
			this.cvv = cvv;
		}

		public String getHolderName()
		{
			return holderName;
		}

		public void setHolderName(String holderName)
		{
			this.holderName = holderName;
		}

		public Integer getIdType()
		{
			return idType;
		}

		public void setIdType(Integer idType)
		{
			this.idType = idType;
		}

		public String getHolderId()
		{
			return holderId;
		}

		public void setHolderId(String holderId)
		{
			this.holderId = holderId;
		}

		public String getPhoneNo()
		{
			return phoneNo;
		}

		public void setPhoneNo(String phoneNo)
		{
			this.phoneNo = phoneNo;
		}

		public String getOrderNo()
		{
			return orderNo;
		}

		public void setOrderNo(String orderNo)
		{
			this.orderNo = orderNo;
		}
	}
}