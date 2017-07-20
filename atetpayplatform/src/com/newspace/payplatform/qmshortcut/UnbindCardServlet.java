package com.newspace.payplatform.qmshortcut;

import static com.newspace.payplatform.ReturnCode.CONN_OR_READ_TIMEOUT;

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
import com.newspace.payplatform.qmshortcut.param.UnbindCardReq;
import com.newspace.payplatform.qmshortcut.param.UnbindCardResp;

/**
 * UnbindCardServlet.java 
 * 描 述:  解绑银行卡信息  
 * 作 者:  liushuai
 * 历 史： 2014-8-12 创建
 */
@SuppressWarnings("serial")
public class UnbindCardServlet extends BaseServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		String requestStr = getStrFromRequest(request);
		UnbindCardReq req = UnbindCardReq.getInstanceFromJson(requestStr);
		UnbindCardResp resp = new UnbindCardResp();
		String key = req == null ? null : PayUtils.getPrivateKey(req.getAppId());

		if (req != null && key != null && req.isLegal())
		{
			returnCode = PayUtils.verifySign(req.getData(), key, req.getSign());

			if (returnCode == ReturnCode.SUCCESS.getCode())
			{
				try
				{
					// 生成请求xml
					String xml = generateXml(req);
					MerchantInfo merchant = MerchantInfo.getInstance();
					merchant.setXml(xml);
					merchant.setUrl("/cnp/pci_del");
					PostProcesser processer = new PostProcesser();
					String respXml = processer.send(merchant);

					// 解析响应xml
					HashMap<String, String> result = parseResultXml(respXml);
					if (!QMShortcutUtils.RESPONSECODE_SUCCESS.equals(result.get("RESPONSECODE")))
						returnCode = ReturnCode.ERROR.getCode();
					resp.setDesc(result.get("RESPONSETEXTMESSAGE"));
				}
				catch (ConnectException e)
				{
					logger.error("连接或读取数据超时", e);
					returnCode = CONN_OR_READ_TIMEOUT.getCode();
				}
				catch (Exception e)
				{
					logger.error("解绑银行卡操作失败", e);
					returnCode = ReturnCode.ERROR.getCode();
				}
			}
		}

		// 记录失败日志
		if (returnCode != ReturnCode.SUCCESS.getCode())
		{
			FailLogUtils.insertFaillog(FailLogVo.OPERATYPE_QM_UNBINDCARD, req == null ? null : req.getData(), req == null ? null : req
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
	private String generateXml(UnbindCardReq req)
	{
		try
		{
			Document document = DocumentHelper.parseText(String.format(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><MasMessage xmlns=\"%s\"></MasMessage>", QMShortcutUtils.XMLNS));
			Element root = document.getRootElement();
			root.addElement("version").addText("1.0");
			Element delContent = root.addElement("PciDeleteContent");

			delContent.addElement("merchantId").addText(QMShortcutUtils.MERCHANTINFO_ID);
			delContent.addElement("customerId").addText(String.valueOf(req.getUserId()));
			delContent.addElement("storablePan").addText(req.getShortCardNo());
			delContent.addElement("bankId").addText(req.getBankId());
			String xml = document.asXML();
			logger.info(String.format("\r\n【UnbindCardServlet 生成的请求xml：\r\n%s】", xml));
			return xml;
		}
		catch (Exception e)
		{
			throw new RuntimeException("【generateXml()生成xml出错】", e);
		}
	}

	/**
	 * 解析xml 
	 */
	private HashMap<String, String> parseResultXml(String xml)
	{
		logger.info(String.format("\r\n【UnbindCardServlet 接收的响应xml：\r\n%s】", xml));
		if (StringUtils.isNullOrEmpty(xml))
			throw new RuntimeException("接收到的响应xml为空");

		final HashMap<String, String> result = new HashMap<String, String>();
		try
		{
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();

			Element txtMsgContent = root.element("PciDeleteContent");
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

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}