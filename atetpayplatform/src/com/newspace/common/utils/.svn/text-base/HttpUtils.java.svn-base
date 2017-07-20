package com.newspace.common.utils;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpProtocolParams;

import com.newspace.common.coder.Coder;
import com.newspace.payplatform.order.PaymentOrderReq;
import com.newspace.payplatform.paytype.web.PayMethodReqVo;
import com.newspace.payplatform.tvbox.param.GetCheckCodeReq;

/**
 * 通过apache httpclient向指定url发送http请求的工具类
 * @author huqili
 * @since jdk1.6 apache httpcore 4.2.2
 * @version 1.0
 */
public class HttpUtils
{
	/**
	 * 发送post请求
	 * @param url 请求URL
	 * @param params 请求参数
	 * @param charset 响应的字符串编码
	 * @return 远程服务器的响应
	 * @throws Exception
	 */
	public static HttpEntity post(String url, Map<String, String> params) throws Exception
	{
		HttpPost post = new HttpPost(url);
		if (ArrayUtils.hasObject(params))
		{
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet())
			{
				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
			post.setEntity(entity);
		}
		return request(post);
	}

	/**
	 * 发送json形式的post请求参数
	 * @param url 请求URL
	 * @param jsonString 请求的json字符串
	 * @return 远程服务器的响应
	 * @throws Exception
	 */
	public static HttpEntity postJson(String url, String jsonString, String charset) throws Exception
	{
		return post(url, jsonString.getBytes(charset));
	}

	/**
	 * 发送post请求
	 * @param url 请求URL
	 * @param bytes post请求的数据
	 * @param params 请求参数
	 * @param charset 字符编码
	 * @return 远程服务器的响应
	 * @throws Exception
	 */
	public static HttpEntity post(String url, byte[] bytes) throws Exception
	{
		HttpPost post = new HttpPost(url);
		ByteArrayEntity entity = new ByteArrayEntity(bytes);
		post.setEntity(entity);
		return request(post);
	}

	/**
	 * 发送get请求
	 * @param url 请求的URL
	 * @return
	 * @throws Exception
	 */
	public static HttpEntity get(String url) throws Exception
	{
		HttpGet get = new HttpGet(url);
		return request(get);
	}

	/**
	 * 发送请求
	 * @param request 请求对象
	 * @return 远程服务器的响应
	 * @throws Exception
	 */
	public static HttpEntity request(HttpUriRequest request) throws Exception
	{
		HttpClient client = getClient();
		HttpResponse response = client.execute(request);
		HttpEntity entity = response.getEntity();
		return entity;
	}

	/**
	 * 得到一个HttpClient对象
	 * @return HttpClient对象
	 * @throws Exception
	 */
	private static HttpClient getClient() throws Exception
	{
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		String agent = "Mozilla/5.0 (Windows; U; Windows NT 5.1;" + " zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9";
		HttpProtocolParams.setUserAgent(client.getParams(), agent);
		Scheme scheme = getSSLScheme();
		client.getConnectionManager().getSchemeRegistry().register(scheme);
		return client;
	}

