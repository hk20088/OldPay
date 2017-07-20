package com.newspace.payplatform.paytype.bo.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.newspace.common.bo.impl.GenericBoImpl;
import com.newspace.payplatform.ReturnCode;
import com.newspace.payplatform.paytype.bo.PayTypeBo;
import com.newspace.payplatform.paytype.dao.PayTypeDao;
import com.newspace.payplatform.paytype.vo.PayTypeVo;
import com.newspace.payplatform.paytype.web.PayMethodData;
import com.newspace.payplatform.paytype.web.PayMethodReqVo;
import com.newspace.payplatform.paytype.web.PayMethodRespVo;
import com.newspace.payplatform.syncdevice.bo.impl.SyncDeviceBoImpl;
import com.newspace.payplatform.syncdevice.vo.SyncDeviceVo;

public class PayTypeBoImpl extends GenericBoImpl<PayTypeVo, PayTypeDao> implements PayTypeBo{


	@Resource
	SyncDeviceBoImpl deviceBo;
	
	/**
	 * 获取设备支持支付方式接口实现。
	 * @param reqVo
	 * @return
	 */
	public PayMethodRespVo getPayMethod(PayMethodReqVo reqVo){
		PayMethodRespVo respVo = new PayMethodRespVo();
		List<PayMethodData> data = new ArrayList<PayMethodData>();
		try {
			//获取所有正常的支付方式。
			List<PayTypeVo> payTypes = getPayTypes();
			
			//获取此款设备不支持的支付方式
			SyncDeviceVo deviceVo = deviceBo.getSyncDevice(reqVo.getDeviceId());
			if(deviceVo != null){
				for(PayTypeVo payType : payTypes){
					//从支付方式集合中剔除掉此设备不支持的支付方式，放到要返回给终端的集合中。
					if(!deviceVo.getPayTypes().contains(payType)){
						PayMethodData vo = new PayMethodData();
						vo.setPaymethodCode(payType.getPayTypeCode());
						data.add(vo);
					}
				}
				respVo.setData(data);
				respVo.setCode(ReturnCode.SUCCESS.getCode());
			}else{ //如果查询不到此设备，则返回所有正常状态的支付方式给终端。
				for(PayTypeVo payType : payTypes){
					PayMethodData vo = new PayMethodData();
					vo.setPaymethodCode(payType.getPayTypeCode());
					data.add(vo);
				}
				respVo.setData(data);
				respVo.setCode(ReturnCode.SUCCESS.getCode());
			}
		} catch (Exception e) {
			logger.error("获取设备支付方式出错！",e);
			respVo.setCode(ReturnCode.ERROR.getCode());
		}
		return respVo;
		
	}
	
	/**
	 * 获取没有被冻结的支付方式。
	 * @return
	 */
	public List<PayTypeVo> getPayTypes(){
		
		String hql = "from PayTypeVo where state != 1";

	    return query(hql);
	}
}
