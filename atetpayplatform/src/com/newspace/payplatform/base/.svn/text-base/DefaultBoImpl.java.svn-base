package com.newspace.payplatform.base;

import com.newspace.common.bo.impl.GenericBoImpl;
import com.newspace.common.dao.GenericDao;
import com.newspace.common.vo.BaseVo;

/**
 * DefaultBoImpl.java 
 * 描 述:  默认的Bo实现类。<br/>
 * 		   在没有自定义的Bo方法的时候，可以使用该类，避免建立空的Bo Dao类。<br/>
 * 		   获得DefaultBoImpl对象之后，要调用setRelateVo方法，设置和Bo相关联的Vo类。<br/>
 * 		   [注意：如果自定义了Bo类，但是Dao类却无需创建新类，此时建议还按照之前的方式，建空的Dao类]	
 * 作 者:  liushuai
 * 历 史： 2014-4-23 创建
 */
@SuppressWarnings("unchecked")
public class DefaultBoImpl extends GenericBoImpl
{
	/**
	 * 必须要有空构造方法
	 */
	public DefaultBoImpl()
	{
	}

	/**
	 * 设置DefaultBoImpl对象关联的Vo 
	 * @param clazz Vo类的class对象
	 */
	public void setRelateVo(Class<? extends BaseVo> clazz)
	{
		this.persistentClass = clazz;
		this.daoClass = DefaultDaoImpl.class;
	}

	/**
	 * 重写父类getDao方法，设置dao的持久化Vo类。
	 * 如果不设置的话DefaultDao的Vo为BaseVo，Hibernate映射的时候会出现问题。
	 */
	@Override
	protected GenericDao getDao()
	{
		DefaultDaoImpl dao = (DefaultDaoImpl) super.getDao();
		dao.setRelateVo(this.persistentClass);
		return dao;
	}
}
