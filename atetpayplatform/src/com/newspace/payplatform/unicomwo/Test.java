package com.newspace.payplatform.unicomwo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpEntity;

import com.newspace.common.utils.HttpUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.payplatform.unicomwo.param.WoConfirmReq;
import com.newspace.payplatform.unicomwo.param.WopayReq;
import com.newspace.payplatform.unicomwo.utils.EncodeUtils;

public class Test {

	private static final ExecutorService pool = Executors.newCachedThreadPool();
	public static void main(String[] args) throws Exception {
		
		/* ExecutorService pool = Executors.newCachedThreadPool();
		
		for(int i=0;i<1;i++)
		{
			Runnable run = new Runnable() {
				
				@Override
				public void run() 
				{

					try 
					{
//						sendSMS("18676431610");  //李乔
//						sendSMS("13286689628");  //唐双利
//						sendSMS("18681523796");  //胡起立
						sendSMS("18601109196");  //刘总
//						sendSMS("13208978660");  //路人甲
					} 
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
			};
			pool.execute(run);
		}
		pool.shutdown();*/
		
		sendSMS("18681523796");  //胡起立
//		sendSMS("18589017366");
		
//		confirm("18601109196", "44e5d26578987894585d0", "363");
		
		//直接支付
//		pay("18681523796");
	}
	
	
	
	private static void confirm(String phone,String orderid,String messCode) throws Exception
	{
		String url = "http://112.95.163.214:25000/atetpayplatform/confirm.do";
		
		WoConfirmReq reqVo = new WoConfirmReq();
		reqVo.setPhone(phone);
		reqVo.setOrderId(orderid);
		reqVo.setMessCode(messCode);
		
		String json = JsonUtils.toJson(reqVo);
		HttpEntity entity =  HttpUtils.postJson(url, json, "utf-8");
		
		/*BufferedReader  read = null;
		read = new BufferedReader(new InputStreamReader(entity.getContent()));
		System.out.println(read.readLine());*/
		
		InputStream is = entity.getContent();
		StringBuffer sb = new StringBuffer();
		byte[] bt = new byte[1024];
		while(true)
		{
			int i = is.read(bt);
			if(i<0)
			{
				break;
			}
			sb.append(new String(bt, 0, i,"utf-8"));
		}
		System.out.println(sb.toString());
	}
	
	private static void sendSMS(String phone) throws Exception
	{
		
//		String url = "http://10.1.1.62/atetpayplatform/unicomWoPay.do";
//		String url= "http://pay.atet.tv:25000/atetpayplatform/unicomWoPay.do";
//		String url= "http://112.95.163.214:25000/atetpayplatform/unicomWoPay.do";
		String url= "http://117.79.147.211:25000/atetpayplatform/unicomWoPay.do";
		
		//肖高翔 18670303951
		//李乔 18676431610
		//山东 18678028610
		//湖南 18673987945 省份通道关闭
		//南昌 18679137990
		//贵州  18685110843
		//福建  18606079269
		//刘总  18601109196
		String channelId = "1012";
		String key = "101%_2_$";
		
		String pwd = EncodeUtils.encode(key, phone);
		
		WopayReq info = new WopayReq();
//		info.setPhoneNum(pwd);
		info.setChannelId(channelId);
		info.setOrderId("44e5d265789878945465464215454");
		info.setPhone(phone);
		info.setCorpId("1111");
//		String json = JsonUtils.toJsonWithExpose(info);
		String json = JsonUtils.toJson(info);
		
		
		
		/*String url = "http://10.1.1.62/atetpayplatform/woplusTrace.do";
//		String url = "http://pay.atet.tv:25000/atetpayplatform/woplusTrace.do";
		WoTraceReq reqVo = new WoTraceReq();
		reqVo.setTransactionId("0d2fbf7f-7bd4-4690-9b1a-4669c5f73dec");
		reqVo.setTransactionId("0d2fbf7f-7bd4-4690-9b1a-4669c5f73dec");
		String json = JsonUtils.toJson(reqVo);*/
		
		try {
			HttpEntity entity = HttpUtils.postJson(url, json,"utf-8");
			
			BufferedReader reader = null;
			
			reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String str = reader.readLine();
			
			System.out.println(str);
			System.out.println("测试");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	private static void pay(String phone)
	{
		String url = "http://smtp.wo.cn:3071/aiwmServPortal/rest/woplus/charge";
		
		String channelId = "1012";
		String key = "101%_2_$";
		
		String pwd = EncodeUtils.encode(key, phone);
		
		WopayReq info = new WopayReq();
		info.setPhoneNum(pwd);
		info.setChannelId(channelId);
		info.setOrderId("44e5d26578987894585d0");
		info.setPhone(phone);
		String json = JsonUtils.toJsonWithExpose(info);
		
		try {
			HttpEntity entity = HttpUtils.postJson(url, json,"utf-8");
			
			BufferedReader reader = null;
			
			reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String str = reader.readLine();
			
			System.out.println(str);
			System.out.println("测试");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
