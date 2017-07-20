package com.newspace.payplatform.unicompay;

import java.util.Properties;

import com.newspace.common.coder.Coder;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.PropertiesUtils;
import com.newspace.common.utils.TwoTuple;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.unicompay.ice.GameWoVacReqPrx;
import com.newspace.payplatform.unicompay.ice.GameWoVacReqPrxHelper;
import com.newspace.payplatform.unicompay.ice.ReqVacBean;
import com.newspace.payplatform.unicompay.ice.ResVacBean;

/**
 * IcePaymentClient.java 
 * 描 述:  ICE支付客户端类，用来与联通VAC网关进行通信，完成支付。
 * 作 者:  liushuai
 * 历 史： 2014-7-10 创建
 */
public class IcePaymentClient
{
	/**
	 * VAC支付网关地址
	 */
	private static final String UNICOM_SERVER_IP;

	/**
	 * VAC支付网关端口
	 */
	private static final String UNICOM_SERVER_PORT;

	private static final JLogger logger = LoggerUtils.getLogger(IcePaymentClient.class);

	static
	{
		Properties prop = PropertiesUtils.getProps("configuration");
		UNICOM_SERVER_IP = prop.getProperty("unicom_pay_server_ip");
		UNICOM_SERVER_PORT = prop.getProperty("unicom_pay_server_port");
	}

	/**
	 * 调用联通ICE接口，完成支付
	 * @param price 计费产品价格
	 * @param telephone 手机号码
	 * @return 二元组,first : 状态码 ; second : 请求支付结果对象
	 */
	public static TwoTuple<Integer, ResVacBean> call(Double price, String telephone)
	{
		Ice.Communicator ic = null;
		ResVacBean respBean = null;
		int returnCode = ReturnCode.SUCCESS.getCode();
		try
		{
			// 初始化通信器
			ic = Ice.Util.initialize();
			//传入远程服务单元的名称、网络协议、IP及端口，获取Printer的远程代理，这里使用的stringToProxy方式  
			Ice.ObjectPrx base = ic.stringToProxy("SimpleGameWoVacReq:default -h " + UNICOM_SERVER_IP + " -p " + UNICOM_SERVER_PORT);
			
			logger.info(String.format("【联通VAC计费IP地址：%s:%s】", UNICOM_SERVER_IP,UNICOM_SERVER_PORT));
			
			//通过checkedCast向下转换，获取Printer接口的远程，并同时检测根据传入的名称获取的服务单元是否Printer的代理接口，如果不是则返回null对象
			GameWoVacReqPrx vacReq = GameWoVacReqPrxHelper.checkedCast(base);
			if (vacReq == null)
				throw new Error("Invalid proxy ! GameWoVacReqPrx 对象为null!");
			ReqVacBean reqBean = generateVacBean(UnicomProduct.getProductByPrice(price), telephone);
			if (reqBean != null)
				respBean = vacReq.getVacReq(reqBean);
			else
				returnCode = ReturnCode.UNICOM_VAC_MATCHED_PRODUCT_NOEXIST.getCode();
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			returnCode = ReturnCode.UNICOM_VAC_UNKOWN_ERROR.getCode();
		}
		finally
		{
			if (ic != null)
				ic.destroy();
		}
		return new TwoTuple<Integer, ResVacBean>(returnCode, respBean);
	}

	/**
	 * 生成联通Vac请求实体对象 
	 * @param product UnicomProduct联通计费产品对象
	 * @param telephone 手机号码
	 */
	private static ReqVacBean generateVacBean(UnicomProduct product, String telephone)
	{
		if (product == null)
			return null;

		ReqVacBean vbean = new ReqVacBean();
		vbean.sim = telephone;
		vbean.producttype = "0"; // 点播-按次
		vbean.pseudocode = Coder.getHexStringByEncryptMD5(vbean.sim);// 手机号码伪码，32位任意字母和数字，这里采用MD5生成
		vbean.seq = "ATET-" + System.currentTimeMillis(); // 交易序列号
		vbean.signtype = "1"; // 签名类型，SHA1withRSA
		vbean.productid = product.getProductid();
		vbean.payproductid = product.getPayproductid();
		vbean.price = String.valueOf(product.getPrice());
		vbean.apid = "";
		vbean.applid = "";
		vbean.productname = product.getProductName();
		return vbean;
	}
}
