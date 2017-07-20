package com.newspace.common.log;

import org.apache.log4j.Logger;

/**
 * JLogger.java 
 * 描 述:  用于记录日志的类  
 * 作 者:  huqili
 * 历 史： 2014-4-18 创建
 */
public class JLogger extends org.apache.log4j.Logger
{
	protected Logger logger;

	public JLogger()
	{
		this(JLogger.class.getName());
	}

	protected JLogger(String name)
	{
		super(name);
		this.logger = Logger.getLogger(name);
	}
}