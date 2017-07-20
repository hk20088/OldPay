package com.newspace.payplatform.unicomiptv;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class Test {

	public static void main(String[] args) throws HttpException, IOException {

		String orderNo = "10011607041028003879";

		HttpClient client = new HttpClient();

		GetMethod method = new GetMethod("http://localhost/atetpayplatform/IptvNotify.do?Act=100&AppId=10000001000001&ThirdAppId=12345&Uin=13012345678&ConsumeStreamId=f9ab14ea-b2ca-408b-8252-36f3070b1c00&TradeNo=10011607041028003879&Subject=abc&Amount=20.00&ChargeAmount=20.00&ChargeAmountIncVAT=20.00&ChargeAmountExclVAT=20.00&Country=&Currency=CNY&Note=test&TradeStatus=1&CreateTime=2014-05-08+20%3A05%3A17&Share=20.00&IsTest=&PayChannel=Paypal&Sign=7fb1ea60fcbb14bfd1f345fb7274f7ef");

		client.executeMethod(method); // 执行请求

		method.getParams().setContentCharset("utf-8"); // 设置编码

		// 打印请求状态

		System.out.println(method.getStatusLine());

		// 打印请求结果

		System.out.println(method.getResponseBodyAsString());
	}
	
	

}
