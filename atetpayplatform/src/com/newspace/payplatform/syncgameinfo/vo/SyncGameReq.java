package com.newspace.payplatform.syncgameinfo.vo;

import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.base.JsonVo;

/**
 * SyncGameReq.java 
 * 描 述:  同步游戏信息接口请求参数实体类
 * 作 者:  liushuai
 * 历 史： 2014-9-16 创建
 */
public class SyncGameReq implements JsonVo
{
	private static final long serialVersionUID = 1L;

	private String appId;

	private String gameId;

	private String cpId;

	private String gameName;

	private String packageName;

	private Long gameCreateTime;

	/**
	 * 是否合法：必须的参数都进行了设置。 
	 */
	public boolean isLegal()
	{
		if (StringUtils.isExistNullOrEmpty(appId, gameId, cpId, gameName, packageName) || gameCreateTime == null)
			return false;
		return true;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getGameId()
	{
		return gameId;
	}

	public void setGameId(String gameId)
	{
		this.gameId = gameId;
	}

	public String getCpId()
	{
		return cpId;
	}

	public void setCpId(String cpId)
	{
		this.cpId = cpId;
	}

	public String getGameName()
	{
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public Long getGameCreateTime()
	{
		return gameCreateTime;
	}

	public void setGameCreateTime(Long gameCreateTime)
	{
		this.gameCreateTime = gameCreateTime;
	}
}