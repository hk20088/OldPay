package com.newspace.common.bo.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.bo.GenericBo;
import com.newspace.common.dao.GenericDao;
import com.newspace.common.exception.BoException;
import com.newspace.common.exception.DaoException;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.page.Page;
import com.newspace.common.page.PageResult;
import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.threadlocal.ThreadLocalUtils;
import com.newspace.common.utils.ArrayUtils;
import com.newspace.common.vo.BaseVo;

/**
 * 通用的Bo基类
 * @author huqili
 * @since jdk1.6
 * @version 1.0
 * @see {@link com.aspire.common.dao.GenericDao}
 * @see com.aspire.common.vo.BaseVo
 * @see com.newspace.common.spring.SpringBeanUtils
 * @param <E> BO类操作的VO类泛型参数
 * @param <T> 操作数据库的Dao类泛型参数
 */
public abstract class GenericBoImpl<E extends BaseVo, T extends GenericDao<E>> implements GenericBo<E, T>
{
	/**
	 * 用于处理数据操作的Dao对象，其类型有泛型参数指定
	 * 此Dao对象由Spring进行实例化
	 */
	private T dao;

	/**
	 * Bo类操作的Vo的Class对象
	 */
	protected Class<E> persistentClass;

	/**
	 * 用于操作数据库的Vo类的Class对象
	 */
	protected Class<T> daoClass;

	/**
	 * 用于记录日志的JLogger实例
	 * @see com.aspire.common.log.JLogger
	 */
	protected JLogger logger = LoggerUtils.getLogger(getClass());

	/**
	 * 构造方法
	 * 此构造方法中包含获取泛型参数所指定的Vo和Dao的Class对象的操作
	 */
	@SuppressWarnings("unchecked")
	public GenericBoImpl()
	{
		try
		{
			String className = getClass().getSimpleName();
			if (!(className.startsWith("DefaultBoImpl")) && !className.contains("$$"))
			{
				this.persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
				this.daoClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
			}
		}
		catch (Exception e)
		{
			logger.error("Bo初始化泛型参数所指定的Vo和Dao时发生错误！", e);
		}
	}

