package com.newspace.payplatform.bindtelephone.bo;

import com.newspace.common.bo.GenericBo;
import com.newspace.payplatform.bindtelephone.dao.BindTelephoneDao;
import com.newspace.payplatform.bindtelephone.vo.BindTelephoneVo;

/**
 * BindTelephoneBo.java 
 * 描 述:  手机号绑定业务bo接口
 * 作 者:  liushuai
 * 历 史： 2014-7-15 创建
 */
public interface BindTelephoneBo extends GenericBo<BindTelephoneVo, BindTelephoneDao>
{
	/**
	 * 绑定手机号
	 * @param vo BindTelephoneVo对象
	 * @return int returnCode , 0:成功
	 */
	public int bindTelephone(BindTelephoneVo vo);

	/**
	 * 查询绑定的手机号
	 * @param userId 用户id
	 * @param productId 设备唯一标识
	 * @param deviceCode 设备编码
	 * @return 手机号
	 */
	public String getTelephone(Integer userId, String atetId);

	/**
	 * 校验验证码是否正确
	 * @param vo BindTelephoneVo对象
	 * @return returnCode，0 ：成功。
	 */
	public int verifyCheckCode(BindTelephoneVo vo);

}
