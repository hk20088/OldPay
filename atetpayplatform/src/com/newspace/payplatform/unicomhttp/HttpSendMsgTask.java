package com.newspace.payplatform.unicomhttp;

import static com.newspace.payplatform.ConstantData.ACTION;
import static com.newspace.payplatform.ConstantData.CMD_ID;
import static com.newspace.payplatform.ConstantData.CORP_ID;
import static com.newspace.payplatform.ConstantData.HTTP_UNICOM_SEND_SMS_URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.HttpUtils;

/**
 * {@link HttpSendMsgTask.java}
 * @description: 以Http，GET请求的方式调用下行短信接口，给用户发送短信。
 * @author huqili
 * @date 2015年3月28日
 * @since JDK1.6
 *
 */
public class HttpSendMsgTask implements Runnable{
	
	//-------------------------------------start---------------------------------
//	private static final String HTTP_UNICOM_SMS_SERVER_URL = "http://61.50.254.170:9000/smsatetHandler";
//	private static final String CORP_ID = "UNIVAC"; //提交短信网关的公司代号，例如UNIVAC
//	private static final String CMD_ID = "10655561000"; //下行特服号码
//	private static final String ACTION = "ATET";
	//-------------------------------------end---------------------------------

	private String telephone;   //用户手机号
	
	private String msgContent;  //短信内容
	
	private String linkId;  //短信网关产生的唯一标识ID
	
	private String time; //timstamp
	
	private static final JLogger logger = LoggerUtils.getLogger(HttpSendMsgTask.class);
	
	private static final String REQUEST_URL = HTTP_UNICOM_SEND_SMS_URL+"?Corp_id="+CORP_ID+"&Cmd_id="+CMD_ID+"&Action="+ACTION;
	
	public HttpSendMsgTask(String telephone,String msgContent){
		this.telephone = telephone;
		this.msgContent = msgContent;
		this.linkId = getLinkID();
		try {
			this.time = URLEncoder.encode(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("时间转换_不支持的编码格式");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		BufferedReader reader = null;
		try {
			String url = REQUEST_URL+"&time="+time+"&Linkid="+linkId+"&Dest="+telephone+"&Msg="+msgContent;
			
			logger.info("下发短信_请求的URL为："+url);
			HttpEntity entity = HttpUtils.get(url);
			reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String str = reader.readLine();
			
			logger.info("\r\n下行短信_返回的响应信息是："+str);
			if(str.endsWith("0")){
				logger.info("下发短信成功！");
			}else{
				logger.error(String.format("\r\n下行短信失败，响应信息：%s", str));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("下行短信接口_请求下发短信服务器失败！");
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("\r\n下行短信接口_关闭输入流错误！",e);
				}
			}
		}
		
	}
	
	/**
	 * 生成LINKID。18位：12位时间戳(yyMMddHHmm)+6位随机数字。
	 */
	public String getLinkID()
	{
		try
		{
			StringBuilder sb = new StringBuilder(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			sb.append(getRandomNumber());
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成六位随机码作为校验码
	 */
	private String getRandomNumber()
	{
		int num = (int) (Math.random() * 900000 + 100000);
		return String.valueOf(num);
	}

}
