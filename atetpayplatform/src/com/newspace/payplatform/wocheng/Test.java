package com.newspace.payplatform.wocheng;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class Test {

	public static void main(String[] args) throws HttpException, IOException {
		
		doPost();
	}
	
	private static void doPost() throws HttpException, IOException
	{
		
			String param = "res=OK&commandid=1065556131&remark=&Src=&Cmd=DC10*202010154800000*310012STUV&code=0&content=DC10*202010154800000*310012STUV&smgw=1065556131&Linkid=160808142930800000612047&Status=DELIVRD&Spid=WOGAME&linkid=&orderid=160808142930800000612047&mo_msg=DC10*202010154800000*550012STUV&mobile=18615653718";
			
		  	HttpClient client = new HttpClient();

		    PostMethod method = new PostMethod("http://woc.at-et.cn:25000/atetpayplatform/wocNotify.do");
		    
		    method.setParameter("", param);  //设置参数

		    client.executeMethod(method);  //执行请求

		    method.getParams().setContentCharset("utf-8"); //设置编码

		    //打印请求状态

		    System.out.println(method.getStatusLine());

		    //打印请求结果

		    System.out.println(method.getResponseBodyAsString());

	}
}
