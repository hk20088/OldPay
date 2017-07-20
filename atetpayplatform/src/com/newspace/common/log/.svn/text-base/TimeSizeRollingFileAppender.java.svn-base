package com.newspace.common.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 根据日期和文件大小滚动的日志文件适配器
 * @author huqili
 */
public class TimeSizeRollingFileAppender extends FileAppender
{
	/**
	 * 每日日志文件个数的最大值
	 */
	private long maxBackupIndex = 5;

	/**
	 * 单个日志文件大小限制
	 */
	private long maxFileSizes = 10 * 1024 * 1024;

	/**
	 * 单个日志文件大小限制（供配置文件中填充的属性）
	 */
	private String maxFileSize = maxFileSizes + "";

	/**
	 * 日志文件名中日期格式
	 */
	private String datePattern = "'.'yyyy-MM-dd";

	/**
	 * 当天的日志文件序号
	 */
	private int lastIndex = 0;

	/**
	 * 无参构造方法
	 */
	public TimeSizeRollingFileAppender()
	{
	}

	/**
	 * 构造方法，初始化部分参数
	 * @param layout {@link org.apache.log4j.Layout}对象
	 * @param fileName 日志文件路径
	 * @param datePattern 日志文件名上的日期格式
	 * @throws IOException
	 */
	public TimeSizeRollingFileAppender(Layout layout, String fileName, String datePattern) throws IOException
	{
		super(layout, fileName, Boolean.TRUE);
		this.datePattern = datePattern;
		scheduleFile();
	}

	@Override
	protected void subAppend(LoggingEvent event)
	{
		scheduleFile();
		super.subAppend(event);
	}

	public long getMaxBackupIndex()
	{
		return maxBackupIndex;
	}

	public void setMaxBackupIndex(long maxBackupIndex)
	{
		this.maxBackupIndex = maxBackupIndex;
	}

	public String getMaxFileSize()
	{
		return maxFileSize;
	}

	public void setMaxFileSize(String maxFileSize)
	{
		this.maxFileSizes = convertFileSize(maxFileSize);
		this.maxFileSize = this.maxFileSizes + "";
	}

	/**
	 * 将日志文件大小限制从String类型的值转换成long类型的数值
	 * @param fileSize
	 * @return
	 */
	private long convertFileSize(String fileSize)
	{
		try
		{
			long size = 0;
			if (fileSize.matches("^\\d+$"))
			{
				size = Long.parseLong(fileSize);
			}
			else if (fileSize.matches("^\\d+\\w+$"))
			{
				String temp = fileSize.replaceAll("\\d+", "");
				String num = fileSize.replace(temp, "");
				size = Long.parseLong(num);
				if (temp.equalsIgnoreCase("m") || temp.equalsIgnoreCase("mb"))
				{
					size = size * 1024 * 1024;
				}
				else if (temp.equalsIgnoreCase("g") || temp.equalsIgnoreCase("gb"))
				{
					size = size * 1024 * 1024 * 1024;
				}
			}
			return size;
		}
		catch (NumberFormatException e)
		{
			LogLog.error("convert file size fail.", e);
			return 0l;
		}
	}

	/**
	 * 根据当前日志的日期和大小，判断是否需要生成新的日志文件，并将当前日志文件重命名
	 */
	private void scheduleFile()
	{
		File file = new File(fileName);
		if (file.exists())
		{
			boolean lenOver = file.length() >= maxFileSizes;
			if (checkDate(file) || lenOver)
			{
				String pattern = datePattern;
				lastIndex = (lastIndex >= maxBackupIndex) ? 0 : lastIndex;
				if (lastIndex > 0)
				{
					pattern += "'.'" + lastIndex;
				}
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				String datestr = sdf.format(new Date(file.lastModified()));
				File nfile = new File(fileName + datestr);
				renameFile(nfile, file);
				if (lenOver)
				{
					lastIndex++;
				}
			}
		}
	}

	/**
	 * 将当前日志文件重新命名
	 * @param nfile 重命名后的日志文件
	 * @param ofile  当前日志文件
	 */
	private void renameFile(File nfile, File ofile)
	{
		try
		{
			if (nfile.exists())
			{
				nfile.delete();
			}
			closeFile();
			ofile.renameTo(nfile);
			setFile(fileName, Boolean.FALSE, bufferedIO, bufferSize);
		}
		catch (IOException e)
		{
			LogLog.error("rename file fail.", e);
		}
	}

	/**
	 * 检查文件的最后更新日期是否在今天之前
	 * @param file 被检查的文件
	 * @return
	 */
	private boolean checkDate(File file)
	{
		Calendar today = Calendar.getInstance();
		today.setTime(new Date(System.currentTimeMillis()));
		today.set(Calendar.HOUR, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		Calendar last = Calendar.getInstance();
		last.setTime(new Date(file.lastModified()));
		return last.before(today);
	}

	public String getDatePattern()
	{
		return datePattern;
	}

	public void setDatePattern(String datePattern)
	{
		this.datePattern = datePattern;
	}

	public static void main(String[] args) throws Exception
	{
		LoggerUtils.initLogConfig("D:\\workspaces\\saigame\\config\\log4j.xml");
		JLogger logger = LoggerUtils.getLogger(TimeSizeRollingFileAppender.class);
		logger.debug("cccccccccc");
		logger.debug("dddddddddddddd");
		logger.error(new Exception("aaaaaaaaaaaaaa"));
	}
}