package com.newspace.payplatform.qmshortcut;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.qmshortcut.observer.TR3Subject;

/**
 * ReceiveTR3AndSendTR4Servlet.java 
 * 描 述:  接收TR3请求，并发送TR4响应。
 * 作 者:  liushuai
 * 历 史： 2014-8-7 创建
 */
@SuppressWarnings("serial")
public class ReceiveTR3AndSendTR4Servlet extends BaseServlet
{
	private static final TR3Subject subject = new TR3Subject();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		String reqXml = getStrFromRequest(request);

		logger.info("\r\n【ReceiveTR3AndSendTR4Servlet 接收到TR3 XML：\r\n" + reqXml + "】");
		if (!CertSignUtils.veriSignForXml(reqXml, QMShortcutUtils.PUBLICKEY_FILE_PATH))
		{
			throw new RuntimeException("【校验XML，信息不一致，将忽略此条消息！】");
		}
		else
		{
			HashMap<String, String> map = parseXml(reqXml);
			String responseCode = map.get("RESPONSECODE");
			String orderNo = map.get("EXTERNALREFNUMBER");
			logger.info(String.format("【TR3消息中，ResponseCode: %s, OrderNo: %s】", responseCode, orderNo));
			if (!StringUtils.isExistNullOrEmpty(responseCode, orderNo))
				subject.handlerReceivedMsg(orderNo, responseCode);
			String tr4Xml = generateTR4Xml(map);
			if (!StringUtils.isNullOrEmpty(tr4Xml))
				response.getWriter().write(tr4Xml);
		}
	}

	/**
	 * 添加观察者
	 */
	public static void addObserver(Observer observer)
	{
		subject.addObserver(observer);
	}

	/**
	 * 删除观察者 
	 */
	public static void removeObserver(Observer observer)
	{
		subject.deleteObserver(observer);
	}

	/**
	 * 解析XML 
	 */
	private HashMap<String, String> parseXml(String xml)
	{
		final HashMap<String, String> map = new HashMap<String, String>();
		try
		{
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();

			Element txnMsgContent = root.element("TxnMsgContent");
			if (txnMsgContent != null)
			{
				txnMsgContent.accept(new VisitorSupport()
				{
					public void visit(Element node)
					{
						map.put(node.getName().toUpperCase(), node.getTextTrim());
					}
				});
			}
			return map;
		}
		catch (Exception e)
		{
			throw new RuntimeException("解析XML出错", e);
		}
	}

	/**
	 * 生成TR4Xml
	 */
	private String generateTR4Xml(Map<String, String> map)
	{
		try
		{
			Document document = DocumentHelper.parseText(String.format(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"%s\"></MasMessage>", QMShortcutUtils.XMLNS));
			Element root = document.getRootElement();

			root.addElement("version").addText("1.0");

			Element txnMsgContent = root.addElement("TxnMsgContent");
			txnMsgContent.addElement("txnType").addText(convertNull2Empty(map.get("TXNTYPE")));
			txnMsgContent.addElement("interactiveStatus").addText("TR4");
			txnMsgContent.addElement("merchantId").addText(convertNull2Empty(map.get("MERCHANTID")));
			txnMsgContent.addElement("terminalId").addText(convertNull2Empty(map.get("TERMINALID")));
			txnMsgContent.addElement("refNumber").addText(convertNull2Empty(map.get("REFNUMBER")));

			String xml = document.asXML();
			logger.info(String.format("\r\n【ReceiveTR3AndSendTR4Servlet 生成的TR4Xml：\r\n%s】", xml));
			return xml;
		}
		catch (Exception e)
		{
			throw new RuntimeException("生成TR4 XML 出错", e);
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