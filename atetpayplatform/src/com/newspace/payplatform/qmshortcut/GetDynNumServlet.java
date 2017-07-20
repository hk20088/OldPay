package com.newspace.payplatform.qmshortcut;

import static com.newspace.payplatform.ReturnCode.CONN_OR_READ_TIMEOUT;
import static com.newspace.payplatform.faillog.FailLogVo.OPERATYPE_QM_GET_DYNNUM;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.qmshortcut.entity.MerchantInfo;
import com.newspace.payplatform.qmshortcut.param.GetDynNumReq;
import com.newspace.payplatform.qmshortcut.param.GetDynNumResp;

/**
 * GetMsgCheckCodeServlet.java 
 * 描 述:  快钱快捷支付，获取短信验证码接口  
 * 作 者:  liushuai
 * 历 史： 2014-8-5 创建
 */
@SuppressWarnings("serial")
public class GetDynNumServlet extends BaseServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		GetDynNumResp resp = new GetDynNumResp();
		String requestStr = getStrFromRequest(request);
		GetDynNumReq req = GetDynNumReq.getInstanceFromJson(requestStr);

		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());
		if (req != null && key != null && req.isLegal())
		{
			// 验证信息内容是否被修改
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());
			if (returnCode == ReturnCode.SUCCESS.getCode())
			{
				// 生成请求xml
				String xml = generateXml(req);
				MerchantInfo merchant = MerchantInfo.getInstance();
				merchant.setXml(xml);
				merchant.setUrl("/cnp/getDynNum");

				try
				{
					// 发送请求
					PostProcesser processer = new PostProcesser();
					String respXml = processer.send(merchant);

					// 解析响应XML
					HashMap<String, String> result = parseResultXml(respXml);
					returnCode = ReturnCode.ERROR.getCode();
					if(QMShortcutUtils.RESPONSECODE_TEL_NOTMATCH.equals(result.get("RESPONSECODE"))){
						returnCode = ReturnCode.QM_NOTMATCH_TEL.getCode();
					}else if (QMShortcutUtils.RESPONSECODE_SUCCESS.equals(result.get("RESPONSECODE"))){
						returnCode = ReturnCode.SUCCESS.getCode();
					}
					
					resp.setDesc(result.get("RESPONSETEXTMESSAGE"));
					if (result.get("TOKEN") != null)
						resp.setToken(result.get("TOKEN"));

				}
				catch (ConnectException e)
				{
					logger.error("连接或读取数据超时", e);
					returnCode = CONN_OR_READ_TIMEOUT.getCode();
				}
				catch (Exception e)
				{
					logger.error("手机动态鉴权失败！", e);
					returnCode = ReturnCode.ERROR.getCode();
				}
			}
		}

		// 记录失败日志
		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(OPERATYPE_QM_GET_DYNNUM, req == null ? null : req.getData(), req == null ? null : req.getSign(),
					returnCode, resp.getDesc());
			if (StringUtils.isNullOrEmpty(resp.getDesc()))
				resp.setDesc(ReturnCode.getDesc(returnCode));
		}

		// 输出响应
		resp.setCode(returnCode);
		if (StringUtils.isNullOrEmpty(key))
			resp.setSign(PayUtils.WRONG_SIGN);
		else
			resp.setSign(PayUtils.getSignByAESandMD5(resp.getData(), key));
		outputResult(response, resp.getJsonStr());
	}

	/**
	 * 生成请求的XML 
	 */
	private String generateXml(GetDynNumReq req)
	{
		try
		{
			Document document = DocumentHelper.parseText(String.format(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"%s\"></MasMessage>", QMShortcutUtils.XMLNS));
			Element root = document.getRootElement();

			root.addElement("version").addText("1.0");
			Element getDynNumContent = root.addElement("GetDynNumContent");

			getDynNumContent.addElement("merchantId").addText(QMShortcutUtils.MERCHANTINFO_ID);
			getDynNumContent.addElement("customerId").addText(String.valueOf(req.getUserId()));
			getDynNumContent.addElement("externalRefNumber").addText(req.getOrderNo());
			getDynNumContent.addElement("amount").addText(String.valueOf(req.getAmount() / 100.0));
			getDynNumContent.addElement("pan").addText(req.getCardNo());
			getDynNumContent.addElement("phoneNO").addText(req.getPhoneNo());
			if (!StringUtils.isNullOrEmpty(req.getExpiredDate()))
				getDynNumContent.addElement("expiredDate").addText(req.getExpiredDate());
			if (!StringUtils.isNullOrEmpty(req.getCvv()))
				getDynNumContent.addElement("cvv2").addText(req.getCvv());
			getDynNumContent.addElement("cardHolderName").addText(convertNull2Empty(req.getHolderName()));
			getDynNumContent.addElement("idType").addText(convertNull2Empty(String.valueOf(req.getIdType()))); // 证件类型默认为0
			getDynNumContent.addElement("cardHolderId").addText(convertNull2Empty(req.getHolderId()));

			String xml = document.asXML();
			logger.info(String.format("\r\n【GetDynNumServlet 生成的请求xml：\r\n%s】", xml));
			return xml;
		}
		catch (Exception e)
		{
			throw new RuntimeException("【generateXml()生成XML错误！】", e);
		}
	}

	/**
	 * 解析响应xml 
	 */
	private HashMap<String, String> parseResultXml(String xml)
	{
		logger.info(String.format("\r\n【GetDynNumServlet 接收的响应xml：\r\n%s】", xml));
		if (StringUtils.isNullOrEmpty(xml))
			throw new RuntimeException("接收到的响应xml为空");
		final HashMap<String, String> result = new HashMap<String, String>();
		try
		{
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();

			Element getDynNumContent = root.element("GetDynNumContent");
			if (getDynNumContent != null)
			{
				getDynNumContent.accept(new VisitorSupport()
				{
					public void visit(Element node)
					{
						result.put(node.getName().toUpperCase(), node.getTextTrim());
					}
				});
			}

			Element errorContent = root.element("ErrorMsgContent");
			if (errorContent != null)
			{
				errorContent.accept(new VisitorSupport()
				{
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
	 * 将null转换成空字符串 
	 */
	private String convertNull2Empty(String str)
	{
		return str == null ? "" : str;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}