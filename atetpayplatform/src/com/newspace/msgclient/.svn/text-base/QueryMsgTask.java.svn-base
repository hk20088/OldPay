package com.newspace.msgclient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.msgclient.entity.MsgEntity;
import com.newspace.payplatform.base.DefaultBoImpl;
import com.newspace.payplatform.unicomsms.ice.Mt;

/**
 * QueryMsgTask.java 
 * 描 述:  查询还未发送短信的任务  
 * 作 者:  liushuai
 * 历 史： 2014-9-19 创建
 */
public class QueryMsgTask implements Runnable
{
	private static DefaultBoImpl msgBo = SpringBeanUtils.getBean(DefaultBoImpl.class);

	private ExecutorService pool = Executors.newCachedThreadPool();

	static
	{
		msgBo.setRelateVo(MsgEntity.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run()
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(MsgEntity.class);
		criteria.add(Restrictions.eq("state", 0));

		List<MsgEntity> list = msgBo.query(criteria);
		for (final MsgEntity entity : list)
		{
			pool.execute(new Runnable()
			{
				@Override
				public void run()
				{
					Mt mt = Mt.fillMt(entity.getTelephone(), entity.getContent());
					if (SendMsgClient.send(mt) == 0)
					{
						entity.setState(MsgEntity.STATE_SENDED);
						msgBo.saveOrUpdate(entity);
					}
				}
			});
		}
	}
}