	/**
	 * 获取首行记录
	 * @return
	 * @throws BoException
	 */
	public E getFirst() throws BoException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		List<E> vos = query(criteria, 1);
		return ArrayUtils.isNullOrEmpty(vos) ? null : vos.get(0);
	}

	/**
	 * 删除Vo
	 * @param vo 需要删除的VO实例
	 * @throws BoException
	 */
	public void delete(E vo) throws BoException
	{
		try
		{
			getDao().delete(vo);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 删除指定ID对应的记录
	 * @param id 需要删除的ID
	 * @throws BoException
	 */
	public void delete(Serializable id) throws BoException
	{
		try
		{
			getDao().delete(id);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 删除可由DetachedCriteria对象查询获得的所有适配的记录
	 * @param criteria DetachedCriteria对象
	 * @return String[] 被删除的记录的ID数组
	 * @throws BoException
	 */
	public String[] delete(DetachedCriteria criteria) throws BoException
	{
		try
		{
			List<E> vos = getDao().query(criteria);
			String[] ids = null;
			if (vos != null)
			{
				ids = new String[vos.size()];
				for (int i = 0; i < vos.size(); i++)
				{
					ids[i] = vos.get(i).getId();
				}
				delete(ids);
			}
			return ids;
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 获取ID对应的记录，并返回封装此记录数据的VO实例
	 * @param id 记录的ID
	 * @return 封装数据的VO类实例
	 * @throws BoException
	 */
	public E get(Serializable id) throws BoException
	{
		try
		{
			return getDao().get(id);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 获取指定ID数组对应的所有记录，并返回封装这些记录的VO类实例的List
	 * @param ids ID数组
	 * @return List<E> 封装数据的VO类实例列表
	 * @throws BoException
	 */
	public List<E> get(Serializable[] ids) throws BoException
	{
		try
		{
			return getDao().get(ids);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 获取表中所有记录，并返回封装这些记录的VO类实例列表
	 * @return List<E> 封装记录的VO类实例列表
	 * @throws BoException
	 */
	public List<E> getAll() throws BoException
	{
		try
		{
			return getDao().getAll();
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 获取表中所有根据Order对象进行排序的记录，并返回封装这些记录的VO类实例列表
	 * @param order 
	 * @return List<E>
	 */
	public List<E> getAll(Order order) throws BoException
	{
		try
		{
			return getDao().getAll(order);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 将VO类实例所封装的数据插入数据库
	 * @param vo 封装数据的VO类实例
	 * @return Serializable 新记录的ID
	 * @throws BoException
	 */
	public Serializable insert(E vo) throws BoException
	{
		try
		{
			return getDao().insert(vo);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * merge操作
	 * @param vo封装数据的VO类实例
	 * @throws BoException
	 */
	public void merge(E vo) throws BoException
	{
		try
		{
			getDao().merge(vo);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 根据DetachedCriteria对象查询数据库，并返回封装数据的VO列表
	 * @param criteria DetachedCriteria对象
	 * @return List<E>封装数据的VO列表
	 * @throws BoException
	 */
	public List<E> query(DetachedCriteria criteria) throws BoException
	{
		try
		{
			return getDao().query(criteria);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 根据PageResult和DetachedCriteria对象分页查询数据库，将查询结果封装到PageResult对象中
	 * @param page PageResult对象
	 * @see com.aspire.common.page.PageResult
	 * @param criteria DetachedCriteria对象
	 */
	public void query(PageResult page, DetachedCriteria criteria) throws BoException
	{
		try
		{
			getDao().query(page, criteria);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 根据Page和DetachedCriteria对象分页查询数据库，将查询结果封装到Page对象中
	 * @param page Page对象
	 * @see com.aspire.common.page.Page
	 * @param criteria DetachedCriteria对象
	 */
	public void query(Page page, DetachedCriteria criteria) throws BoException
	{
		try
		{
			getDao().query(page, criteria);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 根据PageResult和sqlCode分页查询数据库，将查询结果封装到PageResult对象中
	 * @param page PageResult对象
	 * @see com.aspire.common.page.PageResult
	 * @param sqlCode 
	 */
	public void query(PageResult page, int sqlCode) throws BoException
	{
		try
		{
			getDao().query(page, sqlCode);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 通过hibernae 的hql语句查询数据库并返回封装数据的VO列表
	 * @param hql语句
	 * @return List<E>封装数据的VO列表
	 * @throws BoException
	 */
	public List<E> query(String hql) throws BoException
	{

		try
		{
			return getDao().query(hql);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 获取操作数据库的Dao对象<br/>
	 * 此Dao对象须注册于Spring的context中，通过{@link com.newspace.common.spring.SpringBeanUtils}获取
	 * @return dao类实例
	 */
	protected T getDao()
	{
		if (dao == null)
		{
			dao = SpringBeanUtils.getBean(daoClass);
		}
		return dao;
	}

	public List<E> query(DetachedCriteria criteria, int maxResult) throws BoException
	{
		try
		{
			return getDao().query(criteria, maxResult);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	public void update(E vo) throws BoException
	{
		try
		{
			getDao().update(vo);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	public int resultCount(DetachedCriteria criteria) throws BoException
	{
		try
		{
			return getDao().resultCount(criteria);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	public E uniqueResult(DetachedCriteria criteria) throws BoException
	{
		try
		{
			return getDao().uniqueResult(criteria);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	public Serializable saveOrUpdate(E vo) throws BoException
	{
		try
		{
			return getDao().saveOrUpdate(vo);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	public void query(PageResult page, E example) throws BoException
	{
		try
		{
			getDao().query(page, example);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	public List<E> query(E example) throws BoException
	{
		try
		{
			return getDao().query(example);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	public void delete(Serializable[] ids) throws BoException
	{
		if (!ArrayUtils.isNullOrEmpty(ids))
		{
			StringBuffer paramStr = new StringBuffer();
			for (int i = 0; i < ids.length; i++)
			{
				paramStr.append("?,");
			}
			paramStr.replace(paramStr.length() - 1, paramStr.length(), "");
			paramStr.insert(0, "(");
			paramStr.append(")");
			String hql = "delete " + persistentClass.getSimpleName() + " where id in " + paramStr.toString();
			try
			{
				getDao().execute(hql, ids);
			}
			catch (DaoException e)
			{
				throw new BoException(e);
			}
		}
	}

	public void saveOrUpdate(Collection<E> vos) throws BoException
	{
		if (!ArrayUtils.isNullOrEmpty(vos))
		{
			try
			{
				getDao().saveOrUpdate(vos);
			}
			catch (DaoException e)
			{
				throw new BoException(e);
			}
		}
	}

	public List<E> queryByName(String queryName) throws BoException
	{
		try
		{
			return getDao().queryByName(queryName);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	// /**
	// * 执行sql-query中定义的sql语句
	// * @param sqlName sql-query的name属性
	// * @param params 执行sql时的参数
	// * @throws DaoException
	// */
	// public void executeNameSql(String sqlName,Object[]params)throws
	// BoException
	// {
	// try
	// {
	// getDao().executeNameSql(sqlName, params);
	// }
	// catch (DaoException e)
	// {
	// throw new BoException(1024,new String[]{sqlName},e);
	// }
	// }

	public void queryBySQLPage(final PageResult page, final String hql) throws BoException
	{
		try
		{
			getDao().queryBySQLPage(page, hql);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	public void queryBySQL(final List list, final String hql) throws BoException
	{
		try
		{
			getDao().queryBySQL(list, hql);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 执行sql语句
	 * @param sql
	 * @param params 执行sql时的参数
	 * @throws DaoException
	 */
	public void excuteSql(final String sql, final Object[] params) throws BoException
	{
		try
		{
			getDao().executeSql(sql, params);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 获取序列值
	 * @param seqName
	 * @throws DaoException
	 */
	public String getSeqValue(final String seqName) throws BoException
	{
		try
		{
			return getDao().getSeqValue(seqName);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}

	}

	/**
	 * 获取sql.hbm.xml中定义的NameSql
	 * @param sqlName sql的名称
	 * @return
	 * @throws DaoException
	 */
	public String getNameSql(String sqlName) throws BoException
	{
		try
		{
			return getDao().getNameSql(sqlName);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 使用sql.hbm.xml中定影的NameSql查询
	 * @param sqlName sql的名称
	 * @param params 查询参数
	 * @return
	 * @throws DaoException
	 */
	protected CachedRowSet queryByNameSql(String sqlName, Object[] params) throws BoException
	{
		try
		{
			return getDao().queryByNameSql(sqlName, params);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 取出请求参数
	 * @param paramName 参数名称
	 * @param index 参数的下标
	 * @return
	 * @throws BoException
	 */
	protected String getRequestParams(String paramName, int index) throws BoException
	{
		try
		{
			logger.debug(ThreadLocalUtils.getRequest().getParameterMap().keySet());
			String[] arr = ThreadLocalUtils.getRequestParameters(paramName);
			return arr[index];
		}
		catch (Exception e)
		{
			throw new BoException(1041, e);
		}
	}

	/**
	 * 执行存储过程语句
	 * @param sql
	 * @throws DaoException
	 */
	public void executeProcedure(final String sql, final Object[] params) throws BoException
	{
		try
		{
			getDao().executeProcedure(sql, params);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	/**
	 * 校验某个字段的值是否已经存在
	 * @param fieldName 字段名
	 * @param fieldValue 字段值 
	 * @return true 存在; false 不存在
	 */
	public boolean isFieldValueExist(String fieldName, Object fieldValue) throws BoException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(this.persistentClass);
		criteria.add(Restrictions.eq(fieldName, fieldValue));
		List<E> list = query(criteria);
		return list.size() > 0;
	}

	public void executeNameSql(String sqlName, Object[] params) throws BoException
	{
		try
		{
			getDao().executeNameSql(sqlName, params);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}

	public void executeSql(String sql, Object[] params) throws BoException
	{
		try
		{
			getDao().executeSql(sql, params);
		}
		catch (DaoException e)
		{
			throw new BoException(e);
		}
	}
}
