package com.newspace.common.bo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.newspace.common.dao.GenericDao;
import com.newspace.common.exception.BoException;
import com.newspace.common.exception.DaoException;
import com.newspace.common.page.PageResult;
import com.newspace.common.vo.BaseVo;

/**
 * 泛型Bo基础接口
 * @author huqili
 *
 * @param <E> Vo类的泛型参数
 * @param <T> Dao类的泛型参数
 */
public interface GenericBo<E extends BaseVo, T extends GenericDao<E>>
{
	/**
	 * 获取首行记录
	 * @return
	 * @throws BoException
	 */
	E getFirst() throws BoException;

	/**
	 * 根据ID从数据查询记录并封装成对应的Vo类对象
	 * @param id 对象ID
	 * @return E vo类对象
	 * @throws BoException
	 */
	E get(Serializable id) throws BoException;

	/**
	 * 根据ID集合获取Vo类对象列表
	 * @param ids ID集合
	 * @return List<E> vo类对象列表
	 * @throws BoException
	 */
	List<E> get(Serializable[] ids) throws BoException;

	/**
	 * 获取所有记录对应的vo类对象列表
	 * @return List<E> 所有的vo类对象
	 * @throws BoException
	 */
	List<E> getAll() throws BoException;

	/**
	 * 获取所有记录对应的vo类对象列表
	 * @param order 指定排序参数
	 * @return List<E> 所有的vo类对象
	 * @throws BoException
	 */
	List<E> getAll(Order order) throws BoException;

	/**
	 * 根据DetachedCriteria对象查询vo类对象列表
	 * @param criteria 封装查询条件的DetachedCriteria对象
	 * @return List<E> vo类对象列表
	 * @throws BoException
	 */
	List<E> query(DetachedCriteria criteria) throws BoException;

	/**
	 * 根据DetachedCriteria对象查询maxResult指定数量的vo类对象列表
	 * @param criteria 封装查询条件的DetachedCriteria对象
	 * @param maxResult 返回结果的最大数量
	 * @return List<E> vo类对象列表
	 * @throws BoException
	 */
	List<E> query(DetachedCriteria criteria, int maxResult) throws BoException;

	/**
	 * 根据PageResult和DetachedCriteria对象进行分页查询，并将查询结果封装到PageResult对象中
	 * @param page 进行分页查询的PageResult对象
	 * @param criteria 封装查询条件的DetachedCriteria对象
	 * @throws BoException
	 */
	void query(PageResult page, DetachedCriteria criteria) throws BoException;

	/**
	 * QBE分页查询
	 * @param page
	 * @param example
	 * @throws BoException
	 */
	void query(PageResult page, E example) throws BoException;

	// /**
	// * 根据sql语句的代码进行查询
	// * @param sqlCode sql语句代码
	// * @return List<E> 符合条件的vo对象列表
	// * @throws BoException
	// */
	// List<E> query(int sqlCode)throws BoException;
	/**
	 * 根据example进行查询(QBE)
	 * @param vo
	 * @return
	 * @throws BoException
	 */
	List<E> query(E example) throws BoException;

	/**
	 * 根据PageResult和sql语句代码进行分页查询，并将查询结果封装到PageResult对象中
	 * @param page 进行分页查询的PageResult对象
	 * @param sqlCode sql语句代码
	 * @throws BoException
	 */
	void query(PageResult page, int sqlCode) throws BoException;

	/**
	 * 将vo对象持久化
	 * @param vo 需要持久化的vo类对象
	 * @return Integer 插入记录后的ID
	 * @throws BoException
	 */
	Serializable insert(E vo) throws BoException;

	/**
	 * 更新数据库中的记录
	 * @param vo vo类对象
	 * @throws BoException
	 */
	void update(E vo) throws BoException;

	/**
	 * 从数据库记录中删除记录
	 * @param vo 需要删除的vo类对象
	 * @throws BoException
	 */
	void delete(E vo) throws BoException;

	/**
	 * 删除指定ID的记录
	 * @param id 记录id
	 * @throws BoException
	 */
	void delete(Serializable id) throws BoException;

	/**
	 * 删除ID数组中所有ID对应的记录
	 * @param ids
	 * @throws DaoException
	 */
	void delete(Serializable[] ids) throws BoException;

	/**
	 * 根据DetachedCriteria对象查询记录并将符合条件的记录删除
	 * @param criteria DetachedCriteria类对象
	 * @throws BoException
	 */
	String[] delete(DetachedCriteria criteria) throws BoException;

	/**
	 * 根据hql语句查询记录
	 * @param hql
	 * @return List<E> vo类对象列表
	 * @throws BoException
	 */
	List<E> query(final String hql) throws BoException;

	/**
	 * 进行merge操作
	 * @param vo
	 * @throws BoException
	 */
	void merge(E vo) throws BoException;

	/**
	 * 查询满足条件的记录数量
	 * @param criteria
	 * @return
	 * @throws DaoException
	 */
	int resultCount(final DetachedCriteria criteria) throws BoException;

	/**
	 * 根据DetaCriteria对象查询并返回唯一记录
	 * @param criteria
	 * @return
	 * @throws BoException
	 */
	E uniqueResult(DetachedCriteria criteria) throws BoException;

	/**
	 * 插入或更新对象（根据ID进行判断，id为null进行插入，否则进行更新）
	 * @param vo
	 * @return
	 * @throws BoException
	 */
	Serializable saveOrUpdate(E vo) throws BoException;

	/**
	 * 批量插入或更新对象
	 * @param vos
	 * @throws BoException
	 */
	void saveOrUpdate(Collection<E> vos) throws BoException;

	/**
	 * 通过hibernate映射文件中配置的name query进行查询
	 * @param queryName
	 * @return
	 * @throws BoException
	 */
	List<E> queryByName(String queryName) throws BoException;

	/**
	 * 执行sql-query中定义的sql语句
	 * @param sqlName sql-query的name属性
	 * @param params 执行sql时的参数
	 * @throws DaoException
	 */
	void executeNameSql(String sqlName, Object[] params) throws BoException;

	/**
	 * 根据sql语句进行分页查询
	 * @param page {@link com.shixun.common.page.PageResult}对象
	 * @param sql 执行分页查询的sql语句
	 * @throws BoException
	 */
	public void queryBySQLPage(final PageResult page, final String sql) throws BoException;

	/**
	 * 根据sql查询数据
	 * @param list 封装查询结果的列表
	 * @param sql  查询sql语句
	 * @throws BoException
	 */
	@SuppressWarnings("rawtypes")
	public void queryBySQL(final List list, final String sql) throws BoException;

	/**
	 * 执行sql语句
	 * @param sql
	 * @param params 执行sql时的参数
	 * @throws DaoException
	 */
	public void executeSql(final String sql, final Object[] params) throws BoException;

	/**
	 * 获取序列值
	 * @param seqName
	 * @throws DaoException
	 */
	public String getSeqValue(final String seqName) throws BoException;

	/**
	 * 执行存储过程语句
	 * @param sql
	 * @throws DaoException
	 */
	public void executeProcedure(final String sql, final Object[] params) throws BoException;

	/**
	 * 获取sql.hbm.xml中定义的NameSql
	 * @param sqlName sql的名称
	 * @return
	 * @throws DaoException
	 */
	public String getNameSql(String sqlName) throws BoException;

	/**
	 * 校验某个字段的值是否已经存在
	 * @param fieldName 字段名
	 * @param fieldValue 字段值
	 * @return  true 存在; false 不存在
	 */
	public boolean isFieldValueExist(String fieldName, Object fieldValue) throws Exception;
}
