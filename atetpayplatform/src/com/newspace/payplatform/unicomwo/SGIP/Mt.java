package com.newspace.payplatform.unicomwo.SGIP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.huawei.insa2.comm.sgip.message.SGIPMessage;
import com.huawei.insa2.comm.sgip.message.SGIPSubmitMessage;
import com.huawei.insa2.comm.sgip.message.SGIPSubmitRepMessage;
import com.huawei.smproxy.SGIPSMProxy;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.StringUtils;

/**
 * @description SP->SMS
 * @author huqili
 * @date 2016年7月20日
 *
 */
public class Mt {



	public static void main(String[] args) throws UnsupportedEncodingException {
		
//		System.out.println(sgipMt("13034134590","test"));
		
	}

	private static final JLogger logger = LoggerUtils.getLogger(Mt.class);
	
	//这里创建一个特殊的instance变量（它得是一个对象）来充当锁：零长度的byte数组对象创建起来将比任何对象都经济，生成零长度的byte[]对象只需3条操作码。 
	private static byte[] lock = new byte[0];
	
	/**
	 * 由于联通网关同一时间只允许一个链接，所以登陆SMS时使用长链接
	 * 方法：项目启动时，连接SMG，同时初始化SGIPSMProxy对象（使用单例模式，保证Submit的时候取到的对象是同一个）
	 * @param phone
	 * @param msgContent
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static int sgipMt(String phone,String msgContent) throws UnsupportedEncodingException
	{
		int returnCode = -1;
		// 连接登陆
		SGIPSMProxy sgipsmp = SGIPSMProxy.getInstance();
		
		logger.error("Submit类SGIPSMProxy对象的Code:"+sgipsmp.hashCode());
		//先获取与SMG的连接状态，如果是成功则不用Bind
	/*	logger.error("Submit类查询与SMG的连接状态...");
		String str = sgipsmp.getConnState();
		logger.error("Submit类与SMG的连接状态是："+str);*/
		
		String[] UserNumber = {phone};// 接收短信的手机号码，前边要加上86
		byte[] MessageContent = msgContent.getBytes("GB2312");
		
		// 下发短信
		SGIPSubmitMessage sgipsubmit = new SGIPSubmitMessage(SGIPConstant.SPNUMBER, // SP的接入号码
				SGIPConstant.CHARGENUMBER, // 付费号码 string
				UserNumber, // 接收该短消息的手机号，最多100个号码 string[]
				SGIPConstant.CORPID, // 企业代码，取值范围为0～99999 string
				SGIPConstant.SERVICETYPE, // 业务代码，由SP定义 stirng
				03, // 计费类型 int
				"0", // 该条短消息的收费值 stirng
				"0", // 赠送用户的话费 string
				0, // 代收费标志0：应收1：实收 int
				0, // 引起MT消息的原因 int
				06, // 优先级0～9从低 到高，默认为0 int
				null, // 短消息寿命的终止时间 date
				null, // 短消息定时发送的时间 date
				1, // 状态报告标记 int
				0, // GSM协议类型 int
				0, // GSM协议类型 int
				15, // 短消息的编码格式 int
				0, // 信息类型 int
				MessageContent.length, // 短消息内容长度 int
				MessageContent, // 短消息的内容 btye[]
				"0" // 保留，扩展用 string
		);

		try {
			
			// 收到的响应消息转换成rep
			returnCode = ProcessSubmitRep(sgipsmp.send(sgipsubmit),phone);
			
		} catch (Exception ex) {
			
			/**
			 * 1 高并发时，有可能多个请求到达这里。 下面的逻辑包含重新连接SMS（即bind），如果多个线程同时bind，会造成网关堵塞（网关同时最多只能有三个bind连接
			 * 2 所以这里用同步锁控制，保证同一时间只有一个线程执行以下代码
			 * 3 在bind前，要判断此时是否能submit成功，如果成功，说明前面的线程已经bind成功，不需要再bind。 
			 */
			logger.error("submit时出现异常，进入同步锁代码块...");
			synchronized (lock)
			{
				try 
				{
					logger.error(String.format("用户[%s]Submit时出现异常，先尝试submit是否成功...",phone));
					//这里不抛异常，即说明submit成功，不再与SMS重新建立连接
					returnCode = ProcessSubmitRep(sgipsmp.send(sgipsubmit),phone);
					logger.error(String.format("用户[%s]尝试submit成功，说明与SMS重新连接成功...",phone));
				}
				catch (Exception e1) 
				{
					//submit抛出异常后，尝试再与SMS建立一个连接 （注意：与SMS的状态为null，并不能说明bind连接是正常的，所以只要抛异常，这里就重新bind）
					logger.error(String.format("用户[%s]尝试Submit时出现异常,说明尚未与SMS重新连接，先断开与SMS的连接。此时状态为[%s]，异常信息[%s]", phone,sgipsmp.getConnState(),e1));
					
					try 
					{
						sgipsmp.close();
						logger.error("断开连接成功，尝试与SMS重新建立连接...");
						ConnSms.getConn();
						
						logger.error("建立连接成功，尝试重新下发短信...");
						//如果重新建立连接成功，则再次给此用户下发短信
						returnCode = ProcessSubmitRep(sgipsmp.send(sgipsubmit),phone);
						
					} catch (Exception e) {
						logger.error("断开与SMS的连接时出现异常，尝试与SMS重新建立连接... 异常信息为："+e);
						ConnSms.getConn();
					}
					logger.error("与SMS重新建立连接完毕，此时连接状态为："+sgipsmp.getConnState());	
				}
				
			}
			
		}
		return returnCode;
	}
	
	private static int ProcessSubmitRep(SGIPMessage msg,String phone) {
		// 收到的响应消息转换成repMsg
		SGIPSubmitRepMessage repMsg = (SGIPSubmitRepMessage) msg;
		logger.error(String.format("用户[%s]Submit的结果是：[%s],sequenceNumber是：[%s]", phone,repMsg.getResult(),repMsg.getSequenceId()));
		
		return repMsg.getResult();
	}
	
}
