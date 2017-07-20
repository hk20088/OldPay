package com.newspace.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.newspace.common.exception.BoException;
import com.newspace.common.exception.DaoException;
import com.newspace.common.page.Page;
import com.newspace.common.page.PageResult;
import com.newspace.common.vo.BaseVo;

/**
 * 泛型Dao基础接口，此接口包含各种通过Hibernate框架对数据库表进行增、删、改、查等操作的方法
 * @author huqili
 * @since jdk1.6 hibernate3.0
 * @version 1.0
 * @param <E> vo类泛型参数
 */
public interface GenericDao<E extends BaseVo>
{   
    /**
     * 根据ID从数据查询记录并封装成对应的Vo类对象
     * @param id 对象ID
     * @return E vo类对象
     * @throws BoException
     */
    E get(Serializable id)throws DaoException;
    /**
     * 通过load的方式获取VO对象
     * @param id 对象ID
     * @return E vo类对象
     * @throws DaoException
     */
    E load(Serializable id)throws DaoException;
    /**
     * 根据ID集合获取Vo类对象列表
     * @param ids ID集合
     * @return List<E> vo类对象列表
     * @throws BoException
     */
    List<E> get(Serializable[]ids)throws DaoException;
    /**
     * 获取所有记录对应的vo类对象列表
     * @return List<E> 所有的vo类对象
     * @throws BoException
     */
    List<E> getAll()throws DaoException;
    /**
     * 获取所有记录对应的vo类对象列表
     * @param order 指定排序参数
     * @return List<E> 所有的vo类对象
     * @throws BoException
     */
    List<E> getAll(Order order)throws DaoException;
    /**
     * 根据DetachedCriteria对象查询vo类对象列表
     * @param criteria 封装查询条件的DetachedCriteria对象
     * @return List<E> vo类对象列表
     * @throws BoException
     */
    List<E> query(DetachedCriteria criteria)throws DaoException;
    /**
     * 根据DetachedCriteria对象查询maxResult指定数量的vo类对象列表
     * @param criteria 封装查询条件的DetachedCriteria对象
     * @param maxResult 返回结果的最大数量
     * @return List<E> vo类对象列表
     * @throws BoException
     */
    List<E> query(DetachedCriteria criteria,int maxResult)throws DaoException;
    /**
     * 根据PageResult和DetachedCriteria对象进行分页查询，并将查询结果封装到PageResult对象中
     * @param page 进行分页查询的PageResult对象
     * @param criteria 封装查询条件的DetachedCriteria对象
     * @return 没有返回值
     * @throws BoException
     */
    void query(PageResult page,DetachedCriteria criteria)throws DaoException;
    /**
     * 根据PageResult和example进行分页查询，并将查询结果封装到PageResult对象中
     * @param page
     * @param example
     * @return 没有返回值
     * @throws DaoException
     */
    void query(PageResult page,E example)throws DaoException;
    /**
     * 根据example进行查询(QBE：Example类允许你通过一个给定实例 构建一个条件查询，
     * 版本属性、标识符和关联被忽略。默认情况下值为null的属性将被排除。)
     * @param example
     * @return vo类对象列表
     * @throws DaoException
     */
    List<E> query(E example)throws DaoException;
    /**
     * 根据PageResult和sql语句代码进行分页查询，并将查询结果封装到PageResult对象中
     * @param page 进行分页查询的PageResult对象
     * @param sqlCode sql语句代码
     * @throws BoException
     */
    void query(PageResult page,int sqlCode)throws DaoException;
    /**
     * 将vo对象持久化
     * @param vo 需要持久化的vo类对象
     * @return Integer 插入记录后的ID
     * @throws BoException
     */
    Serializable insert(E vo)throws DaoException;
    /**
     * 更新数据库中的记录
     * @param vo vo类对象
     * @return 没有返回值
     * @throws BoException
     */
    void update(E vo)throws DaoException;
    /**
     * 从数据库记录中删除记录
     * @param vo 需要删除的vo类对象
     * @return 没有返回值
     * @throws BoException
     */
    void delete(E vo)throws DaoException;
    /**
     * 删除指定ID的记录
     * @param id 记录id
     * @return 没有返回值
     * @throws BoException
     */
    void delete(Serializable id)throws DaoException;
    /**
     * 根据hql语句查询记录
     * @param hql
     * @return List<E> vo类对象列表
     * @throws BoException
     */
    List<E> query(final String hql)throws DaoException;
    /**
     * 进行merge操作:有记录的话尽心更新操作，没有记录的话尽心插入操作
     * @param vo
     * @return 没有返回值
     * @throws BoException
     */
    void merge(E vo)throws DaoException;
    /**
     * 查询满足条件的记录数量
     * @param criteria
     * @return 满足条件的记录数量
     * @throws DaoException
     */
    int resultCount(final DetachedCriteria criteria)throws DaoException;
    /**
     * 根据DetaCriteria对象查询并返回唯一记录
     * @param criteria
     * @return vo类实例
     * @throws DaoException
     */
    E uniqueResult(DetachedCriteria criteria)throws DaoException;
    /**
     * 执行hql语句
     * @param hql hql语句
     * @param objs sql参数
     * @return 没有返回值
     * @throws DaoException
     */
    void execute(String hql,Object[]params)throws DaoException;
    /**
     * 执行sql-query中定义的sql语句
     * @param sqlName sql-query的name属性
     * @param params 执行sql时的参数
     * @return 没有返回值
     * @throws DaoException
     */
    void executeNameSql(String sqlName,Object[]params)throws DaoException;
    /**
     * 获取sql.hbm.xml中定义的NameSql
     * @param sqlName sql的名称
     * @return 与sqlName对应的sql
     * @throws DaoException
     */
    String getNameSql(String sqlName)throws DaoException;
    /**
     * 使用sql.hbm.xml中定义的NameSql查询
     * @param sqlName sql的名称
     * @param params 查询参数
     * @return CachedRowSet对象
     * @throws DaoException
     */
    CachedRowSet queryByNameSql(String sqlName,Object[]params)throws DaoException;
    /**
     * 使用sql查询
     * @param sqlName sql的名称
     * @param params 查询参数 
     * @return CachedRowSet对象
     * @throws DaoException
     */
    CachedRowSet queryBySql(String sql,Object[]params)throws DaoException;
    /**
     * 执行更新或插入的操作
     * @param vo vo类实例
     * @return 将数据插入数据库之后数据对应的主键ID
     * @throws DaoException
     */
    Serializable saveOrUpdate(E vo)throws DaoException;
    /**
     * 批量的插入或更新操作
     * @param vos vo类实例列表
     * @return 没有返回值
     * @throws DaoException
     */
    void saveOrUpdate(Collection<E> vos)throws DaoException;
    /**
     * 执行映射文件中指定的name query进行查询
     * @param queryName query的名称
     * @return 封装查询结果的VO类实例列表
     * @throws DaoException
     */
    List<E> queryByName(String queryName)throws DaoException;
    /**
     * 执行批量操作。使用jdbc executeBatch。
     * @param sql sql语句
     * @param params 参数数组
     * @return batch操作之后的返回值
     * @throws DaoException
     */
    int[] executeBatch(String sql,Object[][]params)throws DaoException;
    /**
     * 根据sql语句进行分页查询
     * @param page {@link com.newspace.common.page.PageResult}对象
     * @param sql 用于查询的sql语句
     * @return 没有返回值
     * @throws DaoException
     */
    public void queryBySQLPage(final PageResult page,final String sql) throws DaoException;
    /**
     * 使用sql语句查询数据库，并将查询结果封装成VO对象填充到指定列表中
     * @param list 用于填充查询结果的VO类列表
     * @param sql 用于查询的sql语句
     * @return 没有返回值
     * @throws DaoException
     */
	void queryBySQL(final List<Object> list,final String sql) throws DaoException;
    /**
     * 执行sql语句
     * @param sql 需要执行的sql语句
     * @param params 执行sql时的参数
     * @return 没有返回值
     * @throws DaoException
     */
    void executeSql(final String sql,final Object[]params)throws DaoException;
    /**
     * 获取获取数据库中指定SEQUENCE的值
     * @param seqName 序列名称
     * @return 序列值
     * @throws DaoException
     */
    String getSeqValue(final String seqName)throws DaoException;
    /**
     * 执行存储过程语句
     * @param sql  存储过程语句
     * @param params 执行存储过程时传入的参数
     * @return 执行结果
     * @throws DaoException
     */
    int executeProcedure(final String sql,final Object[]params)throws DaoException;
    /**
     * 将对象从hibernate的session中去除
     * @param vo
     * @throws DaoException
     */
    void evict(E vo)throws DaoException;
    
    /**
     * 根据Page和DetachedCriteria对象进行分页查询，并将查询结果封装到Page对象中
     * @param page 进行分页查询的Page对象
     * @param criteria 封装查询条件的DetachedCriteria对象
     * @return 没有返回值
     * @throws BoException
     */
    void query(Page page,DetachedCriteria criteria)throws DaoException;
}
