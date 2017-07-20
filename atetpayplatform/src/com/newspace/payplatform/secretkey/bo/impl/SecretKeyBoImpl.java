package com.newspace.payplatform.secretkey.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.bo.impl.GenericBoImpl;
import com.newspace.common.coder.RSACoder;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.base.BaseResponseJsonVo;
import com.newspace.payplatform.base.DefaultBoImpl;
import com.newspace.payplatform.paypoint.vo.PayPointVo;
import com.newspace.payplatform.secretkey.bo.SecretKeyBo;
import com.newspace.payplatform.secretkey.dao.SecretKeyDao;
import com.newspace.payplatform.secretkey.vo.SecretKeyRspVo;
import com.newspace.payplatform.secretkey.vo.SecretKeyVo;
import com.newspace.payplatform.secretkey.web.CpInfoReqVo;
import com.newspace.payplatform.secretkey.web.PayPointList;

public class SecretKeyBoImpl extends GenericBoImpl<SecretKeyVo, SecretKeyDao> implements SecretKeyBo
{

	@Resource
	DefaultBoImpl defaultBo;

	/**
	 * @name 获取密钥接口实现
	 * @describe CP端调用此接口时生成密钥对，并将密钥对存储在数据库中，然后将私钥返回给CP
	 * @param reqVo
	 * @return rspVo
	 * @date 2014年4月24日
	 */
	@Override
	public SecretKeyRspVo doAppKey(SecretKeyVo reqVo)
	{
		SecretKeyRspVo rspVo = new SecretKeyRspVo();
		try
		{
			// 生成私钥和公钥放在Map中
			Map<String, Object> map = RSACoder.initKey();
			String pri = RSACoder.getPrivateKey(map);
			String pub = RSACoder.getPublicKey(map);

			reqVo.setPrivateKey(pri);
			reqVo.setPublicKey(pub);
			reqVo.setCreateTime(new Date());

			insert(reqVo);
			// 将私钥返回给CP
			rspVo.setAppKey(pri);
			rspVo.setCode(ReturnCode.SUCCESS.getCode());
		}
		catch (Exception e)
		{
			logger.error("获取密钥接口错误！", e);
			rspVo.setCode(ReturnCode.ERROR.getCode());
		}
		return rspVo;
	}

	/**
	 * 同步CP信息接口
	 * @describe CP端调用此接口将CP信息同步到数据库
	 * @param reqVo
	 * @return respVo
	 * @date 2014年4月28日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaseResponseJsonVo saveCpInfo(CpInfoReqVo reqVo)
	{
		BaseResponseJsonVo respVo = new BaseResponseJsonVo();
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(SecretKeyVo.class).add(Restrictions.eq("appId", reqVo.getAppId()));
			List<SecretKeyVo> list = query(criteria);
			if (list.size() > 0) // 表示密钥表中存在此应用
			{
				SecretKeyVo vo = list.get(0);
				vo.setCpId(reqVo.getCpId());
				vo.setCpName(reqVo.getCpName());
				vo.setPackageName(reqVo.getPackageName());
				vo.setCpNotifyUrl(reqVo.getCpNotifyUrl());
				vo.setUpdateTime(new Date());
				// 将CP信息更新到密钥表中
				saveOrUpdate(vo);
				//将更新后的secretKeyVo类放到map中。注：目前无法实现多台服务器间的内存共享，若将数据放到内存中会造成数据更新时无法同步
//				PayUtils.cachedSecretKeyVoMap.put(reqVo.getAppId(), vo);

				/**
				 * 条件：
				 * 1、此接口有两种情况可被调用，① CP审核完成后调用，② CP的通知地址或其它信息有修改时调用。
				 * 2、支付点可添加，删除，不可修改。
				 * 3、支付点添加或删除后都需要重新审核，这时候CP系统会将所有支付点全部同步过来 。
				 * 操作：
				 * 1、首先判断pointList中是否有值，若有值说明请求参数中有此应用的支付点信息，继续操作。
				 * 2、再根据appId查询支付点表，然后删除此应用对应的所有支付点
				 * 3、添加此次同步的支付点，并记录每个支付点状态：0 代表正常， 1 代表CP已将此支付点删除。
				 */
				List<PayPointList> pointLists = reqVo.getPayPointList();
				if (pointLists.size() > 0)
				{

					defaultBo.setRelateVo(PayPointVo.class);
					DetachedCriteria cri = DetachedCriteria.forClass(PayPointVo.class).add(Restrictions.eq("appId", reqVo.getAppId()));

					defaultBo.delete(cri);
						Iterator<?> iterator = pointLists.iterator();
						List<PayPointVo> payList = new ArrayList<PayPointVo>();
						while (iterator.hasNext())
						{
							PayPointVo pointVo = new PayPointVo();
							PayPointList pointList = (PayPointList) iterator.next();

							pointVo.setAppId(reqVo.getAppId());
							pointVo.setPayCode(pointList.getPayCode());
							pointVo.setPayName(pointList.getPayName());
							pointVo.setMoney(pointList.getMoney());
							pointVo.setState(pointList.getState());
							pointVo.setSyncTime(new Date());

							payList.add(pointVo);
							defaultBo.insert(pointVo);
						}
						//将支付点信息放入MAP中。
//						PayUtils.cachedPayPointVoMap.put(reqVo.getAppId(), payList);
				}
			}
			else
			{
				logger.error(String.format("同步CP信息接口错误_密钥表中无此应用，不能将此数据插入数据库！查询appId为：%s", reqVo.getAppId()));
				respVo.setCode(ReturnCode.CP_SYNC_FAILED.getCode());
			}
		}
		catch (Exception e)
		{
			logger.error("同步CP信息接口出现错误！", e);
			respVo.setCode(ReturnCode.ERROR.getCode());
		}
		return respVo;
	}

	/**
	 * 获得appId对应的SecretKeyVo对象
	 * @param appId 
	 * @return SecretKeyVo对象
	 */
	@Override
	public SecretKeyVo getSecretKeyVoInfo(String appId)
	{
		if (StringUtils.isNullOrEmpty(appId))
			return null;

		DetachedCriteria criteria = DetachedCriteria.forClass(SecretKeyVo.class);
		criteria.add(Restrictions.eq("appId", appId));
		List<SecretKeyVo> list = query(criteria);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}
}
