package com.newspace.payplatform.qmshortcut;

import static com.newspace.payplatform.ReturnCode.CONN_OR_READ_TIMEOUT;
import static com.newspace.payplatform.ReturnCode.ERROR;
import static com.newspace.payplatform.ReturnCode.QM_NOTEXIST_CARDINFO;
import static com.newspace.payplatform.ReturnCode.QM_NOT_SUPPORT_BANKCARD;
import static com.newspace.payplatform.ReturnCode.REQUEST_PARAM_ERROR;
import static com.newspace.payplatform.ReturnCode.SUCCESS;

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
import com.newspace.payplatform.faillog.FailLogVo;
import com.newspace.payplatform.qmshortcut.entity.MerchantInfo;
import com.newspace.payplatform.qmshortcut.param.QueryCardIfnoResp;
import com.newspace.payplatform.qmshortcut.param.QueryCardInfoReq;

/**
 * QueryCardInfo.java 
 * 描 述:  根据卡号查询银行卡信息
 * 作 者:  liushuai
 * 历 史： 2014-8-12 创建
 */
@SuppressWarnings("serial")
public class QueryCardInfoServlet extends BaseServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = REQUEST_PARAM_ERROR.getCode();
		String requestStr = getStrFromRequest(request);
		QueryCardInfoReq req = QueryCardInfoReq.getInstanceFromJson(requestStr);
		QueryCardIfnoResp resp = new QueryCardIfnoResp();
		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());

		if (req != null && key != null && req.isLegal())
		{
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());

			if (returnCode == SUCCESS.getCode())
			{
				try
				{
					// 生成请求xml
					String xml = generateXml(req);
					MerchantInfo merchant = MerchantInfo.getInstance();
					merchant.setXml(xml);
					merchant.setUrl("/cnp/query_cardinfo");

					PostProcesser processer = new PostProcesser();
					String respXml = processer.send(merchant);

					// 解析响应xml
					HashMap<String, String> result = parseResultXml(respXml);

					if (result.get("ISEXIST") != null)
					{
						if (QMShortcutUtils.isSupport(result.get("ISSUER"), result.get("CARDTYPE")))
						{
							resp.setCardType(result.get("CARDTYPE"));
							resp.setCardOrg(result.get("CARDORG"));
							resp.setIssuer(result.get("ISSUER"));
							resp.setValidateType(result.get("VALIDATETYPE"));
							resp.setValidFlag(result.get("VALIDFLAG"));
						}
						else
						{
							returnCode = QM_NOT_SUPPORT_BANKCARD.getCode();
						}
					}
					else
					{
						returnCode = QM_NOTEXIST_CARDINFO.getCode();
					}
				}
				catch (ConnectException e)
				{
					logger.error("连接或读取数据超时", e);
					returnCode = CONN_OR_READ_TIMEOUT.getCode();
				}
				catch (Exception e)
				{
					logger.error("根据卡号查询银行卡信息操作失败: " + e.getMessage(), e);
					returnCode = ERROR.getCode();
				}
			}
		}

		// 记录失败日志
		if (returnCode != SUCCESS.getCode() && returnCode != QM_NOTEXIST_CARDINFO.getCode()
				&& returnCode != QM_NOT_SUPPORT_BANKCARD.getCode())
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_QM_QUERYCARDINFO, req == null ? null : req.getData(), req == null ? null : req
					.getSign(), returnCode);

		// 输出响应
		resp.setCode(returnCode);
		if (returnCode != SUCCESS.getCode())
			resp.setDesc(ReturnCode.getDesc(returnCode));
		if (StringUtils.isNullOrEmpty(key))
			resp.setSign(PayUtils.WRONG_SIGN);
		else
			resp.setSign(PayUtils.getSignByAESandMD5(resp.getData(), key));
		outputResult(response, resp.getJsonStr());
	}

	/**
	 * 生成xml 
	 */
	private String generateXml(QueryCardInfoReq req)
	{
		try
		{
			Document document = DocumentHelper.parseText(String.format(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"%s\"></MasMessage>", QMShortcutUtils.XMLNS));
			Element root = document.getRootElement();
			root.addElement("version").addText("1.0");
			Element cardContent = root.addElement("QryCardContent");

			cardContent.addElement("txnType").addText("PUR");
			cardContent.addElement("cardNo").addText(req.getCardNo());

			String xml = document.asXML();
			logger.info(String.format("\r\n【QueryCardInfoServlet 生成的请求xml：\r\n%s】", xml));
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
		logger.info(String.format("\r\n【QueryCardInfoServlet 接收的响应xml：\r\n%s】", xml));
		if (StringUtils.isNullOrEmpty(xml))
			throw new RuntimeException("接收到的响应xml为空");
		final HashMap<String, String> result = new HashMap<String, String>();

		try
		{
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();

			Element txtMsgContent = root.element("CardInfoContent");
			if (txtMsgContent != null)
			{
				result.put("ISEXIST", "true");// 是否存在卡号对应的信息
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}