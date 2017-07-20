// **********************************************************************
//
// Copyright (c) 20014-2018 SSHELL, Inc. All rights reserved.
//
// **********************************************************************

module SmModule
{
    sequence<byte> strConentSeq;
    
	struct Mt
	{
		string				 strSpServiceNumber;	//源号码，最大长度20位？		
		string				 strChargeNumber;		//计费号码 
		byte		         cUserCount;			//目标用户数 填1
		string				 strUserNumber;			//目的号码
		string				 strSpNumber;			//企业ID号码  应用层无需填写
		string				 strServiceCode;		//业务代码
		byte				 cFeeType;				//计费类型
		string				 strFeeCode;			//计费金额
		string				 strGivenCode;			//赠送话费   无需关心
		byte				 cAgentFlag;			//代收费标志，0：应收；1：实收，字符  无需关心
		byte				 cMtReason;				//引起MT消息的原因 
													//0-MO点播引起的第一条MT消息；
													//1-MO点播引起的非第一条MT消息；
													//2-非MO点播引起的MT消息；
													//3-系统反馈引起的MT消息。
		byte		         cPriority;				//优先级  没用
		string				 strExpireTime;			//没用
		string				 strAtTime;				//没用
		byte		         cReportFlag;			//状态报告标记
													//0-该条消息只有最后出错时要返回状态报告
													//1-该条消息无论最后是否成功都要返回状态报告
													//2-该条消息不需要返回状态报告
													//3-该条消息仅携带包月计费信息，不下发给用户，要返回状态报告
		byte		         cPID;					//PID  默认0 
		byte		         cUDHI;					//UDHI 长短信时为1
		byte		         cMsgFormat;			//短信编码方式
		byte		         cMsgType;				//消息类型，默认0
		int					 lMsgLen;				//消息长度
		strConentSeq		 strMsgContent;			//短信内容
		string				 strReserve;			//LinkID
	};
	
	struct MtResp
	{
		string				 strGWID;				//网关MSGID，30字节
		int					 lErrorCode;			//应答错误码
		string				 strReserve;			//保留字段
	};

	struct MtReport
	{
		string			     strGWID;				//网关MSGID，30字节
		byte 		         cReportType;			//0：对先前一条Submit命令的状态报告 1：对先前一条前转Deliver命令的状态报告
		string				 strUserNumber;	        //接收短消息的手机号，手机号码前加“86”国别标志
		byte 		         cStatus;				//该命令所涉及的短消息的当前执行状态 0：发送成功 1：等待发送 2：发送失败
		byte 		         cErrorCode;			//当State=2时为错误码值，否则为0
		string				 strReserve;			//保留字段
	};

	struct Mo
	{
		string				 strGWID;				//网关MSGID，30字节	
		string				 strUserNumber;         //
		string				 strServiceNumber;      //
		byte				 cPID;					//PID  默认0
		byte				 cUDHI;                 //UDHI 长短信时为1
		byte				 cMsgFormat;            //短信编码方式
		int					 lMsgLen;               //消息长度
		strConentSeq		 strMsgContent;         //短信内容
		string				 strReserve;            //LinkID
	};

	interface SmMsg
	{
	  //网关为服务端   SP为客户端
	  // 0 成功 
	  // 1 用户名密码错误 
	  // 2 IP错误	
	  int Login(string strUserName, string strPasswd);

	  //网关为服务端   SP为客户端
	  //0  成功  
	  //1 为客户端信息错误断开连接，需要重新进行Login
	  //2 消息结构错误
	  //3 超流量错误
	  int OnMt( Mt sMt, out string strGWID);

	  //SP为服务端   网关为客户端
	  // 0 成功
	  int OnMtResp(MtResp sResp);

	  //SP为服务端   网关为客户端
	  // 0 成功
	  int OnReport(MtReport sReport);

	  //SP为服务端   网关为客户端
	  // 0 成功
	  int OnMo(Mo sMo);

	};
};