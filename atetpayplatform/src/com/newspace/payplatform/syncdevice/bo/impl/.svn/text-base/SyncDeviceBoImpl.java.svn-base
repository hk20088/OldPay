package com.newspace.payplatform.syncdevice.bo.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.bo.impl.GenericBoImpl;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.syncdevice.bo.SyncDeviceBo;
import com.newspace.payplatform.syncdevice.dao.SyncDeviceDao;
import com.newspace.payplatform.syncdevice.vo.SyncDeviceVo;
import com.newspace.payplatform.syncdevice.web.SyncDeviceReqVo;
import com.newspace.payplatform.syncdevice.web.SyncDeviceRespVo;

public class SyncDeviceBoImpl extends GenericBoImpl<SyncDeviceVo, SyncDeviceDao> implements SyncDeviceBo {

	/**
	 * @description 同步设备信息接口实现。
	 * @author huqili
	 * @date 2014年12月25日
	 * 
	 */
	@Override
	public SyncDeviceRespVo saveDeviceInfo(SyncDeviceReqVo reqVo) {
		
		SyncDeviceRespVo respVo = new SyncDeviceRespVo();
		List<SyncDeviceVo> list = new ArrayList<SyncDeviceVo>();
		SyncDeviceVo vo = new SyncDeviceVo();
		
		try {
			DetachedCriteria criteria=DetachedCriteria.forClass(SyncDeviceVo.class)
								.add(Restrictions.eq("deviceId", reqVo.getDeviceId()));
			
			list = query(criteria);
			if(null == list || list.size() <= 0){ //此信息未同步过，直接插入。
				
				vo = fillVo(reqVo);
				insert(vo);
			}else{
				vo = fillVo(reqVo);
				vo.setId(list.get(0).getId());
				saveOrUpdate(vo);
			}
			respVo.setCode(ReturnCode.SUCCESS.getCode());
			
		} catch (Exception e) {
			logger.error("同步设备信息出错！");
			respVo.setCode(ReturnCode.ERROR.getCode());
		}
		return respVo;
	}
	
	/**
	 * 将请求参数值填充到vo 。
	 * @param reqVo
	 * @return
	 */
	public SyncDeviceVo fillVo(SyncDeviceReqVo reqVo){
		SyncDeviceVo vo = new SyncDeviceVo();
		
		vo.setDeviceId(reqVo.getDeviceId());
		vo.setDeviceCode(reqVo.getDeviceCode());
		vo.setDeviceName(reqVo.getDeviceName());
		vo.setDeviceType(reqVo.getDeviceType());
		vo.setChannelId(reqVo.getChannelId());
		vo.setChannelName(reqVo.getChannelName());
		vo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		
		return vo;
	}

	/**
	 * 根据deviceId(数据库生成的设备唯一标识)获得对应的SyncDeviceVo信息。
	 * @param deviceId
	 * @return
	 */
	@Override
	public SyncDeviceVo getSyncDevice(String deviceId) {
		if(StringUtils.isNullOrEmpty(deviceId)){
			return null;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(SyncDeviceVo.class)
							.add(Restrictions.eq("deviceId", deviceId));
		
		List<SyncDeviceVo> list = query(criteria);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
