package com.newspace.payplatform.syncdevice.bo;

import com.newspace.common.bo.GenericBo;
import com.newspace.payplatform.syncdevice.dao.SyncDeviceDao;
import com.newspace.payplatform.syncdevice.vo.SyncDeviceVo;
import com.newspace.payplatform.syncdevice.web.SyncDeviceReqVo;
import com.newspace.payplatform.syncdevice.web.SyncDeviceRespVo;

public interface SyncDeviceBo extends GenericBo<SyncDeviceVo, SyncDeviceDao>{

	
	/**
	 * 同步设备信息接口
	 * @param reqVo
	 * @return
	 */
	public SyncDeviceRespVo saveDeviceInfo(SyncDeviceReqVo reqVo);
	
	/**
	 * 根据deviceId(数据库生成的设备唯一标识)获得对应的SyncDeviceVo信息。
	 * @param deviceId
	 * @return
	 */
	public SyncDeviceVo getSyncDevice(String deviceId);
}
