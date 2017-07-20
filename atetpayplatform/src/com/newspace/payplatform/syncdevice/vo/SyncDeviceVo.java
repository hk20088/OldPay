package com.newspace.payplatform.syncdevice.vo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.newspace.common.vo.BaseVo;
import com.newspace.payplatform.base.JsonVo;
import com.newspace.payplatform.paytype.vo.PayTypeVo;

public class SyncDeviceVo extends BaseVo implements JsonVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 设备Id(后台数据库生成的设备表唯一标识，非设备硬件Id)
	 */
	private String deviceId;

	private String deviceCode;

	/**
	 * 设备类型：例：乐视TV
	 */
	private String deviceName;

	/**
	 * 设备类型：1：电视 2：手机 3：平板
	 */
	private Integer deviceType;

	/**
	 * 渠道Id
	 */
	private String channelId;
	
	/**
	 * 渠道名称
	 */
	private String channelName;
	
	private Timestamp createTime;
	
	/**
	 * 此款设备不支持的支付方式集合。
	 */
	private Set<PayTypeVo> payTypes = new HashSet<PayTypeVo>();

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Set<PayTypeVo> getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(Set<PayTypeVo> payTypes) {
		this.payTypes = payTypes;
	}

	@Override
	public String logString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{deviceId:").append(deviceId);
		sb.append(", deviceCode:").append(deviceCode);
		sb.append(", deviceName:").append(deviceName);
		sb.append(", deviceType:").append(deviceType);
		sb.append(", channelId:").append(channelId);
		sb.append(", channelName:").append(channelName).append(" }");
		return sb.toString();
	}

}
