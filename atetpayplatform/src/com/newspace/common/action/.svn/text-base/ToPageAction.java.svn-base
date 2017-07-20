package com.newspace.common.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.newspace.common.threadlocal.ThreadLocalUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 实现跳转的action，跳转到指定的页面
 * 访问uri为toPage{1}_{2}_{3}，
 * {1}指定是否重定向（R表示重定向，空表示不重定向）；
 * {2}指定目标页面在/WEB-INF/pages/（不重定向）或/pages/（重定向）目录下的目录名称
 * {3}指定页面的文件名
 * @author huqili
 *
 */
@Controller("toPageAction")
@Scope("request")
public class ToPageAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private Map<?, ?> params;

	/**
	 * 跳转到指定页面（非重定向）
	 * @return
	 */
	public String toPage()
	{
		fillParams();
		return SUCCESS;
	}

	/**
	 * 跳转到指定页面（重定向）
	 * @return
	 */
	public String toPageR()
	{
		fillParams();
		return "rec_success";
	}

	public void fillParams()
	{
		HttpServletRequest request = ThreadLocalUtils.getRequest();
		params = request.getParameterMap();
	}

	public Map<?, ?> getParams()
	{
		return params;
	}
}