	/**
	 * 得到一个发送SSL的Scheme
	 * @return SSL的Scheme
	 * @throws Exception
	 */
	private static Scheme getSSLScheme() throws Exception
	{
		TrustManager trustManager = getTrustManager();
		SSLContext context = SSLContext.getInstance(SSLSocketFactory.TLS);
		context.init(null, new TrustManager[] { trustManager }, null);
		SSLSocketFactory factory = new SSLSocketFactory(context, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme scheme = new Scheme("https", 443, factory);
		return scheme;
	}

	/**
	 * 得到TrustManager对象
	 * @return TrustManager对象
	 */
	private static TrustManager getTrustManager()
	{
		TrustManager manager = new X509TrustManager()
		{
			public X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{

			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
			{

			}
		};
		return manager;
	}

	/**
	 * 替换http请求的url
	 * @param url 替换后的url
	 * @return
	 */
	public static String parseUrl(String url)
	{
		url = url.replaceAll("^(http:(/)*)+", "#{http}");
		url = url.replace('\\', '/');
		url = url.replaceAll("/+", "/");
		url = url.replaceFirst("#\\{http\\}", "http://");
		return url;
	}

	/**
	 * 判断一个url是否是http请求的url（是否包含http请求的schema）
	 * @param url http请求的url
	 * @return
	 */
	public static boolean isHttpUrl(String url)
	{
		url = parseUrl(url);
		return url.matches("^((http)|(https))://.*$");
	}

	public static void main(String[] args) throws Exception
	{
		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIVQKknhjuWZHgqZ7k/Ip8lgX4oT"
				+ "8+ko74ZktCUhiKjyoyLmaTVyTM4TTkG8V+9cRuh7NGuHW+2tG/d4dmVo+NPMziQwC7kVHDvMS0bC"
				+ "O2D/Rj5YHJHri6YjtSv93dQRqcQeZkBV998jbORC2HXbOcvX76vJfJ1z34iyfxskxz3fAgMBAAEC"
				+ "gYB+nmNsBKYagFG+M/HbKXPjmntjxAu4er71YJSRZ8ZK4Rn9PebTcppsaH29hcvndjeK++oP72Jq"
				+ "3i5f37RkIU2jS5yu+onRM0guv3K0EWDgC0vMbyQwVVOtsEA+Ei7pI7vKZ9C9RttF+SPeZONukeER"
				+ "Rw3ZOgC6czfbkq25LTSs4QJBAND1AM8DZZFylsRwImcQe9EHAE7mEDA2JHQTUhSEGVaGuCRHs0FW"
				+ "f9M7dlcUr+kiTRzN7c7sd1+ON1eVQsirGLkCQQCjU4OBLJn4A46Cz93wGfxzHoOzlWn2V9rZmUEb"
				+ "Io5YDLCDCxftbxY8ojfmxcpqN6jxgWcWANfuBupg+GNoJw9XAkB7Ro/bkZ1yWbAo5B7nvwnNH9xG"
				+ "r+QNDWQkNuHNyMFKEuDARqyyd12iOQpwBXxihiIIRFVwJoGmljiAK0bjLE3BAkEAo1BS4suyeek0"
				+ "i2FHT1rssEAG+X+iIQ3gbE1uaK+5HhoyNhbVqjm2RpL+yudka6mUaUk3xyy4ve50VtqIrBDCqQJB"
				+ "AI4qFIk2lXRVSAlJW5JZEcIuK7BORB9TzLUBDe7bb2w1WVNVP/ifwUItH0+8fFHe4zyakApW89pS" + "lgq0/YMy40M=";
		
//		privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIMCsjeDrUTg3CEjhUS1WzUgWbvPreRYHlMZCO1GNYsiKVB/Gj/s8Gk1WKug9arfQz3omEMnTHQWvBjHXAjUDnMkIlKEemMkftQEx/0Vn5tyMEOmv6HyiNHwzAZ19WTjBu20FiaxF1zlXV4QusPsF76A2yTX/b5MMvUpE9yeANEpAgMBAAECgYAP4uTPfIUnRAkNb6w6X692qryW3KEeflz3AKMneosFmxfdbfw14jpo15IwBa2kkbwqpqjIA9zues9GRe5wF57ExKYppCriH2gi4o5q1DIJj+Htp3ESn75xGrIzbSm06umOrDjLBu4O/f/lvXG0G9bU0/qtz4mATZV7wucxq159HQJBANR1JRhQEbb+l25uZzCBXUs5f4URl6LdV1wo7cfvIdj/e3dWc28aKQzC66GNWVQVySgy0sRztIrb5oJVfE1FBe8CQQCd3FQZplzSas4rbIO9NcQx3lTCA8pPHr5J7KK8GPFsM0UUyghf4V9lkqCmR7/H4PNKb1/Ck6zRGWv+TRht2XJnAkB+UQv2EZ0GtQXp5YA6qZ+DWzNDNbt3XKxVIgyF49q2/uc0g9TsCJzBdp7MFZM32CAaY2VG4hhEuwupEBRwiIpHAkAk6UvQ45F4sfibNrhnGZdSgcYXx4MalPmxl19F1B8/SY2xmrvyj2Qa//oIZ2Z0eijpErm1aYpTKjqSL7yyktGpAkEAkbe6D3bp5Qcl6vaNNTTO/wYvDo0NdpYdWUrhppKp4C96xDNN68NOVO1ypeilHYcE3LdLXJ6tC7xeFHy8cUDysg==";

		privateKey = Coder.getHexStringByEncryptMD5(privateKey.replaceAll("\r|\n", ""));

		// 注：此密钥对应的appId : 20140703135517600000 ; 此appId对应的payPoint : 20140703135517666666 ,请勿随意改动。

		

		try
		{
			/** 快钱快捷支付_手机动态鉴权接口开始 **/
			/*String url = "http://10.1.1.62:8080/atetpayplatform/getDynNum.do";
			GetDynNumReq vo = new GetDynNumReq();
			vo.setAppId("20140703135517600000");
			vo.setUserId(123456789);
			vo.setCardNo("6259064851009853");
			vo.setExpiredDate("0718");
			vo.setCvv("133");
			vo.setHolderName("刘亚");
			vo.setIdType(0);
			vo.setHolderId("420983199007199411");
			vo.setPhoneNo("15172787796");
			vo.setAmount(100);
			vo.setOrderNo("10011410092149000006");
			String sign = NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey);
			vo.setSign(sign);

			String json = JsonUtils.toJsonWithExpose(vo);*/
			
			/**快钱首次支付接口测试*/
//			String url = "http://112.95.163.214:25000/atetpayplatform/firstQuickPayment.do";
			/*String url = "http://10.1.1.62:8080/atetpayplatform/firstQuickPayment.do";
			FirstQuickPaymentReq vo = new FirstQuickPaymentReq();
			
			vo.setAppId("20140703135517600000");
			vo.setUserId(123456789);
			vo.setCardNo("6259064851009853");
			vo.setExpiredDate("0718");
			vo.setCvv("133");
			vo.setHolderName("刘亚");
			vo.setIdType(0);
			vo.setHolderId("420983199007199411");
			vo.setPhoneNo("15172787796");
			vo.setAmount(100);
			vo.setOrderNo("10011410092149000006");
			
			vo.setPayPoint("20140703135517666666");
			vo.setCounts(1);
			vo.setToken("148639654");
			vo.setCheckCode("683812");

			vo.setGameName("xx");
			vo.setChannelId("CHANN");
			vo.setCpId("cpid12");
			vo.setDeviceId("xx");
			vo.setDeviceCode("123");
			vo.setProductId("234");
			vo.setDeviceType(1);
			vo.setPartnerId("0");
			vo.setPackageName("packagename");

			String sign = NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey);
			vo.setSign(sign);
			String json = JsonUtils.toJsonWithExpose(vo);

			url = "http://10.1.1.199:8080/atetpayplatform/queryBoundCard.do";
			QueryBoundCardReq vo = new QueryBoundCardReq();
			vo.setAppId("20140703135517600000");
			vo.setUserId(1234567);
			String sign = NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey);
			vo.setSign(sign);*/

			/**快捷支付接口测试*/
			/*String url = "http://10.1.1.62:8080/atetpayplatform/quickPayment.do";

			QuickPaymentReq vo = new QuickPaymentReq();
			vo.setAmount(100);
			vo.setAppId("20140703135517600000");
			vo.setCounts(1);
			vo.setPayPoint("82");
			vo.setOrderNo("10011409231455000002");
			vo.setUserId(7040);
			vo.setShortCardNo("6228481076");

			vo.setGameName("AtetPayDemo4");
			vo.setChannelId("20140701212944327101");
			vo.setCpId("20140915191951837143");
			vo.setDeviceId("20140718145457670123");
			vo.setDeviceCode("TCL-CN-NT667B-H9700-QDM");
			vo.setProductId("000");
			vo.setDeviceType(1);
			vo.setPartnerId("0");
			vo.setPackageName("com.atet.pay.demo4");
			vo.setCpNotifyUrl("http://112.95.163.214:25000/atetpayplatform/atetpay.do");
			vo.setCpOrderNo("14113849095896490");

			String sign = NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey);
			System.out.println("sign: " + sign);
			vo.setSign(sign);

			String json = JsonUtils.toJsonWithExpose(vo);*/

			/**查询卡信息接口测试*/
//			String url = "http://112.95.163.231:25000/atetpayplatform/queryCardInfo.do";
			/*String url = "http://pay.atet.tv:25000/atetpayplatform/queryCardInfo.do";
			QueryCardInfoReq vo = new QueryCardInfoReq();
			vo.setAppId("20140703135517600000");
//			 vo.setCardNo("6222300488071260");
//			 vo.setCardNo("6222024000079998312");
			vo.setCardNo("6228480128150121076");
			String sign = NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey);
			vo.setSign(sign);
			String json = JsonUtils.toJsonWithExpose(vo);*/

			/**卡解绑接口测试*/
			/*String url = "http://112.95.163.214:25000/atetpayplatform/unbindCard.do";
			UnbindCardReq vo = new UnbindCardReq();
			vo.setAppId("20140703135517600000");
			vo.setUserId(1234567);
			vo.setShortCardNo("6222301260");
			vo.setBankId("ICBC");
			String sign = NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey);
			vo.setSign(sign);

			String json = JsonUtils.toJsonWithExpose(vo);*/

			/**获取订单号接口测试*/
//			String url = "http://10.1.1.62:8080/atetpayplatform/generateOrderNo.do";
		/*	String url = "http://112.95.163.229:25001/atetpayplatform/generateOrderNo.do";
//			String url = "http://pay.atet.tv:25000/atetpayplatform/generateOrderNo.do";

			GenerateOrderNoReq vo = new GenerateOrderNoReq();
			vo.setAppId("20140703135517600000");
			vo.setAtetId("20141001");
			vo.setChannelId("20141001");
			vo.setDeviceId("20141001");
			vo.setDeviceType(1);
			vo.setVersionCode("1");
			vo.setSign(NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey));
			String json = JsonUtils.toJsonWithExpose(vo);*/

			/**联通VAC支付接口测试*/
			String url = "http://10.1.1.62:80/atetpayplatform/unicompay.do";
//			String url = "http://112.95.163.229:25000/atetpayplatform/unicompay.do";
			PaymentOrderReq vo = new PaymentOrderReq();
//			
			vo.setUserId(123456);
			vo.setAppId("20140703135517600000"); //20140716135251599154  //20140703135517600000
			vo.setAtetId("20141001");
			vo.setOrderNo("10011411261012000001");  //10011411261012000001
			
			
			/*vo.setAmount(100);
			vo.setDeviceType(1);
			vo.setPayPoint("20140703135517666666");
			vo.setDeviceCode("456789");
			vo.setProductId("123456");
			vo.setDeviceId("123456");
			vo.setPartnerId("0");
			vo.setCpId("CP123");
			vo.setPackageName("packagename");
			vo.setChannelId("20141516464545454545");
			vo.setCounts(1);
			vo.setGameName("gameName");*/

			vo.getData();
			vo.setSign("aaa");
//			vo.setSign(NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey));
			String json = JsonUtils.toJsonWithExpose(vo);

			/**同步游戏信息接口测试*/
			/*String url = "http://112.95.163.214:25000/atetpayplatform/gameInfo.do";

			SyncGameReq req = new SyncGameReq();
			req.setAppId("123456");
			req.setCpId("20140821095202916101");
			req.setGameCreateTime(24213421424L);
			req.setGameId("20141128173526178101");
			req.setGameName("现代战争电视版test");
			req.setPackageName("atet.game.com");
			
			String json = JsonUtils.toJson(req);*/
			
			/**终端用户查询订单接口测试*/
			/*String url = "http://10.1.1.62:8080/atetpayplatform/queryUserOrder.do";
			
			TerminalQueryOrderReq t = new TerminalQueryOrderReq();
			t.setUserId(0);
			t.setAtetId("1022");
			String json = JsonUtils.toJson(t);*/
			
			
			
			/**确认订单接口*/
			/*String url = "http://112.95.163.214:25000/atetpayplatform/confirmOrder.do";
//			String url = "http://10.1.1.62:8080/atetpayplatform/confirmOrder.do";
			
			ConfirmOrderReq vo = new ConfirmOrderReq();
			vo.setAppId("20140703135517600000");
			vo.setOrderNo("10011410071624000001");
			vo.setSign(NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey));
			
			String json = JsonUtils.toJsonWithExpose(vo);*/
			
			/**盒子支付，获取验证码接口测试*/
		/*	String url = "http://pay.atet.tv:25000/atetpayplatform/getCheckCode.do";
//			String url = "http://112.95.163.231:25000/atetpayplatform/getCheckCode.do";
//			String url = "http://10.1.1.62:80/atetpayplatform/getCheckCode.do";
			
			GetCheckCodeReq vo = new GetCheckCodeReq();
			vo.setUserId(123456);
			vo.setAppId("20140703135517600000");
			vo.setAtetId("20141001");
			vo.setProductId("20141001");
			vo.setDeviceCode("20141001");
			vo.setTelephone("18682292071");   //18682292071 13286680619
			
			vo.setSign(NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey));
			String json = JsonUtils.toJsonWithExpose(vo);*/
			
			
			
			/**盒子支付，校验验证码接口测试*/
//			String url = "http://10.1.1.62:8080/atetpayplatform/verifyCheckCode.do";
			/*String url = "http://112.95.163.214:25000/atetpayplatform/verifyCheckCode.do";

			VerifyCheckCodeReq vo = new VerifyCheckCodeReq();
			vo.setUserId(123456);
			vo.setAppId("20140703135517600000");
			vo.setAtetId("20141001");
			vo.setProductId("20141001");
			vo.setDeviceCode("20141001");
			vo.setTelephone("13286680619");
			vo.setCaptcha("908807");
			vo.setSign(NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(vo.getData()), privateKey));
			String json = JsonUtils.toJsonWithExpose(vo);*/
			
			/**联通下发短信测试接口*/
//			String url = "http://112.95.163.214:25000/atetpayplatform/test.do";

		/*	Mt mt = new Mt();
			String content = "ooo壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾0";
//			"壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖拾壹贰叁肆伍陆柒捌玖";、
//			String content = "abcdefghijklmnopqrstuvwxYabcdefghijklmnopqrstuvwxYabcdefghijklmnopqrstuvwxYabcdefghijklmnopqrstuvwxYabcdefghijklmnopqrstuvwxYABCDEFGHKJKLMNO";
//			String content = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
			mt.strSpServiceNumber = "10655561999999999999"; // 1065556170
			mt.strChargeNumber = "";
			mt.cUserCount = 1;
			mt.strUserNumber = "18682292071"; // 13267232929 13273892757 18682112926
			mt.strSpNumber = "92919"; // 97022 92919
			mt.strServiceCode = "";
			mt.cFeeType = 03;
			mt.strFeeCode = "0";
			mt.strGivenCode = "0";
			mt.cAgentFlag = 0;
			mt.cMtReason = 0;
			mt.cPriority = 06;
			mt.strExpireTime = "0";
			mt.strAtTime = "1";
			mt.cReportFlag = 1;
			mt.cPID = 0;
			mt.cUDHI = 0;
			mt.cMsgFormat = 15;
			mt.cMsgType = 0;
			mt.lMsgLen = content.length();
			mt.strMsgContent = content.getBytes("GBK"); // UTF-16BE UnicodeBigUnmarked
			mt.strReserve = "0";
			String json = JsonUtils.toJson(mt);*/
			
			
			
			
			
			/**同步CP信息接口测试*/
			/*String url = "http://10.1.1.62:8080/atetpayplatform/cpInfo.do";
//			String url = "http://112.95.163.214:25000/atetpayplatform/cpInfo.do";
			
			HttpEntity entity = HttpUtils.postJson(url, "{appId:\"20140716135251599154\",cpId:\"20140821095202916101\",cpName:\"胖东来\",packageName:\"pangdonglai\",cpNotifyUrl:\"http://www.baidu.com\",payPointList:[{payCode:\"101\",payName:\"测试支付点1\",money:100.00,state:0},{payCode:\"102\",payName:\"测试支付点2\",money:200.00,state:1},{payCode:\"265\",payName:\"测试支付点3\",money:1,state:1}]}", "utf-8");
			*/
			
//			HttpEntity entity = HttpUtils.postJson(url, "{appId:\"20141112163923495268\",cpId:\"20141110164035280179\",cpName:\"创意比特\",cpNotifyUrl:\"\"," +
//					"payPointList:[{payCode:\"416\",payName:\"小礼包\",money:\"400\",state:\"2\"},{payCode:\"417\",payName:\"中礼包\",money:\"1000\",state:\"2\"}," +
//					"{payCode:\"418\",payName:\"大礼包\",money:\"2000\",state:\"2\"}]}", "utf-8");
//			
			
			/**联通下发短信测试*/
			/*String telephone = "18682292071";
			String content = "以为首的互联网平台大鳄占据主流大众市场的地位不可撼动，大而全的平台化战略思路对于现在的创业者越来越艰难。在市场环境和手机屏幕的双重挤压下，移动新媒体与互联网创业公司正在避开大而全的思路，向重度垂直化发展。甚至抛弃主流市场，瞄准了较为小众的人群和需求。";
			String APPEND_BLANK = "                                                                      ";
			String[] tels = telephone.split(",");
			for(String tel:tels){
				if(content.length() > 70){
					Map<Integer, String> map = StringUtils.splitStr(content, 65);
					for(int i = 0; i < map.size(); i++){
						
						sendMsg(tel, map.get(i) + ("(" + (i+1) + "/"+ map.size() + ")") + APPEND_BLANK);
					}
				}
				
			}*/
			
			
			/**
			 * 获取设备支付方式接口测试
			 */
			/*String url = "http://10.1.1.62:8080/atetpayplatform/paymethod.do";
			PayMethodReqVo vo = new PayMethodReqVo();
			vo.setDeviceId("20140820151141185131");
			
			String json = JsonUtils.toJson(vo);*/
			
			
			HttpEntity entity = HttpUtils.post(url, json.getBytes("UTF-8"));
			System.out.println(entity.getContentEncoding());
			InputStream is = entity.getContent();
			StringBuilder str = new StringBuilder();
			byte[] bs = new byte[1024];
			while (true)
			{
				int i = is.read(bs);
				if (i < 0)
				{
					break;
				}
				str.append(new String(bs, 0, i, "utf-8"));
			}
			System.out.println(str);
			System.out.println("测试");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i = 0 ;i < 2000; i++){
			Runnable run = new Runnable() {
				
				@Override
				public void run() {
					payType();
					
				}
			};
			service.execute(run);
		}
		service.shutdown();
		
	}*/
	
	private static void payType(){
		try {
	
			String url = "http://10.1.1.62:8080/atetpayplatform/paymethod.do";
			PayMethodReqVo vo = new PayMethodReqVo();
			vo.setDeviceId("20140820151141185131");
			
			String json = JsonUtils.toJson(vo);
			
			HttpEntity entity = HttpUtils.post(url, json.getBytes("UTF-8"));
			System.out.println(entity.getContentEncoding());
			InputStream is = entity.getContent();
			StringBuilder str = new StringBuilder();
			byte[] bs = new byte[1024];
			while (true)
			{
				int i = is.read(bs);
				if (i < 0)
				{
					break;
				}
				str.append(new String(bs, 0, i, "utf-8"));
			}
			System.out.println(str);
			System.out.println("测试");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			
		}
	}
}