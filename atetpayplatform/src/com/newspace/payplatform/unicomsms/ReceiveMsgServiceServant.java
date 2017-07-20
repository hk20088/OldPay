package com.newspace.payplatform.unicomsms;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Ice.Current;
import Ice.StringHolder;

import com.newspace.common.coder.Coder;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.NativeEncryptUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.utils.TwoTuple;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.bindtelephone.bo.BindTelephoneBo;
import com.newspace.payplatform.bindtelephone.vo.BindTelephoneVo;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.paynotify.NotifyContent;
import com.newspace.payplatform.paynotify.NotifyEntity;
import com.newspace.payplatform.syncdevice.vo.SyncDeviceVo;
import com.newspace.payplatform.unicompay.IcePaymentClient;
import com.newspace.payplatform.unicompay.ice.ResVacBean;
import com.newspace.payplatform.unicomsms.ice.Mo;
import com.newspace.payplatform.unicomsms.ice.Mt;
import com.newspace.payplatform.unicomsms.ice.MtReport;
import com.newspace.payplatform.unicomsms.ice.MtResp;
import com.newspace.payplatform.unicomsms.param.BindTelUpstreamContent;
import com.newspace.payplatform.unicomsms.param.GetOrderNoDownstreamContent;
import com.newspace.payplatform.unicomsms.param.GetOrderNoUpstreamContent;
import com.newspace.payplatform.unicomsms.param.MsgPaymentDownstreamContent;
import com.newspace.payplatform.unicomsms.param.MsgPaymentUpstreamContent;
import com.newspace.payplatform.unicomsms.param.UpstreamContent;
import com.newspace.payplatform.unicomsms.param.UpstreamData;

/**
 * ServiceI.java 
 * 描 述:  接收联通短信服务接口  
 * 作 者:  liushuai
 * 历 史： 2014-7-15 创建
 */
@SuppressWarnings("serial")
public class ReceiveMsgServiceServant extends com.newspace.payplatform.unicomsms.ice._SmMsgDisp
{
	private static final BindTelephoneBo bindTelBo = SpringBeanUtils.getBean(BindTelephoneBo.class);

	private static final ExecutorService pool = Executors.newCachedThreadPool();

	private static final JLogger logger = LoggerUtils.getLogger(ReceiveMsgServiceServant.class);

	/**
	 * 网关为服务端   SP为客户端
	 * @return int 0: 成功;1: 用户名密码错误 ;2: IP错误
	 */
	@Override
	public int Login(String strUserName, String strPasswd, Current current)
	{
		return 0;
	}

	/**
	 * 网关为服务端   SP为客户端
	 * @return int 0:成功;1:为客户端信息错误断开连接，需要重新进行Login
	 * 			   2:消息结构错误;3:超流量错误
	 */
	@Override
	public int OnMt(Mt sMt, StringHolder strGWID, Current current)
	{
		return 0;
	}

	/**
	 * SP为服务端   网关为客户端
	 * 重复的，作废不用
	 */
	@Override
	public int OnMtResp(MtResp resp, Current current)
	{
		return 0;
	}

	/**
	 * SP为服务端   网关为客户端
	 */
	@Override
	public int OnReport(MtReport sReport, Current current)
	{
		String phone = sReport.strUserNumber;
		String status = sReport.cStatus == 0 ? "发送成功" : sReport.cStatus == 1 ? "等待发送" : "发送失败";
		logger.info(String.format("【ReceiveMsgServiceServant：发送给手机号： %s  的短信 %s】", phone, status));
		return 0;
	}

	/**
	 * SP为服务端   网关为客户端。
	 * 联通SMS网关往Server发送短信息。
	 * @return int 0:成功。
	 */
	@Override
	public int OnMo(Mo mo, Current current)
	{
		logger.info("【ReceiveMsgServiceServant：上行方法OnMo()被调用！】");
		logger.info(String.format("【ReceiveMsgServiceServant：接收到%s发送的短信 】", mo.strUserNumber));
		int returnCode = ReturnCode.SUCCESS.getCode();
		String msg = getMsgByEncodeType(mo.cMsgFormat, mo.strMsgContent);
		logger.info(String.format("【ReceiveMsgServiceServant：接收到短信 编码格式：%s  内容 :%s】", mo.cMsgFormat, msg));
		if (StringUtils.isNullOrEmpty(msg))
			return -1;
		UpstreamContent content = UpstreamContent.getInstanceFromJson(msg);
		if (content == null)
			return -1;
		logger.info("【ReceiveMsgServiceServant：接收的短信请求类型：  " + content.getMsgType() + "】");
		if (verifyMsgContent(content))
		{
			switch (content.getMsgType())
			{
				case UpstreamData.MSGTYPE_STRONG_RELATE:
					returnCode = handlerBindTel(BindTelUpstreamContent.getInstanceFromJson(msg), mo.strUserNumber);
					break;
				case UpstreamData.MSGTYPE_WEAK_RELATE:
					returnCode = handlerVACPay(MsgPaymentUpstreamContent.getInstanceFromJson(msg), mo.strUserNumber);
					break;
				case UpstreamData.MSGTYPE_WEAK_GETORDERNO:
					handlerGetOrderNo(GetOrderNoUpstreamContent.getInstanceFromJson(msg), mo.strServiceNumber);
					break;
			}
			return returnCode;
		}
		return -1;
	}

