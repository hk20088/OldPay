package com.newspace.payplatform.qmshortcut;

import static com.newspace.payplatform.ReturnCode.CONN_OR_READ_TIMEOUT;
import static com.newspace.payplatform.faillog.FailLogVo.OPERATYPE_QM_SHORTCUT_FIRST;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.newspace.common.utils.DateUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paynotify.HttpAsyncConnExecutor;
import com.newspace.payplatform.paynotify.NotifyContent;
import com.newspace.payplatform.paynotify.NotifyEntity;
import com.newspace.payplatform.qmshortcut.entity.MerchantInfo;
import com.newspace.payplatform.qmshortcut.param.FirstQuickPaymentReq;
import com.newspace.payplatform.qmshortcut.param.FirstQuickPaymentResp;
import com.newspace.payplatform.warnnotify.CheckAccountTask;

/**
 * FirstQuickPaymentServlet.java 
 * 描 述:  快钱第一次快捷支付接口
 * 作 者:  liushuai
 * 历 史： 2014-8-6 创建
 */
@SuppressWarnings("serial")
public class FirstQuickPaymentServlet extends BaseServlet
{
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		String requestStr = getStrFromRequest(request);
		FirstQuickPaymentReq req = FirstQuickPaymentReq.getInstanceFromJson(requestStr);
		FirstQuickPaymentResp resp = new FirstQuickPaymentResp();
		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());
		PaymentOrderVo orderVo = null;
		if (req != null && key != null && req.isLegal())
		{
			//根据订单号获取订单对象,并将请求参数填充到对象中,然后更新记录。
			orderVo = PayUtils.queryByOrderNo(req.getOrderNo(),Boolean.TRUE);
			if(orderVo == null){
				returnCode = ReturnCode.ORDERNO_OBJECT_FAIL.getCode();
			}else{
				orderVo = generateOrderObj(orderVo,req);
				returnCode = PayUtils.verifyAndRecordOrder(req.getData(), req.getSign(), orderVo);
			}
			
			if (returnCode == ReturnCode.SUCCESS.getCode())
			{
				try
				{
					// 发送请求，进行快捷支付
					String xml = generateXml(req);
					MerchantInfo merchant = MerchantInfo.getInstance();
					merchant.setXml(xml);
					merchant.setUrl("/cnp/purchase");
					PostProcesser processer = new PostProcesser();
					String respXml = processer.send(merchant);

					// 解析响应xml
					HashMap<String, String> result = parseResultXml(respXml);
					returnCode = ReturnCode.ERROR.getCode();
					orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
					resp.setDesc(result.get("RESPONSETEXTMESSAGE"));
					resp.setShortCardNo(result.get("STORABLECARDNO"));
					if (QMShortcutUtils.RESPONSECODE_SUCCESS.equals(result.get("RESPONSECODE")))
					{
						//支付成功后，检查此订单是否异常，如果异常则下发预警短信。
						pool.execute(new CheckAccountTask(orderVo));
						
						returnCode = ReturnCode.SUCCESS.getCode();
						orderVo.setState(PaymentOrderVo.PAY_STATE_SUCCESS);
					}
				}
				catch (ConnectException e)
				{
					logger.error("连接或读取数据超时", e);
					returnCode = CONN_OR_READ_TIMEOUT.getCode();
				}
				catch (Exception e)
				{
					orderVo.setState(PaymentOrderVo.PAY_STATE_FAIL);
					returnCode = ReturnCode.ERROR.getCode();
					logger.error("银行卡首次快捷支付失败！", e);
				}
				orderVo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				// 更新订单支付状态
				PayUtils.saveOrUpdateOrder(orderVo);
			}
		}

		// 记录失败日志
		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(OPERATYPE_QM_SHORTCUT_FIRST, req == null ? null : req.getData(), req == null ? null : req.getSign(),
					returnCode, resp.getDesc());
			// 如果快钱方面没有返回ResponseTextMsg，就使用ReturnCode的描述信息
			if (StringUtils.isNullOrEmpty(resp.getDesc()))
				resp.setDesc(ReturnCode.getDesc(returnCode));
		}
		else
		{
			// 如果请求参数中包含了notifyUrL就使用请求参数中的，如果没有则从数据库读取
			String cpNotifyUrl = StringUtils.isNullOrEmpty(orderVo.getCpNotifyUrl()) ? PayUtils.getNotifyUrl(orderVo.getAppId()) : orderVo
					.getCpNotifyUrl();
			if (!StringUtils.isNullOrEmpty(cpNotifyUrl) && StringUtils.isHttpURL(cpNotifyUrl))
			{
				NotifyContent content = NotifyContent.getInstanceByOrder(orderVo);
				NotifyEntity entity = new NotifyEntity(cpNotifyUrl, content.generateContent());
				HttpAsyncConnExecutor.submitNotifyTask(entity);
			}
		}
		// 输出响应
		resp.setCode(returnCode);
		resp.setOrderNo(orderVo == null ? null : orderVo.getOrderNo());
		if (StringUtils.isNullOrEmpty(key))
			resp.setSign(PayUtils.WRONG_SIGN);
		else
			resp.setSign(PayUtils.getSignByAESandMD5(resp.getData(), key));
		
		logger.info(String.format("快钱快捷支付首次支付_响应结果：%s，orderNo：%s，appId：%s",resp.getDesc(),req.getOrderNo(),req.getAppId() ));
		outputResult(response, resp.getJsonStr());
	}

	/**
	 * 生成请求xml 
	 */
	private String generateXml(FirstQuickPaymentReq req)
	{
		try
		{
			Document document = DocumentHelper.parseText(String.format(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"%s\"></MasMessage>", QMShortcutUtils.XMLNS));
			Element root = document.getRootElement();

			root.addElement("version").addText("1.0");

			Element txnMsgContent = root.addElement("TxnMsgContent");
			txnMsgContent.addElement("interactiveStatus").addText("TR1");
			txnMsgContent.addElement("txnType").addText(QMShortcutUtils.TXN_TYPE_PUR);
			txnMsgContent.addElement("merchantId").addText(QMShortcutUtils.MERCHANTINFO_ID);
			txnMsgContent.addElement("terminalId").addText(QMShortcutUtils.MERCHANTINFO_TERMINALID);
			txnMsgContent.addElement("entryTime").addText(getTimeString());
			txnMsgContent.addElement("cardNo").addText(req.getCardNo());

			if (!StringUtils.isNullOrEmpty(req.getExpiredDate()))
				txnMsgContent.addElement("expiredDate").addText(req.getExpiredDate());
			if (!StringUtils.isNullOrEmpty(req.getCvv()))
				txnMsgContent.addElement("cvv2").addText(req.getCvv());
			txnMsgContent.addElement("amount").addText(String.valueOf(req.getAmount() / 100.0));
			txnMsgContent.addElement("externalRefNumber").addText(req.getOrderNo());
			txnMsgContent.addElement("customerId").addText(String.valueOf(req.getUserId()));
			txnMsgContent.addElement("cardHolderName").addText(convertNull2Empty(req.getHolderName()));
			txnMsgContent.addElement("idType").addText(convertNull2Empty(String.valueOf(req.getIdType())));
			txnMsgContent.addElement("cardHolderId").addText(convertNull2Empty(req.getHolderId()));
			txnMsgContent.addElement("spFlag").addText(QMShortcutUtils.SPFLAG_QUICKPAY);

			Element extMap = txnMsgContent.addElement("extMap");

			Element extDate1 = extMap.addElement("extDate");
			extDate1.addElement("key").addText("phone");
			extDate1.addElement("value").addText(req.getPhoneNo());

			Element extDate2 = extMap.addElement("extDate");
			extDate2.addElement("key").addText("validCode");
			extDate2.addElement("value").addText(req.getCheckCode());

			Element extDate3 = extMap.addElement("extDate");
			extDate3.addElement("key").addText("savePciFlag");
			extDate3.addElement("value").addText(QMShortcutUtils.SAVE_PCI_FLAG);

			Element extDate4 = extMap.addElement("extDate");
			extDate4.addElement("key").addText("token");
			extDate4.addElement("value").addText(req.getToken());

			Element extDate5 = extMap.addElement("extDate");
			extDate5.addElement("key").addText("payBatch");
			extDate5.addElement("value").addText(QMShortcutUtils.PAYBATCH_FIRST);

			String xml = document.asXML();
			logger.info(String.format("\r\n【FirstQuickPaymentServlet 生成的请求xml：\r\n%s】", xml));
			return xml;
		}
		catch (Exception e)
		{
			throw new RuntimeException("【generateXml()生成xml出错】", e);
		}
	}

	/**
	 * 解析响应xml 
	 */
	private HashMap<String, String> parseResultXml(String xml)
	{
		logger.info(String.format("\r\n【FirstQuickPaymentServlet 接收的响应xml：\r\n%s】", xml));
		if (StringUtils.isNullOrEmpty(xml))
			throw new RuntimeException("接收到的响应xml为空");
		final HashMap<String, String> result = new HashMap<String, String>();
		try
		{
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();

			Element txtMsgContent = root.element("TxnMsgContent");
			if (txtMsgContent != null)
			{
				txtMsgContent.accept(new VisitorSupport()
				{
					@Override
					public void visit(Element node)
					{
						result.put(node.getName().toUpperCase(), node.getTextTrim());
					}
				});
			}

			Element errorMsgContent = root.element("ErrorMsgContent");
			if (errorMsgContent != null)
			{
				errorMsgContent.accept(new VisitorSupport()
				{
					@Override
					public void visit(Element node)
					{
						result.put(node.getName().toUpperCase(), node.getTextTrim());
					}
				});
			}
			return result;
		}
		catch (Exception e)
		{
			throw new RuntimeException("解析响应XML出错", e);
		}
	}

	/**
	 * 填充订单对象 
	 */
	private PaymentOrderVo generateOrderObj(PaymentOrderVo vo , FirstQuickPaymentReq req)
	{
		if(!vo.getAtetId().equals(req.getAtetId()))
			vo.setIsScanPay(PaymentOrderVo.IS_SCAN_PAY);
		vo.setPartnerId(req.getPartnerId());
		vo.setPackageName(req.getPackageName());
		vo.setDeviceCode(req.getDeviceCode());
		vo.setPayPoint(req.getPayPoint());
		vo.setGameName(req.getGameName());
		vo.setProductId(req.getProductId());
		vo.setAmount(req.getAmount());
		vo.setCpId(req.getCpId());
		vo.setUserId(req.getUserId());
		vo.setCounts(req.getCounts());
		vo.setAmountType(PaymentOrderVo.AMOUNT_TYPE_RMB);
		vo.setPayType(PaymentOrderVo.PAYTYPE_QM_SHORTCUT);
		vo.setState(PaymentOrderVo.PAY_STATE_NONPROCESS);
		
		vo.setCpOrderNo(req.getCpOrderNo());
		vo.setCpNotifyUrl(req.getCpNotifyUrl());
		vo.setCpPrivateInfo(req.getCpPrivateInfo());
		return vo;
	}

	/**
	 * 将null转换成空字符串 
	 */
	private String convertNull2Empty(String str)
	{
		return str == null ? "" : str;
	}

	/**
	 * 获取当前时间字符串 
	 */
	private String getTimeString()
	{
		return DateUtils.DATETIMEFORMAT_NOGAP.get().format(new Date());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}