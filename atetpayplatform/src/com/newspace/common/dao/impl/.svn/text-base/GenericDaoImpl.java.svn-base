package com.newspace.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.newspace.common.dao.GenericDao;
import com.newspace.common.exception.DaoException;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.page.Page;
import com.newspace.common.page.PageResult;
import com.newspace.common.utils.ArrayUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.common.vo.BaseVo;
import com.sun.rowset.CachedRowSetImpl;

/**
 * 通用的Dao基类，实现了{@link com.aspire.common.dao.GenericDao}接口，提供操作数据库的各种方法
 * 
 * @author huqili
 * @since jdk1.6 hibernate3.0
 * @version 1.0
 * @param <E>
 *            VO类泛型参数
 */
@Transactional
public abstract class GenericDaoImpl<E extends BaseVo> extends HibernateDaoSupport implements GenericDao<E>
{
	/**
	 * VO类的Class实例
	 */
	protected Class<E> persistentClass;

	/**
	 * {@link com.newspace.common.log.JLogger}类实例
	 */
	protected JLogger logger = LoggerUtils.getLogger(getClass());

	/**
	 * 构造函数，通过获取子类中指定的泛型参数获取VO类的Class实例
	 */
	@SuppressWarnings( { "unchecked" })
	public GenericDaoImpl()
	{
		this.persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * 删除VO对应的记录
	 * 
	 * @param vo
	 *            VO类对象
	 * @return 没有返回值
	 * @throws DaoException
	 */
	public void delete(E vo) throws DaoException
	{
		Session session = null;
		try
		{
			session = getSession();
			session.delete(vo);
		}
		catch (Exception e)
		{
			throw new DaoException(1006, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	/**
	 * 将对象从hibernate的session中去除
	 * 
	 * @param vo
	 *            VO类实例
	 * @throws DaoException
	 */
	public void evict(E vo) throws DaoException
	{
		Session session = null;
		try
		{
			session = getSession();
			session.evict(vo);
		}
		catch (Exception e)
		{
			throw new DaoException(1000, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	/**
	 * 将指定ID对应的记录删除
	 * 
	 * @param id
	 *            记录的主键ID
	 * @return 没有返回值
	 * @throws DaoException
	 */
	public void delete(Serializable id) throws DaoException
	{
		E vo = get(id);
		delete(vo);
	}

	/**
	 * 使用hql语句查询数据库，并返回封装数据的VO类实例列表
	 * 
	 * @param hql
	 *            hql语句
	 * @return List<E>封装数据的VO类实例列表
	 * @throws DaoException
	 */
	@SuppressWarnings( { "unchecked" })
	public List<E> query(final String hql) throws DaoException
	{
		Session session = getSession();
		try
		{
			Query query = session.createQuery(hql);
			return query.list();
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings( { "unchecked" })
	public E get(Serializable id) throws DaoException
	{
		Session session = getSession();
		try
		{
			return (E) session.get(persistentClass, id);
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	/**
	 * 通过load的方式获取VO对象
	 * 
	 * @param id
	 *            对象ID
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public E load(Serializable id) throws DaoException
	{
		Session session = getSession();
		try
		{
			return (E) session.load(persistentClass, id);
		}
		catch (Exception e)
		{
			return null;
		}
		finally
		{
			releaseSession(session);
		}
	}

	@SuppressWarnings( { "unchecked" })
	public List<E> get(Serializable[] ids) throws DaoException
	{
		Session session = getSession();
		try
		{
			Criteria criteria = session.createCriteria(persistentClass).add(Restrictions.in("id", ids));
			return criteria.list();
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public List<E> getAll() throws DaoException
	{
		return getAll(null);
	}

	@SuppressWarnings( { "unchecked" })
	public List<E> getAll(Order order) throws DaoException
	{
		Session session = getSession();
		try
		{
			Criteria criteria = session.createCriteria(persistentClass);
			if (order != null)
			{
				criteria.addOrder(order);
			}
			return criteria.list();
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public Serializable insert(E vo) throws DaoException
	{
		Session session = getSession();
		try
		{
			Serializable id = session.save(vo);
			return id;
		}
		catch (Exception e)
		{
			throw new DaoException(1008, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public void merge(E vo) throws DaoException
	{
		Session session = getSession();
		try
		{
			session.merge(vo);
		}
		catch (Exception e)
		{
			throw new DaoException(1000, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	@SuppressWarnings( { "unchecked" })
	public List<E> query(DetachedCriteria criteria) throws DaoException
	{
		Session session = getSession();
		try
		{
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			Criteria c = criteria.getExecutableCriteria(session);
			return c.list();
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public void query(final PageResult page, final DetachedCriteria criteria) throws DaoException
	{
		Session session = getSession();
		try
		{
			Criteria c = criteria.getExecutableCriteria(session).setProjection(Projections.rowCount());
			Object t = c.uniqueResult();
			int total = 0;
			if (t != null)
			{
				total = Integer.parseInt(t.toString());
			}
			page.setRecordCount(total);
			if (total != 0)
			{
				int pageCount = total / page.getShowCount();
				if (total % page.getShowCount() != 0)
				{
					pageCount++;
				}
				if (page.getCurrentPage() > pageCount)
				{
					page.setCurrentPage(pageCount);
				}
				page.setPageCount(pageCount);
				c.setProjection(null).setFirstResult((page.getCurrentPage() - 1) * page.getShowCount()).setFetchSize(
						page.getShowCount()).setMaxResults(page.getShowCount()).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY);
				page.setVos(c.list());
			}
		}
		catch (DataAccessException e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public void query(PageResult page, int sqlCode) throws DaoException
	{

	}

	@SuppressWarnings( { "unchecked" })
	public List<E> query(final DetachedCriteria criteria, final int maxResult) throws DaoException
	{
		Session session = getSession();
		try
		{
			Criteria c = criteria.getExecutableCriteria(session).setResultTransformer(
					CriteriaSpecification.DISTINCT_ROOT_ENTITY).setMaxResults(maxResult);
			return c.list();
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public void update(E vo) throws DaoException
	{
		Session session = getSession();
		try
		{
			session.merge(vo);
		}
		catch (Exception e)
		{
			throw new DaoException(1004, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public int resultCount(final DetachedCriteria criteria) throws DaoException
	{
		Session session = getSession();
		try
		{
			Criteria c = criteria.getExecutableCriteria(session).setProjection(Projections.rowCount());
			Object object = c.uniqueResult();
			return Integer.parseInt(object.toString());
		}
		catch (Exception e)
		{
			throw new DaoException(1005, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	@SuppressWarnings("unchecked")
	public E uniqueResult(final DetachedCriteria criteria) throws DaoException
	{
		Session session = getSession();
		try
		{
			Criteria c = criteria.getExecutableCriteria(session);
			return (E) c.uniqueResult();
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public void query(PageResult page, E example) throws DaoException
	{
		Example e = Example.create(example);
		DetachedCriteria criteria = DetachedCriteria.forClass(example.getClass()).add(e);
		query(page, criteria);
	}

	@SuppressWarnings("unchecked")
	public List<E> query(E example) throws DaoException
	{
		Session session = getSession();
		try
		{
			Example ex = Example.create(example);
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(ex);
			return criteria.list();
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	public void execute(final String hql, final Object[] params) throws DaoException
	{
		try
		{
			getHibernateTemplate().bulkUpdate(hql, params);
		}
		catch (Exception e)
		{
			throw new DaoException(1010, e);
		}
	}

	/**
	 * 执行sql-query中定义的sql语句
	 * 
	 * @param sqlName
	 *            sql-query的name属性
	 * @param params
	 *            执行sql时的参数
	 * @throws DaoException
	 */
	public void executeNameSql(String sqlName, Object[] params) throws DaoException
	{
		Session session = null;
		try
		{
			session = getSession();
			Query query = session.getNamedQuery(sqlName);
			if (query != null)
			{
				String sql = query.getQueryString();
				query = session.createSQLQuery(sql);
				if (!ArrayUtils.isNullOrEmpty(params))
				{
					for (int i = 0; i < params.length; i++)
					{
						query.setParameter(i, params[i]);
					}
				}
				query.executeUpdate();
			}
		}
		catch (Exception e)
		{
			throw new DaoException(1024, new String[] { sqlName }, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	/**
	 * 获取sql.hbm.xml中定义的NameSql
	 * 
	 * @param sqlName
	 *            sql的名称
	 * @return
	 * @throws DaoException
	 */
	public String getNameSql(String sqlName) throws DaoException
	{
		Session session = null;
		try
		{
			session = getSession();
			Query query = session.getNamedQuery(sqlName);
			String temp = query.getQueryString();
			if (!StringUtils.isNullOrEmpty(temp))
			{
				temp = temp.trim();
			}
			return temp;
		}
		catch (Exception e)
		{
			throw new DaoException(1025, new String[] { sqlName }, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	/**
	 * 使用sql.hbm.xml中定影的NameSql查询
	 * 
	 * @param sqlName
	 *            sql的名称
	 * @param params
	 *            查询参数
	 * @return
	 * @throws DaoException
	 */
	public CachedRowSet queryByNameSql(String sqlName, Object[] params) throws DaoException
	{
		String sql = getNameSql(sqlName);
		return queryBySql(sql, params);
	}

	/**
	 * 使用sql查询
	 * 
	 * @param sqlName
	 *            sql的名称
	 * @param params
	 *            查询参数
	 * @return
	 * @throws DaoException
	 */
	public CachedRowSet queryBySql(String sql, Object[] params) throws DaoException
	{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try
		{
			conn = getJdbcConnection();
			stmt = conn.prepareStatement(sql);
			fillParams(stmt, params);
			rs = stmt.executeQuery();
			CachedRowSet rowSet = new CachedRowSetImpl();
			rowSet.populate(rs);
			return rowSet;
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseConnection(rs, stmt, conn);
		}
	}

	public Serializable saveOrUpdate(E vo) throws DaoException
	{
		Session session = null;
		try
		{
			Serializable id = null;
			session = getSession();
			try
			{
				session.saveOrUpdate(vo);
			}
			catch (Exception e)
			{
				session.merge(vo);
			}
			id = vo.getId();
			return id;
		}
		catch (Exception e)
		{
			throw new DaoException(1011, e);
		}
		finally
		{
			// releaseSession(session);
		}
	}

	public void saveOrUpdate(Collection<E> vos) throws DaoException
	{
		try
		{
			getHibernateTemplate().saveOrUpdateAll(vos);
		}
		catch (Exception e)
		{
			throw new DaoException(1011, e);
		}
	}

	public int[] executeBatch(String sql, Object[][] params) throws DaoException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = getJdbcConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++)
			{
				fillParams(ps, params[i]);
				ps.addBatch();
			}
			return ps.executeBatch();
		}
		catch (Exception e)
		{
			throw new DaoException(1000, e);
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.commit();
					conn.setAutoCommit(true);
				}
			}
			catch (SQLException e)
			{
				throw new DaoException(1000, e);
			}
			releaseConnection(null, ps, conn);
		}
	}

	/**
	 * 获取JDBC连接
	 * 
	 * @return
	 * @throws DaoException
	 */
	protected Connection getJdbcConnection() throws DaoException
	{
		try
		{
			SessionFactoryImplementor factory = (SessionFactoryImplementor) getSessionFactory();
			ConnectionProvider cp = factory.getConnectionProvider();
			return cp.getConnection();
		}
		catch (Exception e)
		{
			throw new DaoException(1014, e);
		}
	}

	/**
	 * 填充参数到PreparedStatement对象
	 * 
	 * @param ps
	 * @param params
	 * @throws SQLException
	 */
	private void fillParams(PreparedStatement ps, Object[] params) throws DaoException
	{
		if (params != null && params.length != 0)
		{
			for (int i = 0; i < params.length; i++)
			{
				try
				{
					if (params[i] == null)
					{
						ps.setNull(i + 1, Types.VARCHAR);
					}
					else
					{
						ps.setObject(i + 1, params[i]);
					}
				}
				catch (Exception e)
				{
					throw new DaoException(1016, e);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<E> queryByName(String queryName) throws DaoException
	{
		Session session = getSession();
		try
		{
			Query query = session.getNamedQuery(queryName);
			return query.list();
		}
		catch (Exception e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

	/**
	 * 关闭数据库操作的各种对象
	 * 
	 * @param rs
	 * @param stmt
	 * @param connection
	 * @throws Exception
	 */
	protected void releaseConnection(ResultSet rs, Statement stmt, Connection connection) throws DaoException
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
			if (stmt != null)
			{
				stmt.close();
			}
			if (connection != null)
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			throw new DaoException(1015, e);
		}

	}

	@SuppressWarnings( { "unchecked" })
	public void queryBySQLPage(final PageResult page, final String hql) throws DaoException
	{
		try
		{
			getHibernateTemplate().execute(new HibernateCallback()
			{

				public Object doInHibernate(final Session session) throws HibernateException, SQLException
				{
					SQLQuery query = session.createSQLQuery(hql);
					List<Object[]> t = query.list();
					int total = 0;
					if (t != null)
					{
						total = t.size();
					}
					page.setRecordCount(total);
					if (total != 0)
					{
						int pageCount = total / page.getShowCount();
						if (total % page.getShowCount() != 0)
						{
							pageCount++;
						}
						page.setPageCount(pageCount);

						query.setFirstResult((page.getCurrentPage() - 1) * page.getShowCount());
						query.setFetchSize(page.getShowCount());
						query.setMaxResults(page.getShowCount());
						List<Object[]> list = query.list();

						String strs[] = hql.replace("select", "").replace("distinct", "").substring(0,
								hql.replace("select", "").replace("distinct", "").indexOf("from")).split(",");
						List<Object> objList = new ArrayList<Object>();
						for (int i = 0; i < list.size(); i++)
						{
							try
							{
								Object[] obj = list.get(i);
								Object object = (Object) Class.forName(getPersistentClassVo().getName()).newInstance();
								for (int j = 0; j < strs.length; j++)
								{
									if (strs[j].trim().indexOf(".") != -1)
									{
										strs[j] = strs[j].trim().substring(strs[j].trim().indexOf(".") + 1).trim();
									}
									setObjAttrSetValue(object, strs[j].trim(), obj[j] + "");
								}
								objList.add(object);
							}
							catch (Exception e)
							{
								throw new DaoException(1015, e);
							}

						}
						page.setVos(objList);
					}

					return null;
				}
			});
		}
		catch (DataAccessException e)
		{
			throw new DaoException(1007, e);
		}
	}

	@SuppressWarnings( { "unchecked" })
	private Class<E> getPersistentClassVo()
	{
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private void setObjAttrSetValue(Object obj, String attr, Object value) throws Exception
	{
		Field field;
		try
		{
			field = obj.getClass().getDeclaredField(attr);
			field.setAccessible(true);// 关键。。。可访问私有变量。
			field.set(obj, value);

		}
		catch (Exception e)
		{
			throw e;
		}

	}

	@SuppressWarnings( { "unchecked" })
	public void queryBySQL(final List<Object> list, final String hql) throws DaoException
	{
		try
		{
			getHibernateTemplate().execute(new HibernateCallback()
			{

				public Object doInHibernate(final Session session) throws HibernateException, SQLException
				{
					SQLQuery query = session.createSQLQuery(hql);
					List<Object[]> lists = query.list();
					String strs[] = hql.replace("select", "").replace("distinct", "").substring(0,
							hql.replace("select", "").replace("distinct", "").indexOf("from")).split(",");
					for (int i = 0; i < lists.size(); i++)
					{
						try
						{
							Object[] obj = lists.get(i);
							Object object = (Object) Class.forName(getPersistentClassVo().getName()).newInstance();
							for (int j = 0; j < strs.length; j++)
							{
								if (strs[j].trim().indexOf(".") != -1)
								{
									strs[j] = strs[j].trim().substring(strs[j].trim().indexOf(".") + 1).trim();
								}
								setObjAttrSetValue(object, strs[j].trim(), obj[j] + "");
							}
							list.add(object);
						}
						catch (Exception e)
						{
							throw new DaoException(1024, new String[] { hql }, e);
						}

					}
					return list;
				}

			});
		}
		catch (DataAccessException e)
		{
			throw new DaoException(1007, e);
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @param params
	 *            执行sql时的参数
	 * @throws DaoException
	 */
	public void executeSql(String sql, Object[] params) throws DaoException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			sql = sql.toUpperCase();
			conn = getJdbcConnection();
			conn.setAutoCommit(Boolean.FALSE);
			stmt = conn.prepareStatement(sql);
			fillParams(stmt, params);
			stmt.executeUpdate();
			conn.commit();
		}
		catch (Exception e)
		{
			throw new DaoException(28001, new String[] { sql }, e);
		}
		finally
		{
			releaseConnection(null, stmt, conn);
		}
	}

	/**
	 * 获取序列值
	 * 
	 * @param seqName
	 * @throws DaoException
	 */
	public String getSeqValue(final String seqName) throws DaoException
	{
		String seqValue = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = getJdbcConnection();
			ps = conn.prepareStatement("select " + seqName.trim() + ".nextval as seq from dual");
			rs = ps.executeQuery();
			if (rs.next())
			{
				return rs.getString("seq");
			}
		}
		catch (Exception e)
		{
			throw new DaoException(1042, new String[] { seqName }, e);
		}
		finally
		{
			releaseConnection(rs, ps, conn);
		}
		return seqValue;
	}

	/**
	 * 执行存储过程语句
	 * 
	 * @param sql
	 * @throws DaoException
	 */
	public int executeProcedure(final String sql, final Object[] params) throws DaoException
	{
		Connection conn = null;
		CallableStatement stmt = null;
		try
		{
			conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			stmt = conn.prepareCall(sql);
			if (!ArrayUtils.isNullOrEmpty(params))
			{
				for (int i = 0; i < params.length; i++)
				{
					stmt.setObject(i, params[i]);
				}
			}
			return stmt.executeUpdate();
		}
		catch (Exception e)
		{
			throw new DaoException(1024, new String[] { sql }, e);
		}
		finally
		{
			releaseConnection(null, stmt, conn);
		}
	}

	public void query(final Page page, final DetachedCriteria criteria) throws DaoException
	{
		Session session = getSession();
		try
		{
			Criteria c = criteria.getExecutableCriteria(session).setProjection(Projections.rowCount());
			Object t = c.uniqueResult();
			int total = 0;
			if (t != null)
			{
				total = Integer.parseInt(t.toString());
			}
			page.setRecordCount(total);
			if (total != 0)
			{
				int pageCount = total / page.getShowCount();
				if (total % page.getShowCount() != 0)
				{
					pageCount++;
				}
				page.setPageCount(pageCount);
				c.setProjection(null).setFirstResult((page.getCurrentPage() - 1) * page.getShowCount()).setFetchSize(
						page.getShowCount()).setMaxResults(page.getShowCount()).setResultTransformer(
						Criteria.DISTINCT_ROOT_ENTITY);
				page.setVos(c.list());
			}
		}
		catch (DataAccessException e)
		{
			throw new DaoException(1007, e);
		}
		finally
		{
			releaseSession(session);
		}
	}

}