	/**
	 * 验证短信内容
	 */
	private boolean verifyMsgContent(UpstreamContent content)
	{
		String key = PayUtils.getPrivateKey(content.getAppId());
		if (StringUtils.isNullOrEmpty(key))
			return false;
		if (content.getMsgType() == UpstreamData.MSGTYPE_WEAK_RELATE)
		{
			try
			{
				key = Coder.getHexStringByEncryptMD5(key);
				// MD5 16位加密
				String sign = Coder.getHexStringByEncryptMD5(NativeEncryptUtils.encryptAES(content.getData(), key)).substring(8, 24);
				if (content.getSign().equalsIgnoreCase(sign))
					return true;
			}
			catch (Exception e)
			{
				logger.error("验证短信内容失败 : " + e.getMessage(), e);
			}
			return false;
		}
		else
		{
			return PayUtils.verifySign(content.getData(), key, content.getSign()) == ReturnCode.SUCCESS.getCode();
		}
	}

	/**
	 * 弱联网方式，进行VAC支付; cpId需要通过appId来获取。
	 * @param content MsgPaymentUpstreamContent对象
	 * @param telephone 需要支付的手机号
	 */
	private int handlerVACPay(MsgPaymentUpstreamContent content, String telephone)
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		if (content == null)
			return returnCode;

		String key = PayUtils.getPrivateKey(content.getAppId());
		String cpId = PayUtils.getCpId(content.getAppId());
		SyncDeviceVo vo = PayUtils.getSyncDevice(content.getDeviceId());

