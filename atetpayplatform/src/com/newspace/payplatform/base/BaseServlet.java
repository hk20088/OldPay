package com.newspace.payplatform.base;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.newspace.common.bo.GenericBo;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.PropertiesUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.vo.BaseVo;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.token.vo.TokenVo;

/**
 * BaseServlet.java 
 * 描 述:  接口Servlet的基类，封装通用方法  
 * 作 者:  liushuai
 * 历 史： 2014-4-22 创建
 */
public class BaseServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * 令牌在session中保存的key
	 */
	private static final String TOKEN_KEY = "p_request_token";

	/**
	 * 令牌的失效时间
	 */
	private static final long TOKEN_EXPIRED_TIME;

	/**
	 * 用于缓存每个Class对象关联的PropertyDescriptor[]的Map
	 */
	private static final Map<Class<? extends JsonVo>, PropertyDescriptor[]> cachedPropDescriptorMap = new HashMap<Class<? extends JsonVo>, PropertyDescriptor[]>();

	/**
	 * Object类所声明的方法集合
	 */
	private static final Set<Method> OBJECT_METHODS = new HashSet<Method>();

	protected transient JLogger logger = LoggerUtils.getLogger(getClass());

	static
	{
		Properties prop = PropertiesUtils.getProps("configuration");
		TOKEN_EXPIRED_TIME = Long.parseLong(prop.getProperty("token_expired_time"));
		OBJECT_METHODS.addAll(Arrays.asList(Object.class.getDeclaredMethods()));
	}

	/**
	 * 填充vo：将Request请求参数填充到ReqVo对象中
	 * @param HttpServletRequest Http请求对象
	 * @param JsonVo 要进行填充的JsonVo对象
	 * @return returnCode  操作状态码
	 */
	protected int padRequestVo(HttpServletRequest request, JsonVo vo)
	{
		int returnCode = ReturnCode.SUCCESS.getCode();
		String jsonStr = getStrFromRequest(request);
		if (!StringUtils.isNullOrEmpty(jsonStr))
		{
			returnCode = padRequestVo(jsonStr, vo);
		}
		else
		{
			returnCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		}
		return returnCode;
	}

	/**
	 * 填充vo：将json格式数据填充到ReqVo对象中
	 * @param json json数据
	 * @param JsonVo 要进行填充的JsonVo对象
	 */
	protected int padRequestVo(String json, JsonVo vo)
	{
		int retCode = ReturnCode.SUCCESS.getCode();
		JsonVo tmpVo = new Gson().fromJson(json, vo.getClass());

		Class<? extends JsonVo> clazz = vo.getClass();
		try
		{
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = cachedPropDescriptorMap.get(clazz);
			if (props == null)
			{
				props = beanInfo.getPropertyDescriptors();
				cachedPropDescriptorMap.put(clazz, props);
			}

			for (PropertyDescriptor prop : props)
			{
				if (!OBJECT_METHODS.contains(prop.getReadMethod()))
				{
					Method getMethod = prop.getReadMethod();
					if (getMethod != null)
					{
						Object value = getMethod.invoke(tmpVo, new Object[] {});
						if (value != null)
						{
							Method setMethod = prop.getWriteMethod();
							if (setMethod != null)
							{
								setMethod.invoke(vo, new Object[] { value });
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.error("解析json，填充ReqVo对象时出错！", e);
			retCode = ReturnCode.REQUEST_PARAM_ERROR.getCode();
		}
		return retCode;
	}

	/**
	 * 获得Bo对象
	 * @param boClass Bo类的class对象
	 * @return T Bo对象
	 */
	@SuppressWarnings("unchecked")
	protected <T extends GenericBo> T getBo(Class<T> boClass)
	{
		return SpringBeanUtils.getBean(boClass);
	}

	/**
	 * 获得DefaultBoImpl对象
	 * @param clazz  VoClass对象
	 * @return DefaultBoImpl 默认的Bo对象
	 */
	protected DefaultBoImpl getDefaultBo(Class<? extends BaseVo> clazz)
	{
		DefaultBoImpl defaultBo = getBo(DefaultBoImpl.class);
		defaultBo.setRelateVo(clazz);
		return defaultBo;
	}

	/**
	 * 通过HttpServletResponse输出响应结果
	 * @param response Http响应
	 * @param vo 响应JsonVo类
	 */
	protected void outputResult(HttpServletResponse response, JsonVo vo)
	{
		String json = new Gson().toJson(vo);
		outputResult(response, json);
	}

	/**
	 * 重载方法：通过HttpServletResponse输出响应结果
	 * @param response Http响应
	 * @param json 要输出的json字符串
	 */
	protected void outputResult(HttpServletResponse response, String json)
	{
		try
		{
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.write(json);
			out.flush();
			out.close();
			logger.info(String.format("\r\n【响应结果: %s】", json));
		}
		catch (IOException e)
		{
			logger.error("发送响应结果失败！", e);
		}
	}

	/**
	 * 从HttpServletRequest中读取数据字符串。
	 */
	protected String getStrFromRequest(HttpServletRequest request)
	{
		String str = null;
		BufferedReader reader = null;
		try
		{
			if (request.getContentLength() > 0)
			{
				reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
				StringBuilder sb = new StringBuilder();
				while ((str = reader.readLine()) != null)
				{
					sb.append(str);
				}
				str = sb.toString();
				logger.info(String.format("\r\n【 接收到请求数据字符串：%s】", str));
			}
		}
		catch (IOException e)
		{
			logger.error("从HttpServletRequest中读取数据字符串失败！", e);
			str = null;
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					logger.error("输入流BufferedReader关闭时发生错！", e);
				}
			}
		}
		return str;
	}

	/**
	 * 从HttpServletRequest中读取参数并放入Map中
	 * Map的key为属性名，value为属性值
	 * @return Map<String, String>
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, String> generateParamMap(HttpServletRequest request)
	{
		// 将接收到通知消息的参数，使用key-value的形式保存到Map
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
		{
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			StringBuilder valueStr = new StringBuilder();
			for (int i = 0; i < values.length; i++)
				valueStr.append((i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",");
			params.put(name, valueStr.toString());
			logger.info(String.format("\r\n【解析出参数：%s，值：%s】", name, valueStr.toString()));
		}
		return params;
	}

	/**
	 * 创建令牌
	 * 
	 * @param req
	 */
	protected String createToken(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		synchronized (session)
		{
			TokenVo vo = new TokenVo();
			String token = UUID.randomUUID().toString();
			vo.setToken(token);
			vo.setCreateTime(System.currentTimeMillis());
			session.setAttribute(TOKEN_KEY, (Serializable) vo);
			return vo.getToken();
		}
	}

	/**
	 * 校验令牌
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	protected String validateToken(HttpServletRequest req)
	{
		try
		{
			HttpSession session = req.getSession();
			synchronized (session)
			{
				Object object = session.getAttribute(TOKEN_KEY);
				if (object == null)
				{
					throw new Exception("token does not exists...");
				}
				session.removeAttribute(TOKEN_KEY);
				TokenVo vo = (TokenVo) object;
				if ((System.currentTimeMillis() - vo.getCreateTime()) > TOKEN_EXPIRED_TIME)
				{
					throw new Exception("token has expired...");
				}
				return vo.getToken();
			}
		}
		catch (Exception e)
		{
			logger.error("", e);
			return null;
		}
	}
}