package com.newspace.payplatform.unicomwo.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.JsonUtils;
import com.newspace.payplatform.base.DefaultBoImpl;
import com.newspace.payplatform.unicomwo.param.WoTraceResp;
import com.newspace.payplatform.unicomwo.param.WoTraceResp.traceInfos;
import com.newspace.payplatform.unicomwo.vo.WoTraceVo;

/**
 * @description 封装并记录沃邮箱交易轨迹图工具类
 * @author huqili
 * @since JDK1.6
 */
public class TraceUtils 
{

	private static final DefaultBoImpl traceBo = SpringBeanUtils.getBean(DefaultBoImpl.class);
	
	static
	{
		traceBo.setRelateVo(WoTraceVo.class);
	}
	
	/**
	 * 根据交易号查询交易轨迹
	 * @param transactionId
	 * @return
	 */
	public static WoTraceVo getTrace(String transactionId)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(WoTraceVo.class);
		criteria.add(Restrictions.eq("transactionId", transactionId));
		return (WoTraceVo) traceBo.uniqueResult(criteria);
	}
	
	/**
	 * 记录订单轨迹图
	 */
	@SuppressWarnings("unchecked")
	public static void insertTrace(String code,String phone,String subject,int price,String transactionId)
	{
		WoTraceVo vo = new WoTraceVo();
		vo.setPhone(phone);
		vo.setTransactionId(transactionId);
		vo.setTrace(fillTrace(code,phone, subject, price, transactionId));
		vo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
		traceBo.insert(vo);
	}
	
	/**
	 * 封装订单轨迹图
	 * @return
	 */
	private static String fillTrace(String code,String phone,String subject,Integer price,String transactionId)
	{
		WoTraceResp trace = new WoTraceResp();
		List<traceInfos> traceInfoList = new ArrayList<traceInfos>();
		
		//定义每个action的时间
		long currentTime = System.currentTimeMillis();
		String currentTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date(currentTime));
		String _currentTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTime));
		
		long secondTime = currentTime + 2100;
		String secondTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date(secondTime));
		
		long thirdTime = currentTime + 90680;
		String thirdTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date(thirdTime));
		String _thirdTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(thirdTime));
		
		long fourthTime = currentTime + 93880;
		String fourthTimeFormat = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date(fourthTime));
		
		
		
		
		trace.setResultCode("0");
		trace.setResultDescription("success");
		trace.setTransactionId(transactionId);
		
		//封装轨迹图
		traceInfos traceInfo_1 = new traceInfos();
		traceInfo_1.setTime(currentTimeFormat);
		traceInfo_1.setAction("用户获取支付验证码");
		traceInfo_1.setSmsFromTo("应用->WO+计费平台");
		traceInfo_1.setSmsContent(phone+"发起购买:"+subject+":金额:"+price.doubleValue());
		traceInfo_1.setDescription(_currentTimeFormat+"|paymentUser:"+phone+"|outTradeNo:"+transactionId+"|subject:"+subject+"|price:"+price.doubleValue()+"|quantity：1|totalFee:"+price.doubleValue());
		traceInfoList.add(traceInfo_1);
		
		traceInfos traceInfo_2 = new traceInfos();
		traceInfo_2.setTime(secondTimeFormat);
		traceInfo_2.setAction("平台向用户下发验证码");
		traceInfo_2.setSmsFromTo("10655516005->"+phone);
		traceInfo_2.setSmsContent(fillMsgContent_action2(code,subject, price));
		traceInfoList.add(traceInfo_2);
		
		traceInfos traceInfo_3 = new traceInfos();
		traceInfo_3.setTime(thirdTimeFormat);
		traceInfo_3.setAction("用户确认支付");
		traceInfo_3.setSmsFromTo("应用->WO+计费平台");
		traceInfo_3.setSmsContent("用户回填验证码"+code+"，确认支付。");
		traceInfo_3.setDescription(_thirdTimeFormat+"|price:"+price.doubleValue()+"|totalFee："+price.doubleValue()+"|timeStamp:"+thirdTimeFormat+"|paymentUser:"+phone+"|outTradeNo:"+transactionId+"|subject:"+subject+"|quantity:1 |paymentcodesms:"+code);
		traceInfoList.add(traceInfo_3);
		
		traceInfos traceInfo_4 = new traceInfos();
		traceInfo_4.setTime(fourthTimeFormat);
		traceInfo_4.setAction("计费成功通知短信");
		traceInfo_4.setSmsFromTo("10655516005->"+phone);
		traceInfo_4.setMessageContext(fillMsgContent_action4(subject, price));
		traceInfoList.add(traceInfo_4);
		
		
		trace.setTraceInfos(traceInfoList);
		
		return JsonUtils.toJsonWo(trace);
	}
	
	//封装第二个action的短信内容
	public static String fillMsgContent_action2(String code,String subject, Integer price)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("您好，感谢您订购ATET ").append(subject);
		sb.append("业务。资费").append(price.doubleValue()).append("元");
		sb.append("不含通信费。请回复").append(code);
		sb.append("确认订购。【联通APP计费】");
		
		return sb.toString();
	}
	
	//封装第四个action的短信内容
	public static String fillMsgContent_action4(String subject,Integer price)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("尊敬的用户，您已经成功购买ATET的 ").append(subject);
		sb.append("，支付金额：").append(price.doubleValue()).append("元");
		sb.append("。咨询电话：4000600611。【联通APP计费】");
		
		return sb.toString();
	}
	
	//获取三位随机码
	public static String getcode()
	{
		return String.valueOf((int)(Math.random()*900 + 100));
	}
	
	public static void main(String[] args) {
		System.out.println(fillTrace("125","18681523799", "A游学单点",2, "0d2fbf7f-7bd4-4690-9b1a-4669c5f73dec"));
		
		System.out.println(fillMsgContent_action2("125","一大西瓜", 1));
		System.out.println(fillMsgContent_action4("一大西瓜", 1));
		
		
		
	}
		
}
