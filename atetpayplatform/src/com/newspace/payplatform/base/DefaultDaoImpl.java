package com.newspace.payplatform.base;

import com.newspace.common.dao.impl.GenericDaoImpl;
import com.newspace.common.vo.BaseVo;

/**
 * DefaultDaoImpl.java 
 * 描 述:  默认的Dao实现类；包级别访问权限。
 * 作 者:  liushuai
 * 历 史： 2014-4-23 创建
 */
@SuppressWarnings("unchecked")
class DefaultDaoImpl extends GenericDaoImpl<BaseVo>
{
	public DefaultDaoImpl()
	{
	}

	/**
	 * 设置DefaultDao关联的持久化Vo
	 * @param clazz
	 */
	public void setRelateVo(Class clazz)
	{
		this.persistentClass = clazz;
	}
}
