<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  	<!-- 
    <form action="atetAlipayNotify.do" method="post">
    	<input type="text" value="partnerId=0&userId=1032000003&deviceId=20140905180227977101&productId=8c:77:16:24:89:0a&gameName=AtetPayDemo4&deviceCode=ATETW900&deviceType=2&channelId=20140815095938531103&cpId=20140915191951837143&cpOrderNo=14114553675083224&cpNotifyUrl=http://112.95.163.214:25000/atetpayplatform/atetpay.do&packageName=com.atet.pay.demo4&appId=20140915200454029161&payPoint=82&counts=1&amount=100" name="body"/>
    	<input type="text" value="10011409231455000002" name="out_trade_no"/>
    	<input type="text" value="2014092317993001" name="trade_no"/>
    	<input type="text" value="TRADE_FINISHED" name="trade_status"/>
    	
    	<input type="submit" value="submit"/>
    </form>
     -->
     This is my atetpayplatform jsp!
  </body>
</html>
