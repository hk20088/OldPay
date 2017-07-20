package com.newspace.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.bo.GenericBo;
import com.newspace.common.exception.BoException;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.page.PageResult;
import com.newspace.common.utils.ArrayUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.vo.BaseVo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * BaseAction.java 
 * 描 述:  通用的action基类
 * 		   内部包含集成所有BO类对象的BoFacade类对象  
 * 作 者:  huqili
 * 历 史： 2014-4-18 修改
 */
public abstract class BaseAction<E extends BaseVo> extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private String redirectURL;

	protected Class<E> voClass;

	/**
	 * 泛型参数指定的Vo类对象列表
	 */
	protected Collection<E> vos;

	/**
	 * 泛型参数指定的Vo类对象
	 */
	protected E vo;

	/**
	 * ajax返回的json格式的字符串
	 */
	protected String json;

	/**
	 * 日志对象
	 */
	protected JLogger logger = LoggerUtils.getLogger(getClass());

	/**
	 * 分页展示时每个分页展示的记录数
	 */
	protected final int PAGE_SIZE_MIN = 10;
	protected final int PAGE_SIZE_MEDIUM = 30;
	protected final int PAGE_SIZE_MAX = 60;

	/**
	 * 分页展示的当前页码
	 */
	protected int currentPage = -1;

	/**
	 * url
	 */
	private String url;

	/**
	 * PageResult对象
	 */
	protected PageResult page;

	/**
	 * 编辑类型 0.新建 1.编辑
	 */
	protected String editType;

	/**
	 * 时间查询时开始时间的后缀
	 */
	protected String startDateSuffix = " 00:00:00";

	/**
	 * 时间查询是结束时间的后缀
	 */
	protected String endDateSuffix = " 23:59:59";

	@SuppressWarnings("unchecked")
	public BaseAction()
	{
		if (!BaseAction.class.isAssignableFrom(getClass()))
		{
			return;
		}
		// 在构造函数中初始化封装请求参数的VO对象
		try
		{
			voClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			this.vo = (E) voClass.newInstance();
		}
		catch (Exception e)
		{
			logger.error("初始化请求参数的vo对象时发生错误！", e);
		}
	}

	/**
	 * 根据example获取criteria对象
	 * @param example 
	 * @param enableLike
	 * @param excludeNull
	 * @return
	 */
	protected <T extends BaseVo> DetachedCriteria getCriteriaByExample(T example, boolean enableLike, boolean excludeNone)
	{
		Example e = Example.create(example);
		if (enableLike)
		{
			e.enableLike(MatchMode.ANYWHERE);
		}
		if (excludeNone)
		{
			e.excludeNone();
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(example.getClass()).add(e);
		if (example.getId() != null && !"".equals(example.getId()))
		{
			if (enableLike)
			{
				criteria.add(Restrictions.like("id", example.getId(), MatchMode.ANYWHERE));
			}
			else
			{
				criteria.add(Restrictions.eq("id", example.getId()));
			}
		}
		return criteria;
	}

	/**
	 * 获取ValueStack对象
	 * @return
	 */
	protected ValueStack getStack()
	{
		return ActionContext.getContext().getValueStack();
	}

	/**
	 * 分页查询
	 * @param criteria 用于分页查询的Criteria对象
	 * @param bo 执行分页查询的Bo对象
	 * @param showCount 分页展示记录条数
	 * @return
	 * @throws BoException 
	 */
	protected void queryPage(DetachedCriteria criteria, GenericBo<?, ?> bo, int showCount) throws BoException
	{
		queryPage(criteria, bo, showCount, Boolean.FALSE);
	}

	/**
	 * 分页查询
	 * @param criteria 用于分页查询的Criteria对象
	 * @param bo 执行分页查询的Bo对象
	 * @param showCount 分页展示记录条数
	 * @param ingoreQueryString 是否忽略请求url中的请求参数
	 * @throws BoException
	 */
	protected void queryPage(DetachedCriteria criteria, GenericBo<?, ?> bo, int showCount, boolean ingoreQueryString) throws BoException
	{
		if (currentPage == -1)
		{
			currentPage = 1;
		}
		page = new PageResult(getStack(), showCount);
		page.setCurrentPage(currentPage);
		setUrl(ingoreQueryString);
		page.setUrl(url);
		bo.query(page, criteria);
	}

	/**
	 * 分页查询
	 * @param sql 分页查询的sql语句
	 * @param bo 执行查询的Bo对象
	 * @param showCount 分页展示行数
	 * @param ingoreQueryString 是否忽略url中带入的请求参数
	 * @return
	 * @throws BoException 
	 */
	protected void queryBySQLPage(String sql, GenericBo<?, ?> bo, int showCount) throws BoException
	{
		queryBySQLPage(sql, bo, showCount, Boolean.FALSE);
	}

	/**
	 * 分页查询
	 * @param sql 分页查询的sql语句
	 * @param bo 执行查询的Bo对象
	 * @param showCount 分页展示行数
	 * @param ingoreQueryString 是否忽略url中带入的请求参数
	 * @return
	 * @throws BoException 
	 */
	protected void queryBySQLPage(String sql, GenericBo<?, ?> bo, int showCount, boolean ingoreQueryString) throws BoException
	{
		if (currentPage == -1)
		{
			currentPage = 1;
		}
		page = new PageResult(getStack(), showCount);
		page.setCurrentPage(currentPage);
		setUrl(ingoreQueryString);
		page.setUrl(url);
		bo.queryBySQLPage(page, sql);
	}

	/**
	 * 分页查询
	 * @param vos 需要进行分页的VO对象列表
	 * @param showCount 分页展示记录条数
	 * @return
	 */
	protected void queryPage(List<?> vos, int showCount)
	{
		queryPage(vos, showCount, Boolean.FALSE);
	}

	/**
	 * 分页查询
	 * @param vos 需要进行分页的VO对象列表
	 * @param showCount 分页展示记录条数
	 * @param ingoreQueryString 是否忽略url中的请求参数
	 * @return
	 */
	protected void queryPage(List<?> vos, int showCount, boolean ingoreQueryString)
	{
		if (currentPage == -1)
		{
			currentPage = 1;
		}
		page = new PageResult(getStack(), showCount);
		page.setCurrentPage(currentPage);
		setUrl(ingoreQueryString);
		page.setUrl(url);
		page.setRecordCount(vos.size());
		int pageCount = vos.size() / showCount;
		if (vos.size() % showCount != 0)
		{
			pageCount++;
		}
		page.setPageCount(pageCount);
		List<?> subVos = null;
		if (!ArrayUtils.isNullOrEmpty(vos))
		{
			int start = (currentPage - 1) * showCount;
			int end = currentPage * showCount;
			end = end > vos.size() ? vos.size() : end;
			subVos = vos.subList(start, end);
		}
		page.setVos(subVos);
	}

	/**
	 * 添加等于查询条件到DetachedCriteria对象中
	 * @param criteria DetachedCriteria对象
	 * @param field 需要添加查询条件的对象
	 * @param value 查询的值
	 * @param excludeNull 是否忽略空值
	 * @return
	 */
	protected DetachedCriteria addEQ(DetachedCriteria criteria, String field, Object value, boolean excludeNull)
	{
		if (excludeNull && (value == null || value.equals("")))
		{
			return criteria;
		}
		criteria.add(Restrictions.eq(field, value));
		return criteria;
	}

	/**
	 * 添加Like查询条件到DetachedCriteria对象
	 * @param criteria DetachedCriteria对象
	 * @param field 需要添加查询条件的字段名称
	 * @param value 查询的值
	 * @param matchMode 匹配模式
	 * @param excludeNull 是否忽略空值
	 * @return
	 */
	protected DetachedCriteria addLike(DetachedCriteria criteria, String field, String value, MatchMode matchMode, boolean excludeNull)
	{
		if (excludeNull && (value == null || value.equals("")))
		{
			return criteria;
		}
		criteria.add(Restrictions.like(field, value, matchMode));
		return criteria;
	}

	/**
	 * 添加between查询条件到DetachedCriteria对象
	 * @param criteria DetachedCriteria对象
	 * @param field 需要添加查询条件的字段名称
	 * @param lower 下限
	 * @param upper 上限
	 * @return
	 */
	protected DetachedCriteria addBetween(DetachedCriteria criteria, String field, Object lower, Object upper)
	{
		if (lower != null && !"".equals(lower))
		{
			criteria.add(Restrictions.gt(field, lower));
		}
		if (upper != null && !"".equals(upper))
		{
			criteria.add(Restrictions.lt(field, upper));
		}
		return criteria;
	}

	/**
	 * 添加查询条件in 或like in（根据fuzzy判断）
	 * @param criteria DetachedCriteria对象
	 * @param fieldName 字段名称
	 * @param valueStr 以指定字符(;)分隔的参数值组成的字符串
	 * @param mode MatchMode
	 * @param excludeNull 是否忽略空值
	 * @param fuzzy 是否模糊查询
	 * @return
	 */
	protected DetachedCriteria addLikeIn(DetachedCriteria criteria, String fieldName, String valueStr, MatchMode mode, boolean excludeNull,
			boolean fuzzy)
	{
		if (!StringUtils.isNullOrEmpty(valueStr))
		{
			// valueStr = StringUtils.formatMultiParams(valueStr);
			String[] params = valueStr.split(";");
			if (fuzzy)
			{
				addLikeIn(criteria, fieldName, params, MatchMode.ANYWHERE, true);
			}
			else
			{
				criteria.add(Restrictions.in("requestId", params));
			}
		}
		return criteria;
	}

	/**
	 * @param criteria
	 * @param fieldName
	 * @param values
	 * @param mode
	 * @param excludeNull
	 * @return
	 */
	protected DetachedCriteria addLikeIn(DetachedCriteria criteria, String fieldName, String[] values, MatchMode mode, boolean excludeNull)
	{
		if (!ArrayUtils.isNullOrEmpty(values))
		{
			Criterion c = null;
			if (!StringUtils.isNullOrEmpty(values[0]) || !excludeNull)
			{
				c = Restrictions.like(fieldName, values[0], mode);
			}
			for (int i = 1; i < values.length; i++)
			{
				if (!StringUtils.isNullOrEmpty(values[i]) || !excludeNull)
				{
					c = Restrictions.or(c, Restrictions.like(fieldName, values[i], mode));
				}
			}
			if (c != null)
			{
				criteria.add(c);
			}
		}
		return criteria;
	}

	/**
	 * 以html的形式输出信息
	 * @param msg 需要输出的信息
	 */
	protected void outputMsg(String msg)
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try
		{
			PrintWriter writer = response.getWriter();
			writer.write(msg);
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			logger.error(e);
		}
	}

	/**
	 * 以JSON的形式输出信息
	 * @param msg 需要输出的信息
	 */
	protected void outputObj(Object msg)
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try
		{
			PrintWriter writer = response.getWriter();
			writer.print(msg);
			writer.flush();
			writer.close();
		}
		catch (IOException e)
		{
			logger.error(e);
		}
	}

	/**
	 * 向页面输出图片
	 * @param path
	 * @throws Exception
	 */
	protected void outputImg(String path) throws Exception
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("image/*");
		InputStream is = null;
		OutputStream os = null;
		try
		{
			is = new FileInputStream(new File(path));
			byte[] data = new byte[is.available()];
			is.read(data);
			is.close();
			os = response.getOutputStream();
			os.write(data);
			os.flush();
		}
		finally
		{
			if (is != null)
			{
				is.close();
			}
			if (os != null)
			{
				os.close();
			}
		}
	}

	/**
	 * 设置分页查询的URL
	 */
	private void setUrl(boolean ingoreQueryString)
	{
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		url = request.getRequestURI();
		String queryStr = request.getQueryString();
		if (!ingoreQueryString && queryStr != null)
		{
			queryStr = urlFilter(queryStr);
			url += "?" + queryStr;
		}
	}

	/**
	 * url字符过滤
	 * @param url
	 * @return
	 */
	protected String urlFilter(String url)
	{
		if (!StringUtils.isNullOrEmpty(url))
		{
			try
			{
				url = URLDecoder.decode(url, "utf-8");
			}
			catch (UnsupportedEncodingException e)
			{
				url = "";
			}
			url = url.replaceAll("[\\\\$%'\"()<>\\+\\n/,@\\r]", "");
			url = url.replaceAll("&", "&amp;");
		}
		return url;
	}

	/**
	 * 初始化编辑类型
	 * 当vo为空或vo的ID为空时，编辑类型为新建模式，否则为编辑模式
	 *
	 */
	protected void initEditType()
	{
		if (isEdit())
		{
			editType = "1";
		}
		else
		{
			editType = "0";
		}
	}

	/**
	 * 判断是否为编辑模式（vo和vo的id均不为null）
	 * @return
	 */
	protected boolean isEdit()
	{
		return vo != null && vo.getId() != null;
	}

	/**
	 * 从请求中获取参数，如果为null就返回空字符串""
	 * @param request http请求
	 * @param key 参数的关键字
	 * @return 参数值
	 */
	protected String getParameter(HttpServletRequest request, String key)
	{
		String value = request.getParameter(key);
		if (value == null)
		{
			value = "";
		}
		return value.trim();
	}

	public Collection<E> getVos()
	{

		return vos;
	}

	public void setVos(Collection<E> vos)
	{

		this.vos = vos;
	}

	public E getVo()
	{

		return vo;
	}

	public void setVo(E vo)
	{

		this.vo = vo;
	}

	@JSON(name = "json")
	public String getJson()
	{

		return json;
	}

	public void setJson(String json)
	{

		this.json = json;
	}

	public int getCurrentPage()
	{

		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{

		this.currentPage = currentPage;
	}

	public String getUrl()
	{

		return url;
	}

	public void setUrl(String url)
	{

		this.url = url;
	}

	public PageResult getPage()
	{

		return page;
	}

	public void setPage(PageResult page)
	{

		this.page = page;
	}

	public String getEditType()
	{

		return editType;
	}

	public void setEditType(String editType)
	{

		this.editType = editType;
	}

	/**
	 * 调用统一的方法，重定向到action或者JSP
	 * @return 
	 * @author aaron
	 */
	public String redirectAction(String redirect)
	{
		this.redirectURL = redirect;
		if (redirect.indexOf(".jsp") > 0)
		{// 重定向到jsp
			return "redirectJSP";
		}
		else
		{// 重定向到action
			return "redirect";
		}
	}

	public String getRedirectURL()
	{
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL)
	{
		this.redirectURL = redirectURL;
	}
}