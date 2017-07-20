package com.newspace.payplatform.qmshortcut;

import static com.newspace.payplatform.ReturnCode.CONN_OR_READ_TIMEOUT;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.PayUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseServlet;
import com.newspace.payplatform.faillog.FailLogUtils;
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.qmshortcut.entity.BankCardInfo;
import com.newspace.payplatform.qmshortcut.entity.MerchantInfo;
import com.newspace.payplatform.qmshortcut.param.QueryBoundCardReq;
import com.newspace.payplatform.qmshortcut.param.QueryBoundCardResp;

/**
 * QueryBoundCardInfoServlet.java 
 * 描 述:  查询已绑定的银行卡信息  
 * 作 者:  liushuai
 * 历 史： 2014-8-6 创建
 */
@SuppressWarnings("serial")
public class QueryBoundCardInfoServlet extends BaseServlet
{
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		String requestStr = getStrFromRequest(request);
		QueryBoundCardReq req = QueryBoundCardReq.getInstanceFromJson(requestStr);
		QueryBoundCardResp resp = new QueryBoundCardResp();
		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());

		if (req != null && key != null && req.isLegal())
		{
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());

			if (returnCode == ReturnCode.SUCCESS.getCode())
			{
				try
				{
					// 生成请求XML
					String xml = generateXml(req);
					MerchantInfo merchant = MerchantInfo.getInstance();
					merchant.setXml(xml);
					merchant.setUrl("/cnp/pci_query");

					PostProcesser processer = new PostProcesser();
					String respXml = processer.send(merchant);

					// 解析响应xml
					HashMap<String, Object> result = parseResultXml(respXml);
					if (!QMShortcutUtils.RESPONSECODE_SUCCESS.equals(result.get("RESPONSECODE")))
						returnCode = ReturnCode.ERROR.getCode();
					resp.setCards((ArrayList<BankCardInfo>) result.get("PCIINFOS"));
					resp.setDesc((String) result.get("RESPONSETEXTMESSAGE"));
				}
				catch (ConnectException e)
				{
					logger.error("连接或读取数据超时", e);
					returnCode = CONN_OR_READ_TIMEOUT.getCode();
				}
				catch (Exception e)
				{
					logger.error("查询已绑定的银行卡信息失败", e);
					returnCode = ReturnCode.ERROR.getCode();
				}
			}

		}

		// 记录失败日志
		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_QM_QUERYBOUNDCARD, req == null ? null : req.getData(), req == null ? null : req
					.getSign(), returnCode, resp.getDesc());
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
	 * 生成xml 
	 */
	private String generateXml(QueryBoundCardReq req)
	{
		try
		{
			Document document = DocumentHelper.parseText(String.format(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"%s\"></MasMessage>", QMShortcutUtils.XMLNS));
			Element root = document.getRootElement();
			root.addElement("version").addText("1.0");
			Element pciQueryContent = root.addElement("PciQueryContent");
			pciQueryContent.addElement("merchantId").addText(QMShortcutUtils.MERCHANTINFO_ID);
			pciQueryContent.addElement("customerId").addText(String.valueOf(req.getUserId()));
			pciQueryContent.addElement("cardType").addText(req.getCardType() == null ? "0001" : req.getCardType());

			String xml = document.asXML();
			logger.info(String.format("\r\n【QueryBoundCardInfoServlet 生成的请求xml：\r\n%s】", xml));
			return xml;
		}
		catch (DocumentException e)
		{
			throw new RuntimeException("【generateXml()生成xml出错】", e);
		}
	}

	/**
	 * 解析xml 
	 */
	private HashMap<String, Object> parseResultXml(String xml)
	{
		logger.info(String.format("\r\n【QueryBoundCardInfoServlet 接收的响应xml：\r\n%s】", xml));
		if (StringUtils.isNullOrEmpty(xml))
			throw new RuntimeException("接收到的响应xml为空");
		final HashMap<String, Object> result = new HashMap<String, Object>();

		try
		{
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();

			Element txtMsgContent = root.element("PciQueryContent");
			if (txtMsgContent != null)
			{
				final List<BankCardInfo> list = new ArrayList<BankCardInfo>();
				result.put("PCIINFOS", list);
				txtMsgContent.accept(new VisitorSupport()
				{
					@Override
					public void visit(Element node)
					{
						String name = node.getName();
						if (name.equalsIgnoreCase("responseCode"))
							result.put(name.toUpperCase(), node.getTextTrim());
						else if (name.equalsIgnoreCase("pciInfo"))
						{
							BankCardInfo info = new BankCardInfo();
							info.setBankId(node.elementText("bankId"));
							info.setShortPhoneNo(node.elementText("shortPhoneNo"));
							info.setStorablePan(node.elementText("storablePan"));
							info.setPhoneNo(node.elementText("phoneNO"));
							list.add(info);
						}
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}