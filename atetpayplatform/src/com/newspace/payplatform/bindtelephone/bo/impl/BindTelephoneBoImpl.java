package com.newspace.payplatform.bindtelephone.bo.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.bo.impl.GenericBoImpl;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.bindtelephone.bo.BindTelephoneBo;
import com.newspace.payplatform.bindtelephone.dao.BindTelephoneDao;
import com.newspace.payplatform.bindtelephone.vo.BindTelephoneVo;

/**
 * BindTelephoneBoImpl.java 
 * 描 述:  手机号绑定业务bo类
 * 作 者:  liushuai
 * 历 史： 2014-7-15 创建
 */
public class BindTelephoneBoImpl extends GenericBoImpl<BindTelephoneVo, BindTelephoneDao> implements BindTelephoneBo
{
	/**
	 * 校验码有效期，10min
	 */
	public static final int EXPIRE_TIME = 10 * 60 * 1000;

	/**
	 * 绑定手机号
	 * @param vo BindTelephoneVo对象
	 * @return boolean 绑定操作是否成功
	 * @return int returnCode , 0:成功
	 */
	public int bindTelephone(BindTelephoneVo vo)
	{
		if (StringUtils.isExistNullOrEmpty(vo.getAtetId(), vo.getTelephone()))
		{
			logger.error("绑定手机号操作失败，atetId、telephone存在空值！");
			return ReturnCode.REQUEST_PARAM_EXIST_NULL.getCode();
		}
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(BindTelephoneVo.class);
			criteria.add(Restrictions.eq("atetId", vo.getAtetId()));
			if (vo.getUserId() != null)
				criteria.add(Restrictions.eq("userId", vo.getUserId()));
			else
				criteria.add(Restrictions.isNull("userId"));
			BindTelephoneVo bindTelVo = uniqueResult(criteria);
			int sendCount = getCount(vo.getTelephone(), vo.getDeviceType());
			// 先屏蔽掉每天发送三次的限制，方便测试，正式版本再取消掉这些注释。
			/*if (sendCount >= 3)
			{ // 超出了每天每个手机号的发送限制次数
				return ReturnCode.BINDTEL_OVERTOP_COUNT.getCode();
			}
			else
			{*/
			// 不存在则进行新增操作
			if (bindTelVo == null)
			{
				vo.setSendCount(1);
				vo.setLastSendTime(new Timestamp(new Date().getTime()));
				getDao().saveOrUpdate(vo);
			}
			else
			// 已经存在进行更新手机号操作
			{
				// 如果之前最后发送日期为今天，则将发送次数+1 ；
				if (inToday(bindTelVo.getLastSendTime()))
					bindTelVo.setSendCount(bindTelVo.getSendCount() + 1);
				else
					// 如果最后发送日期不为今天，重新设置为1
					bindTelVo.setSendCount(1);
				bindTelVo.setLastSendTime(new Timestamp(new Date().getTime()));
				bindTelVo.setTelephone(vo.getTelephone());
				bindTelVo.setExt(vo.getExt());
				getDao().saveOrUpdate(bindTelVo);
			}
			// }
		}
		catch (Exception e)
		{
			logger.error("绑定手机号操作失败！", e);
			return ReturnCode.ERROR.getCode();
		}
		return ReturnCode.SUCCESS.getCode();
	}

	/**
	 * 查询绑定的手机号
	 * @param userId 用户id
	 * @param productId 设备唯一标识
	 * @param deviceCode 设备编码
	 * @return 手机号
	 */
	@Override
	public String getTelephone(Integer userId, String atetId)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BindTelephoneVo.class);
		criteria.add(Restrictions.eq("atetId", atetId));
		criteria.add(Restrictions.eq("state", 0));
		if (userId != null)
			criteria.add(Restrictions.eq("userId", userId));
		else
			criteria.add(Restrictions.isNull("userId"));
		BindTelephoneVo bindTelVo = uniqueResult(criteria);
		return bindTelVo == null ? null : bindTelVo.getTelephone();
	}

	/**
	 * 校验验证码是否正确
	 * @param vo BindTelephoneVo对象
	 * @return returnCode，0 ：成功。
	 */
	@Override
	public int verifyCheckCode(BindTelephoneVo vo)
	{
		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(BindTelephoneVo.class);
			criteria.add(Restrictions.eq("atetId", vo.getAtetId()));
			criteria.add(Restrictions.eq("telephone", vo.getTelephone()));
			if (vo.getUserId() != null)
				criteria.add(Restrictions.eq("userId", vo.getUserId()));
			else
				criteria.add(Restrictions.isNull("userId"));
			BindTelephoneVo bindTelVo = uniqueResult(criteria);
			if (bindTelVo != null)
			{
				Date lastSendTime = bindTelVo.getLastSendTime();
				if (lastSendTime.getTime() + EXPIRE_TIME < new Date().getTime())// 判断验证码是否失效，有效期10min
				{
					logger.info("校验验证码失败，验证码已失效");
					return ReturnCode.BINDTEL_EXPIRED.getCode();
				}

				if (vo.getExt().equals(bindTelVo.getExt()))// 输入验证码正确
				{
					bindTelVo.setState(BindTelephoneVo.STATE_SUCCESS);
					logger.info("校验验证码正确，更改绑定手机号状态");
					bindTelVo.setExt(null);// 绑定成功之后，删除数据中验证码信息
					saveOrUpdate(bindTelVo);
					return ReturnCode.SUCCESS.getCode();
				}
				logger.info(String.format("【校验验证码不正确，数据库中：%s,传入值：%s】", bindTelVo.getExt(), vo.getExt()));
				return ReturnCode.VERIFY_CHECKCODE_NOTEQUAL.getCode();
			}
			logger.error("查询不到相关数据");
		}
		catch (Exception e)
		{
			logger.error("校验验证码失败，请重新获取验证码", e);
		}
		return ReturnCode.VERIFY_CHECKCODE_FAIL.getCode();
	}

	/**
	 * 获取手机号当天已经发送验证码的次数
	 */
	private int getCount(String phone, int deviceType)
	{
		// 如果上行短信绑定手机操作,跟验证码次数没有关系，直接返回0
		if (deviceType == BindTelephoneVo.DEVICTTYPE_PHONE)
			return 0;

		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);

		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);

		DetachedCriteria cri = DetachedCriteria.forClass(BindTelephoneVo.class);
		cri.add(Restrictions.eq("telephone", phone));
		cri.add(Restrictions.between("lastSendTime", start.getTime(), end.getTime()));
		cri.add(Restrictions.eq("deviceType", BindTelephoneVo.DEVICETYPE_TV));
		cri.addOrder(Order.desc("sendCount"));

		// 当符合条件的记录超过三条则说明今天肯定至少已经发送三次了，只用查前三条即可。
		List<BindTelephoneVo> list = query(cri, 3);
		if (list.size() >= 3)
			return 3;
		int count = 0;
		for (BindTelephoneVo vo : list)
			count += vo.getSendCount();
		return count;
	}

	/**
	 * 判断日期是否在今天之内 
	 */
	private boolean inToday(Date date)
	{
		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);

		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal.after(start) && cal.before(end);
	}
}