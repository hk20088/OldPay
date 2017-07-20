package com.newspace.payplatform.secretkey.bo;

import com.newspace.common.bo.GenericBo;
import com.newspace.payplatform.base.BaseResponseJsonVo;
import com.newspace.payplatform.secretkey.dao.SecretKeyDao;
import com.newspace.payplatform.secretkey.vo.SecretKeyRspVo;
import com.newspace.payplatform.secretkey.vo.SecretKeyVo;
import com.newspace.payplatform.secretkey.web.CpInfoReqVo;

public interface SecretKeyBo extends GenericBo<SecretKeyVo, SecretKeyDao>
{
	/**
	 * 获取密钥接口
	 * @param reqVo
	 * @return respVo
	 */
	public SecretKeyRspVo doAppKey(SecretKeyVo reqVo);

	/**
	 * 同步CP信息接口
	 * @param reqVo
	 * @return respVo
	 */
	public BaseResponseJsonVo saveCpInfo(CpInfoReqVo reqVo);

	/**
	 * 获得appId对应的publicKey和cpName
	 * @param appId 
	 * @return SecretKeyVo对象
	 */
	public SecretKeyVo getSecretKeyVoInfo(String appId);
}
