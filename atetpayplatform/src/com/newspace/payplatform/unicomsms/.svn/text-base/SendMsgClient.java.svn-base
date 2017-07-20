package com.newspace.payplatform.unicomsms;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import Ice.StringHolder;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.PropertiesUtils;
import com.newspace.payplatform.unicomsms.ice.Mt;
import com.newspace.payplatform.unicomsms.ice.SmMsgPrx;
import com.newspace.payplatform.unicomsms.ice.SmMsgPrxHelper;

/**
 * SendMsgClient.java 
 * 描 述:  向联通短信网关发送消息的客户端
 * 作 者:  liushuai
 * 历 史： 2014-7-19 创建
 */
public class SendMsgClient
{
	/**
	 * 发送下行短信状态， 0 ： 发送成功
	 */
	public static final int SEND_SUCCESS = 0;

	/**
	 * 发送下行短信状态，1 ： 发送失败
	 */
	public static final int SEND_FAIL = 1;

	/**
	 * 发送下行短信状态，2 ： 重新发送
	 */
	public static final int SEND_AGAIN = 2;

	/**
	 * 联通短信网关服务地址IP
	 */
	private static final String UNICOM_SMS_SERVER_IP;

	/**
	 * 联通短信网关服务地址端口
	 */
	private static final String UNICOM_SMS_SERVER_PORT;

	/**
	 * 连接联通短信网关用户名
	 */
	private static final String UNICOM_SMS_USERNAME;

	/**
	 * 连接联通短信网关密码
	 */
	private static final String UNICOM_SMS_PASSWORD;

	private static final JLogger logger = LoggerUtils.getLogger(SendMsgClient.class);

	static
	{
		Properties prop = PropertiesUtils.getProps("configuration");
		UNICOM_SMS_SERVER_IP = prop.getProperty("unicom_sms_server_ip");
		UNICOM_SMS_SERVER_PORT = prop.getProperty("unicom_sms_server_port");
		UNICOM_SMS_USERNAME = prop.getProperty("unicom_sms_username");
		UNICOM_SMS_PASSWORD = prop.getProperty("unicom_sms_password");
	}

	/**
	 * 发送下行短信。1> 验证用户名、密码;  2> 下发下行短信。
	 * @param Mt对象
	 * @return int 发送下行短信结果。0：成功，1：通用失败，其他：具体的失败原因。
	 */
	public static int send(Mt mt)
	{
		logger.info("【spServerNumber：" + mt.strSpServiceNumber + " spNumber：" + mt.strSpNumber + " serverCode：" + mt.strServiceCode
				+ " userName：" + mt.strUserNumber + "】");
		String content = "";
		try
		{
			content = new String(mt.strMsgContent, "GBK");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		logger.info("【短信内容：" + content + "\r\n内容长度：" + content.length() + "】");

		int result = SEND_FAIL;

		Ice.Communicator ic = null;
		try
		{
			ic = Ice.Util.initialize();
			Ice.ObjectPrx base = ic.stringToProxy("SmServer:default -h " + UNICOM_SMS_SERVER_IP + " -p " + UNICOM_SMS_SERVER_PORT);
			logger.info("【短信网关地址及端口号为：" + UNICOM_SMS_SERVER_IP + ":" + UNICOM_SMS_SERVER_PORT + "】");
			SmMsgPrx service = SmMsgPrxHelper.checkedCast(base);
			if (service == null)
			{
				throw new Error("【SendMsgClient：获取联通远程服务对象失败！】");
			}

			int loginResult = service.Login(UNICOM_SMS_USERNAME, UNICOM_SMS_PASSWORD);
			logger.info("【SendMsgClient：下行短信_登录接口返回码：" + loginResult + "】");
			if (loginResult == 0)
			{
				StringHolder strGWID = new StringHolder();
				strGWID.value = "";
				result = service.OnMt(mt, strGWID);
				logSendResultInfo(result);
			}
			else if (loginResult == 1)
			{
				logger.error("【SendMsgClient：发送下行短信时，登录失败，用户名密码错误！】");
			}
			else if (loginResult == 2)
			{
				logger.error("【SendMsgClient：发送下行短信时，登录失败，IP错误！】");
			}
		}
		catch (Ice.ConnectFailedException e)
		{
			logger.error("【SendMsgClient：ICE连接超时！】", e);
		}
		catch (RuntimeException e)
		{
			logger.error("【SendMsgClient：发送下行短信出错！】", e);
		}
		finally
		{
			destoryCommunicator(ic);
		}
		return result;
	}

	/**
	 * 销毁通信器对象 
	 */
	private static void destoryCommunicator(Ice.Communicator ic)
	{
		if (ic != null)
			ic.destroy();
	}

	/**
	 * 记录发送下行短信结果信息
	 */
	private static void logSendResultInfo(int result)
	{
		logger.debug("【SendMsgClient：下行短信_下发(MT)接口返回码：" + result + "】");

		switch (result)
		{
			case 0:
				logger.debug("【SendMsgClient：发送下行短信成功】");
				break;
			case 1:
				logger.error("【SendMsgClient：登录失败: IP、用户名、密码错误】");
				break;
			case 2:
				logger.error("【SendMsgClient：未登录进行消息下发！】");
				break;
			case 3:
				logger.error("【SendMsgClient：下发消息客户端IP错误！】");
				break;
			case 4:
				logger.error("【SendMsgClient: 下发消息源号码错误！】");
				break;
			case 20:
				logger.error("【SendMsgClient: 短信长度错误！】");
				break;
			case 21:
				logger.error("【SendMsgClient: SMServer拒绝服务，大网关拒绝服务！】");
				break;
			case 22:
				logger.error("【SendMsgClient: 客户端超流量！】");
				break;
			default:
				logger.error("【SendMsgClient：发送下行短信失败，未知状态！】");
		}
	}
}