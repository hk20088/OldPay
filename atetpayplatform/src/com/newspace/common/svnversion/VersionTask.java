package com.newspace.common.svnversion;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

/**
 * VersionTask.java 
 * 描 述:  Ant创建svn版本文件
 * 作 者:  liushuai
 * 历 史： 2014-8-21 创建
 */
public class VersionTask extends Task
{
	private String versionFileDir;
	private String refid;
	private String versionFileSuffix;

	public VersionTask()
	{
		this.versionFileSuffix = ".sql";
	}

	public void execute() throws BuildException
	{
		String versionNumber = getProject().getProperty("mainVersion");

		Path path = new Path(getProject(), this.versionFileDir);
		File dir = new File(path.toString());
		System.out.println("BaseDir:" + getProject().getProperty("basedir"));
		System.out.println("Path: " + path.toString());
		System.out.println("version file directory:" + dir.getPath());

		if (!dir.exists())
		{
			throw new BuildException("version file directory not exists.");
		}
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return name.matches("^" + VersionTask.this.getProject().getProperty("project.short.name") + ".*sql$");
			}
		};
		File[] files = dir.listFiles(filter);
		String version = "";
		String furtherVersion = "";
		if ((files != null) && (files.length > 0))
		{
			Arrays.sort(files);
			version = files[(files.length - 1)].getName();
			//拿到当前最新版本的主版本号 比如 V2.01中的  2
			String oldMainVersion = version.substring(version.indexOf("_V") + 2, version.indexOf("."));
			//拿到当前最新版本的子版本号比如V2.01中的  01
			String subVersion = version.substring(version.indexOf(".") + 1, version.indexOf(".") + 3);
			//拼装当前版本
			version = getProject().getProperty("project.short.name") + "_V" + versionNumber + ".";
			int i = Integer.parseInt(subVersion);
			int newSubVersion = i + 1;
			if (oldMainVersion.equals(versionNumber))
			{
				furtherVersion = version + getSubVersion(newSubVersion);
				version = version + subVersion;
			}
			else
			{
				furtherVersion = version + "01";
				version = version + "00";
			}
		}
		else
		{
			version = getProject().getProperty("project.short.name") + "_V" + versionNumber + ".";
			furtherVersion = version + "01";
			version = version + "00";
		}
		File sqlFile = new File(path.toString() + File.separator + furtherVersion + this.versionFileSuffix);
		try
		{
			sqlFile.createNewFile();
		}
		catch (IOException e)
		{
			throw new BuildException("create new modify file fail..." + e.getMessage());
		}

		System.out.println("version:" + version);
		getProject().setProperty(this.refid, version);
	}

	private String getSubVersion(int subVersion)
	{
		String i = "00" + subVersion;
		return i.substring(i.length() - 2, i.length());
	}

	public String getVersionFileDir()
	{
		return this.versionFileDir;
	}

	public void setVersionFileDir(String versionFileDir)
	{
		this.versionFileDir = versionFileDir;
	}

	public String getRefid()
	{
		return this.refid;
	}

	public void setRefid(String refid)
	{
		this.refid = refid;
	}

	public String getVersionFileSuffix()
	{
		return this.versionFileSuffix;
	}

	public void setVersionFileSuffix(String versionFileSuffix)
	{
		this.versionFileSuffix = versionFileSuffix;
	}
}