package com.newspace.payplatform.order.bo.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.newspace.common.bo.impl.GenericBoImpl;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.JDBCUtils;
import com.newspace.common.utils.PropertiesUtils;
import com.newspace.payplatform.order.bo.PaymentOrderBo;
import com.newspace.payplatform.order.dao.PaymentOrderDao;
import com.newspace.payplatform.order.dao.impl.PaymentOrderDaoImpl;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.unicomhttp.HttpSendMsgTask;

/**
 * PaymentOrderBoImpl.java 
 * @description  PaymentOrderVo的Business层实现类
 * @author huqili
 * @date 2014年11月21日　修改
 */
public class PaymentOrderBoImpl extends GenericBoImpl<PaymentOrderVo, PaymentOrderDao> implements PaymentOrderBo
{
	
	/**
	 * 预警阀值(用户)
	 */
	private static final Integer  WARN_THRESHOLD_USER;
	
	/**
	 * 预警阀值(设备)
	 */
	private static final Integer  WARN_THRESHOLD_ATETID;
	
	/**
	 * 预警阀值(IP)
	 */
	private static final Integer  WARN_THRESHOLD_IP;
	
	/**
	 * 接收预警信息的的手机号，可能有多个，用,号分开。
	 */
	private static final String WARN_TELEPHONE;
	
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	
	private static final JLogger logger = LoggerUtils.getLogger(PaymentOrderDaoImpl.class);
	
	static{
		Properties prop = PropertiesUtils.getProps("prewarning"); 
		WARN_THRESHOLD_USER = Integer.parseInt(prop.getProperty("threshold_user"));
		WARN_THRESHOLD_ATETID = Integer.parseInt(prop.getProperty("threshold_atetId"));
		WARN_THRESHOLD_IP = Integer.parseInt(prop.getProperty("threshold_IP"));
		WARN_TELEPHONE = prop.getProperty("telephone");
	}
	
	
	/**
	 * 清除缓存，并返回对应数据库记录的最新状态
	 * @param vo 要清除缓存的PaymentOrderVo对象
	 * @return PaymentOrderVo 最新状态的vo对象
	 */
	@Override
	public PaymentOrderVo evictCache(PaymentOrderVo vo)
	{
		String id = vo.getId();
		getDao().evictCache(vo);
		return get(id);
	}

	/**
	 * 检查订单是否异常接口实现。
	 * @param vo 要查询的订单对象
	 */
	@Override
	public void checkAccount(PaymentOrderVo vo) {
		
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			//读取配置文件里接收预警短信的手机号
			String[] tels = WARN_TELEPHONE.split(",");
			
			//0、查询订单表中此用户当天的交易额
			if(vo.getUserId() != 0){
				String querySqlforUserId = String.format("select sum(amount) from t_payplatform_order where　userId = '%s' and trunc(updateTime) = to_date('%s','yyyyMMdd')",
						vo.getUserId(),new SimpleDateFormat("yyyyMMdd").format(new Date()));
				ps = conn.prepareStatement(querySqlforUserId);
				rs = ps.executeQuery();
				//如果查询出来的值，则判断阀值与此值大小，如果超过阀值则下发短信给管理员。
					if(rs.next()){
						Integer amount = rs.getString(1) == null ? 0 : Integer.parseInt(rs.getString(1)) / 100; //将此用户当天消费总额查询出来并转化为　元。
						Integer amounts = amount + vo.getAmount() / 100;
						ps.close();
						if(amounts >= WARN_THRESHOLD_USER){  //此用户当天消费总额超过阀值，下发短信。
							logger.info(String.format("用户[%s]今日已消费%s元，超过设定阀值%s元！", vo.getUserId(),amounts,WARN_THRESHOLD_USER));
							String msgContent = String.format("请注意!用户[%s]今天已消费%s元,超过设定阀值%s元!",
									vo.getUserId(),amounts,WARN_THRESHOLD_USER);
							for(String telephone : tels){
								
								sendMsg(telephone, msgContent);
							}
						}
					}
			}
			
			//1、查询订单表中此设备当天的交易额
			String querySqlforAtetId = String.format("select sum(amount) from t_payplatform_order where　atetId = '%s' and trunc(updateTime) = to_date('%s','yyyyMMdd')",
					vo.getAtetId(),new SimpleDateFormat("yyyyMMdd").format(new Date()));
			ps = conn.prepareStatement(querySqlforAtetId);
			rs = ps.executeQuery();
			//如果查询出来的值，则判断阀值与此值大小，如果超过阀值则下发短信给管理员。
			if(rs.next()){
				Integer amount = rs.getString(1) == null ? 0 : Integer.parseInt(rs.getString(1)) / 100; //将此设备当天消费总额查询出来并转化为　元。
				Integer amounts = amount + vo.getAmount() / 100;
				ps.close();
				if(amounts >= WARN_THRESHOLD_ATETID){  //此设备当天消费总额超过阀值，下发短信。
					logger.info(String.format("设备[%s]今日已消费%s元，超过设定阀值%s元！", vo.getAtetId(),amounts,WARN_THRESHOLD_ATETID));
					String msgContent = String.format("请注意!设备[%s]今天已消费%s元,超过设定阀值%s元!",
							vo.getAtetId(),amounts,WARN_THRESHOLD_ATETID);
					for(String telephone : tels){
						
						sendMsg(telephone, msgContent);
					}
				}
			}
			
			//2、查询订单表中此IP当天的交易额
			String querySqlforIp = String.format("select sum(amount) from t_payplatform_order where　clientIP = '%s' and trunc(updateTime) = to_date('%s','yyyyMMdd')",
					vo.getClientIP(),new SimpleDateFormat("yyyyMMdd").format(new Date()));
			
			ps = conn.prepareStatement(querySqlforIp);
			rs = ps.executeQuery();
			
			if(rs.next()){
				Integer amount = rs.getString(1) == null ? 0 : Integer.parseInt(rs.getString(1)) / 100; //将此IP当天消费总额查询出来并转化为　元。
				Integer amounts = amount + vo.getAmount() / 100;
				ps.close();
				if(amounts >= WARN_THRESHOLD_IP){  //此IP当天消费总额超过阀值，下发短信。
					logger.info(String.format("IP[%s]今日已消费%s元，超过设定阀值%s元！", vo.getClientIP(),amounts,WARN_THRESHOLD_IP));
					String msgContent = String.format("请注意!IP[%s]今天已消费%s元,超过设定阀值%s元!",
							vo.getClientIP(),amounts,WARN_THRESHOLD_IP);
					for(String telephone : tels){
						
						sendMsg(telephone, msgContent);
					}
				}
			}
		} catch (SQLException e) {
			logger.error(String.format("预警模块_查询用户%s或设备%s当天交易额失败！", vo.getUserId(),vo.getAtetId()));
		}finally{
			JDBCUtils.releaseSource(rs, ps, conn);
		}
		
	}
	
	/**
	 * 下发短信的方法
	 * @param telephone
	 * @param msgContent
	 */
	private void sendMsg(String telephone,String msgContent){
		
		pool.execute(new HttpSendMsgTask(telephone, msgContent));
	}
}
