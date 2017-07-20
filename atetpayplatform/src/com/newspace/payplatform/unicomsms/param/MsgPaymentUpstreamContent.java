package com.newspace.payplatform.unicomsms.param;

import com.google.gson.annotations.Expose;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.StringUtils;

/**
 * MsgPaymentUpstreamContent.java 
 * 描 述:  短信支付上行短信内容
 * 作 者:  liushuai
 * 历 史： 2014-7-20 创建
 */
public class MsgPaymentUpstreamContent
{
	/**
	 *  短信支付上行短信data
	 */
	@Expose
	private String data;

	/**
	 * 对data进行加密产生的签名
	 */
	@Expose
	private String sign;

	private MsgPaymentUpstreamData dataObj = new MsgPaymentUpstreamData();

	private static final JLogger logger = LoggerUtils.getLogger(MsgPaymentUpstreamContent.class);

	/**
	 * 静态工厂方法：用来生成并初始化MsgPaymentUpstreamContent对象
	 */
	public static MsgPaymentUpstreamContent getInstanceFromJson(String json)
	{
		try
		{
			MsgPaymentUpstreamContent content = new MsgPaymentUpstreamContent();
			content.setData(json.substring(0, json.lastIndexOf("&")));
			content.setSign(json.substring(json.lastIndexOf("=") + 1));

			logger.debug("弱联网支付_获取到的短信内容：" + json);
			logger.debug("弱联网支付_待加密串：" + content.getData());
			logger.debug("弱联网支付_加密串：" + content.getSign());

			/**
			 * 将短信内容解析成MsgPaymentUpstreamData对象
			 */
			String[] str = json.split("&");
			content.setPartnerId(str[0].substring(2));
			content.setUserId(Integer.parseInt(str[1].substring(2)));
			content.setAppId(str[2].substring(2));
			content.setDeviceId(str[3].substring(2));
			content.setPayPoint(str[4].substring(2));
			content.setCounts(Integer.parseInt(str[5].substring(2)));
			content.setAmount(Integer.parseInt(str[6].substring(2)));
			content.setOrderNo(str[7].substring(2));
			content.setMsgType(Integer.parseInt(str[8].substring(2)));
			content.setIsReceipt(Integer.parseInt(str[8].substring(2)));

			logger.debug("partnerId: " + content.dataObj.getPartnerId() + ", userId: " + content.dataObj.getUserId() + ", appId: "
					+ content.dataObj.getAppId() + ", deviceId: " + content.dataObj.getDeviceId() + ", payPoint: "
					+ content.dataObj.getPayPoint() + ", counts: " + content.dataObj.getCounts() + ", amount: "
					+ content.dataObj.getAmount() + ", orderNo: " + content.dataObj.getOrderNo() + ", msgType: "
					+ content.dataObj.getMsgType());

			return content;
		}
		catch (IndexOutOfBoundsException e)
		{
			logger.error("弱联网支付_解析短信字符串失败\r\ncontent:" + json, e);
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
		StringBuilder sb = new StringBuilder();

		sb.append("a=").append(dataObj.getPartnerId());
		sb.append("&b=").append(dataObj.getUserId());
		sb.append("&c=").append(dataObj.getAppId());
		sb.append("&d=").append(dataObj.getDeviceId());
		sb.append("&e=").append(dataObj.getPayPoint());
		sb.append("&f=").append(dataObj.getCounts());
		sb.append("&g=").append(dataObj.getAmount());
		sb.append("&h=").append(dataObj.getOrderNo());
		sb.append("&i=").append(dataObj.getMsgType());
		sb.append("&j=").append(dataObj.getIsReceipt());

		return sb.toString();
	}

	public Integer getIsReceipt()
	{
		return dataObj.getIsReceipt();
	}

	public void setIsReceipt(Integer isReceipt)
	{
		dataObj.setIsReceipt(isReceipt);
	}

	public String getPartnerId()
	{
		return dataObj.getPartnerId();
	}

	public void setPartnerId(String partnerId)
	{
		dataObj.setPartnerId(partnerId);
	}

	public Integer getUserId()
	{
		return dataObj.getUserId();
	}

	public void setUserId(Integer userId)
	{
		dataObj.setUserId(userId);
	}

	public String getDeviceId()
	{
		return dataObj.getDeviceId();
	}

	public void setDeviceId(String deviceId)
	{
		dataObj.setDeviceId(deviceId);
	}

	public String getPayPoint()
	{
		return dataObj.getPayPoint();
	}

	public void setPayPoint(String payPoint)
	{
		dataObj.setPayPoint(payPoint);
	}

	public Integer getCounts()
	{
		return dataObj.getCounts();
	}

	public void setCounts(Integer counts)
	{
		dataObj.setCounts(counts);
	}

	public Integer getAmount()
	{
		return dataObj.getAmount();
	}

	public void setAmount(Integer amount)
	{
		dataObj.setAmount(amount);
	}

	public String getOrderNo()
	{
		return dataObj.getOrderNo();
	}

	public void setOrderNo(String orderNo)
	{
		dataObj.setOrderNo(orderNo);
	}

	public Integer getMsgType()
	{
		return dataObj.getMsgType();
	}

	public void setMsgType(Integer msgType)
	{
		dataObj.setMsgType(msgType);
	}

	public String getAppId()
	{
		return dataObj.getAppId();
	}

	public void setAppId(String appId)
	{
		dataObj.setAppId(appId);
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

	/**
	 * 静态内部类：短信内容中data对应的实体类
	 */
	private static class MsgPaymentUpstreamData extends UpstreamData
	{
		private String partnerId;

		private Integer userId;

		private String deviceId;

		private String payPoint;

		private Integer counts;

		private Integer amount;

		private String orderNo;

		// 是否需要发送回执短息给用户，0：不需要，1:需要。默认为0。
		private Integer isReceipt;

		public String getPartnerId()
		{
			return partnerId;
		}

		public void setPartnerId(String partnerId)
		{
			this.partnerId = partnerId;
		}

		public Integer getUserId()
		{
			return userId;
		}

		public void setUserId(Integer userId)
		{
			this.userId = userId;
		}

		public String getDeviceId()
		{
			return deviceId;
		}

		public void setDeviceId(String deviceId)
		{
			this.deviceId = deviceId;
		}

		public String getPayPoint()
		{
			return payPoint;
		}

		public void setPayPoint(String payPoint)
		{
			this.payPoint = payPoint;
		}

		public Integer getCounts()
		{
			return counts;
		}

		public void setCounts(Integer counts)
		{
			this.counts = counts;
		}

		public Integer getAmount()
		{
			return amount;
		}

		public void setAmount(Integer amount)
		{
			this.amount = amount;
		}

		public String getOrderNo()
		{
			return orderNo;
		}

		public void setOrderNo(String orderNo)
		{
			this.orderNo = orderNo;
		}

		public Integer getIsReceipt()
		{
			return isReceipt;
		}

		public void setIsReceipt(Integer isReceipt)
		{
			this.isReceipt = isReceipt;
		}
	}
}