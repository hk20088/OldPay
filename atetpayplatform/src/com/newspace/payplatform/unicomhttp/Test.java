package com.newspace.payplatform.unicomhttp;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.newspace.payplatform.unicomhttp.param.VacNotifyUrlEnum;
import com.newspace.payplatform.unicomwo.param.WoNotifyUrlEnum;



public class Test {

	public static void main(String[] args) throws IOException {
	
		
		doPost();
	
	
	}
	
	
	private static void doPost() throws HttpException, IOException
	{
//		String param = "mo_msg=dc1*202010100800000*2000&mobile=18681523796&commandid=106555613230&linkid=10011408161411000130&code=0";
		
			String param = "res=OK&commandid=1065556131&remark=&Src=&Cmd=DC1*202010154800000&code=0&content=DC1*202010154800000*500&smgw=1065556131&Linkid=160808142930800000612047&Status=DELIVRD&Spid=WOGAME&linkid=&orderid=160808142930800000612047&mo_msg=DC10*202010154800000*5000&mobile=17608945317";
			
		  	HttpClient client = new HttpClient();

		    PostMethod method = new PostMethod("http://117.79.147.212:25000/atetpayplatform/unicomNotify.do");
//		    PostMethod method = new PostMethod("http://pay.atet.tv:25000/atetpayplatform/unicomNotify.do");
//		    PostMethod method = new PostMethod("http://10.1.1.62/atetpayplatform/unicomNotify.do");
		    method.setParameter("", param);  //设置参数

		    client.executeMethod(method);  //执行请求

		    method.getParams().setContentCharset("utf-8"); //设置编码

		    //打印请求状态

		    System.out.println(method.getStatusLine());

		    //打印请求结果

		    System.out.println(method.getResponseBodyAsString());

	}
	
	//GET请求
	private static void doGet() throws IOException
	{
		HttpMethod method = new GetMethod("http://10.1.1.62/atetpayplatform/unicomNotify.do?mo_msg=dc1*202010100800000*2000&mobile=18681523796&commandid=106555613230&linkid=10011408161411000130&code=0");
		
		  //打印请求状态

	    System.out.println(method.getStatusLine());

	    //打印请求结果

	    System.out.println(method.getResponseBodyAsString());
	}
}
