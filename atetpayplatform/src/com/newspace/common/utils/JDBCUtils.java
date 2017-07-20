package com.newspace.common.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.spring.SpringBeanUtils;


/**
 * JDBCUtils.java 
 * @description  采用JDBC方式操作数据库工具类  
 * @author huqili
 * @date 2014年11月20日
 */
public final class JDBCUtils
{
	private static final ComboPooledDataSource dbpool = SpringBeanUtils.getBean("ds-config");

	private static final JLogger logger = LoggerUtils.getLogger(JDBCUtils.class);

	/**
	 * 从数据库连接池中获取一个连接 
	 */
	public static Connection getConnection()
	{
		Connection conn = null;
		try
		{
			conn = dbpool.getConnection();
		}
		catch (SQLException e)
		{
			logger.error("数据库连接出现错误，获取Connection失败", e);
		}
		return conn;
	}

	/**
	 * 释放资源
	 */
	public static void releaseSource(ResultSet result, Statement stat, Connection conn)
	{
		if (result != null)
		{
			try
			{
				result.close();
			}
			catch (SQLException e)
			{
				logger.error("释放资源ResultSet失败", e);
			}
		}

		if (stat != null)
		{
			try
			{
				stat.close();
			}
			catch (SQLException e)
			{
				logger.error("释放资源Statement失败", e);
			}
		}

		if (conn != null)
		{
			try
			{
				conn.close();
			}
			catch (SQLException e)
			{
				logger.error("释放资源Connection失败", e);
			}
		}
	}
}