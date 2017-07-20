package com.newspace.msgclient.entity;

import java.util.Date;

import com.newspace.common.vo.BaseVo;

/**
 * MsgEntity.java 
 * 描 述:  短信息实体类  
 * 作 者:  liushuai
 * 历 史： 2014-9-19 创建
 */
public class MsgEntity extends BaseVo
{
	private static final long serialVersionUID = 1L;

	/**
	 * 手机号
	 */
	private String telephone;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 状态，0：未发送，1：已发送
	 */
	private int state;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 项目编号
	 */
	private int projectCode;

	/**
	 * 未发送
	 */
	public static final int STATE_NON_SEND = 0;

	/**
	 * 已发送
	 */
	public static final int STATE_SENDED = 1;

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public int getProjectCode()
	{
		return projectCode;
	}

	public void setProjectCode(int projectCode)
	{
		this.projectCode = projectCode;
	}

	@Override
	public String logString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{id : ").append(id);
		sb.append(", telephone: ").append(telephone);
		sb.append(", state: ").append(state).append("}");
		return sb.toString();
	}
}