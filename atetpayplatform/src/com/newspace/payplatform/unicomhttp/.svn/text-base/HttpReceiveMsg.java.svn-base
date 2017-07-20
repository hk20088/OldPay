package com.newspace.payplatform.unicomhttp;

import static com.newspace.payplatform.ConstantData.HTTP_UNICOM_RECEIVE_SMS_URL;
import static com.newspace.payplatform.ConstantData.SPNUMBER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.HttpUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.unicomhttp.param.UnicomProduct;

/**
 * {@link HttpReceiveMsg.java}
 * @description 以Http，GET请求的方式调用联通上行短信方法，进行扣费操作。
 * @author huqili
 * @date 2015年4月13日
 *
 */
public class HttpReceiveMsg{
	
	
	private static final JLogger logger = LoggerUtils.getLogger(HttpReceiveMsg.class);
	
//	private static String HTTP_UNICOM_RECEIVE_SMS_URL="http://61.50.254.170:9000/wopayment";
//	private static String SPNUMBER = "800000";

	public static int MO(Double price,String telephone,String orderNo){
		BufferedReader read = null;
		try {
			/**
			 * 按照一定格式封装短信内容
			 * 
			 * 第1种：  DC价格(价格1或2位)*商品短代码(9位)+渠道短代码(6位)
			 * 示  例：  DC2*020182000900002
			 *
			 * 第2种：  DC价格(价格1或2位)*商品短代码(9位)+渠道短代码(6位)*透传内容
			 * 示  例：  DC6*020184000900000*BJLXVAC_1412290004038BLS1DU2
			 *
			 * 说明：这里因为没有需要透传的内容，所以使用第一种方案来实现
			 */
			String msgContent = UnicomProduct.getPayCommand(price) + "*" + UnicomProduct.getShortCode(price)+SPNUMBER;
			System.out.println("\r\n生成的 短信内容是："+msgContent);
			
			String url = HTTP_UNICOM_RECEIVE_SMS_URL+"?mobile="+telephone+"&mo_msg="+msgContent+"&linkid="+orderNo+"&command="+UnicomProduct.getCommandid(price);
			logger.info("\r\n短信上行_请求的URL是："+url);
			System.out.println("\r\n短信上行_请求的URL是："+url);
			
			HttpEntity entity = HttpUtils.get(url);
			read = new BufferedReader(new InputStreamReader(entity.getContent()));
			String str = read.readLine();
			logger.info("\r\n联通短信上行扣费_返回的响应信息是："+str);
			
			return Integer.parseInt(str);
			
		} catch (Exception e) {
			logger.error("\r\n上行短信_请求上行短信服务器失败！",e);
			return ReturnCode.ERROR.getCode();
		}finally{
			if(read != null){
				try {
					read.close();
				} catch (IOException e) {
					logger.error("\r\n上行短信_关闭输入流错误！",e);
				}
			}
		}
	}

}