		try
		{
			if (PayUtils.getSignByAESandMD5(content.getData(), key).equalsIgnoreCase(content.getSign()))
			{
				// 完成支付，保存订单信息
				PaymentOrderVo orderVo = fillOrderVoFromMsgContent(content);
				TwoTuple<Integer, ResVacBean> result = IcePaymentClient.call(content.getAmount() / 100.0, telephone);
				if ((returnCode = result.first) == ReturnCode.SUCCESS.getCode() && result.second != null)
				{
					returnCode = Integer.parseInt(result.second.resultcode);
					logger.info(String.format("【ReceiveMsgServiceServant：支付请求返回状态码：%s】", returnCode));
					orderVo.setPaymentCreatedOrderNo(result.second.seq);
					orderVo.setCpId(cpId);
					if (vo != null)
					{
						orderVo.setDeviceCode(vo.getDeviceCode());
						orderVo.setChannelId(vo.getChannelId());
						orderVo.setDeviceType(vo.getDeviceType());
					}
					if (returnCode == ReturnCode.SUCCESS.getCode())
					{
						orderVo.setState(PaymentOrderVo.PAY_STATE_SUCCESS);
						if (content.getIsReceipt().intValue() == 1)
						{ // 发送通知短信
							String msgContent = String.format("【ATET】您购买的%s，数量%s，共计%s元 已经购买成功！", content.getPayPoint(), content
									.getCounts(), content.getAmount() / 100.0);
							pool.execute(new SendMsgTask(telephone,msgContent));
						}
						// 如果请求参数中包含了notifyUrL就使用请求参数中的，如果没有则从数据库读取
						String cpNotifyUrl = StringUtils.isNullOrEmpty(orderVo.getCpNotifyUrl()) ? PayUtils
								.getNotifyUrl(orderVo.getAppId()) : orderVo.getCpNotifyUrl();
						if (!StringUtils.isNullOrEmpty(cpNotifyUrl) && StringUtils.isHttpURL(cpNotifyUrl))
						{
							NotifyContent notifyContent = NotifyContent.getInstanceByOrder(orderVo);
							NotifyEntity entity = new NotifyEntity(cpNotifyUrl, notifyContent.generateContent());
							HttpAsyncConnExecutor.submitNotifyTask(entity);
						}
					}
					else
						orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
				}
				else
				{
					logger.info("【ReceiveMsgServiceServant：支付请求发生异常】");
					orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
				}
				PayUtils.saveOrUpdateOrder(orderVo);

				// 操作失败记录日志
				if (returnCode != ReturnCode.SUCCESS.getCode())
				{
					FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_UNICOM_MSG_PAYMENT, content.getData(), content.getSign(), returnCode);
				}

				// 发送下行短信，返回支付状态
				MsgPaymentDownstreamContent downContent = new MsgPaymentDownstreamContent();
				downContent.setCode(returnCode);
				downContent.setSign(PayUtils.getSignByAESandMD5(content.getData(), key));
				logger.info(String.format("【ReceiveMsgServiceServant：发送下行短信内容：%s】", downContent.getJsonStr()));
				pool.execute(new SendMsgTask(telephone,downContent.getJsonStr()));
			}
			else
			{
				logger.error("【ReceiveMsgServiceServant：使用短信进行VAC支付操作校验不通过！】");
			}
		}
		catch (Exception e)
		{
			logger.error("【ReceiveMsgServiceServant：使用短信进行VAC支付失败！】", e);
		}
		return returnCode;
	}

	/**
	 * 强联网方式，进行绑定手机号操作。
	 * @param content BindTelUpstreamContent对象
	 * @param telephone 需要绑定的手机号
	 */
	private int handlerBindTel(BindTelUpstreamContent content, String telephone)
	{
		if (content == null || StringUtils.isNullOrEmpty(telephone))
			return ReturnCode.REQUEST_PARAM_ERROR.getCode();

		int result = ReturnCode.BIND_TEL_ERROR.getCode();
		String key = PayUtils.getPrivateKey(content.getAppId());
		try
		{
			if (PayUtils.getSignByAESandMD5(content.getData(), key).equalsIgnoreCase(content.getSign()))
				result = bindTelBo.bindTelephone(fillBindTelVo(content, telephone));
			else
				logger.error("【ReceiveMsgServiceServant：绑定手机号操作校验不通过！】");
		}
		catch (Exception e)
		{
			logger.error("【ReceiveMsgServiceServant：绑定手机号操作失败！】", e);
		}
		return result;
	}

	/**
	 * 弱联网方式，获取订单号操作，并发送下行短信到联通短信网关。
	 * @param content GetOrderNoUpstreamContent对象
	 * @param telephone 发起请求的手机号码
	 */
	private void handlerGetOrderNo(GetOrderNoUpstreamContent content, String telephone)
	{
		if (content == null || StringUtils.isNullOrEmpty(telephone))
			return;

		String key = PayUtils.getPrivateKey(content.getAppId());
		try
		{
			if (!StringUtils.isNullOrEmpty(key) && PayUtils.getSignByAESandMD5(content.getData(), key).equalsIgnoreCase(content.getSign()))
			{
				String orderNo = PaymentOrderVo.generateOrderNo();
				logger.info(String.format("【ReceiveMsgServiceServant：生成订单号 : %s】", orderNo));
				GetOrderNoDownstreamContent down = new GetOrderNoDownstreamContent();
				down.setAppId(content.getAppId());
				down.setOrderNo(orderNo);
				logger.info(String.format("【ReceiveMsgServiceServant：发送下行短信内容：%s】", down.getJsonStr()));
				Mt mt = Mt.fillMt(telephone, down.getJsonStr());
				int sendResult = SendMsgClient.send(mt);
				logger.info(String.format("【ReceiveMsgServiceServant：发送下行短信结果 : %s】", sendResult));
			}
			else
			{
				logger.error("【ReceiveMsgServiceServant：弱联网方式，获取订单号操作校验不通过！】");
			}
		}
		catch (Exception e)
		{
			logger.error("【ReceiveMsgServiceServant：弱联网方式，获取订单号操作失败！】", e);
		}
	}

	/**
	 * 使用支付短信内容中的各属性值生成订单对象
	 */
	private PaymentOrderVo fillOrderVoFromMsgContent(MsgPaymentUpstreamContent content)
	{
		PaymentOrderVo orderVo = new PaymentOrderVo();
		orderVo.setPartnerId(content.getPartnerId());
		orderVo.setOrderNo(content.getOrderNo());
		orderVo.setUserId(content.getUserId());
		orderVo.setAppId(content.getAppId());
		orderVo.setAmount(content.getAmount());
		orderVo.setCounts(content.getCounts());
		orderVo.setPayPoint(content.getPayPoint());
		orderVo.setPayType(PaymentOrderVo.PAYTYPE_UNICOM);
		orderVo.setState(PaymentOrderVo.PAY_STATE_NONPROCESS);
		orderVo.setCreateTime(new Timestamp(new Date().getTime()));
		return orderVo;
	}

	/**
	 * 生成BindTelephoneVo对象 
	 */
	private BindTelephoneVo fillBindTelVo(BindTelUpstreamContent content, String telephone)
	{
		BindTelephoneVo vo = new BindTelephoneVo();
		vo.setUserId(content.getUserId());
		vo.setAtetId(content.getAtetId());
		vo.setProductId(content.getProductId());
		vo.setDeviceCode(content.getDeviceCode());
		vo.setDeviceType(BindTelephoneVo.DEVICTTYPE_PHONE);
		vo.setTelephone(telephone);
		vo.setState(BindTelephoneVo.STATE_SUCCESS);
		vo.setSendCount(0);
		return vo;
	}

	/**
	 * 将byte[]数组根据不同的编码格式转成String 
	 */
	private String getMsgByEncodeType(int encodeType, byte[] bytes)
	{
		try
		{
			logger.info(String.format("【ReceiveMsgServiceServant：短信编码格式：%s】", encodeType));
			if (encodeType == 8) // UCS2编码
				return new String(bytes, "UnicodeBigUnmarked");
			else if (encodeType == 15) // GBK
				return new String(bytes, "GBK");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return new String(bytes);
	}
}