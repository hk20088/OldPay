package com.newspace.payplatform;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.newspace.common.coder.Coder;
import com.newspace.common.exception.BoException;
import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.spring.SpringBeanUtils;
import com.newspace.common.utils.NativeEncryptUtils;
import com.newspace.common.utils.PropertiesUtils;
import com.newspace.common.utils.StringUtils;
import com.newspace.payplatform.base.DefaultBoImpl;
import com.newspace.payplatform.order.bo.PaymentOrderBo;
import com.newspace.payplatform.order.vo.PaymentOrderVo;
import com.newspace.payplatform.paypoint.vo.PayPointVo;
import com.newspace.payplatform.secretkey.bo.SecretKeyBo;
import com.newspace.payplatform.secretkey.vo.SecretKeyVo;
import com.newspace.payplatform.syncdevice.bo.SyncDeviceBo;
import com.newspace.payplatform.syncdevice.vo.SyncDeviceVo;

/**
 * PayUtils.java 
 * 描 述:  支付相关工具类  
 * 作 者:  liushuai
 * 历 史： 2014-6-27 创建
 */
public final class PayUtils
{
	/**
	 * 地区代码。
	 */
	public static final String AREA_CODE;

	/**
	 * 错误的数字签名，随便写的。当需要加密的时候却又没有key，那么返回一个错误的签名。
	 */
	public static final String WRONG_SIGN = "HASFDQFAFDLISSAFSAF2WRS2B";

	/**
	 * ATET RSA加密中的私钥 
	 */
	public static final String RSA_ATET_PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJx3hRxjUpFbDLk86gXj4ZOooKRMTp1T1bzP6U6Z+fjNGRLX+aKhewo3VCP2wChQ1kYPxU+AJH0oMT0tXcbxMXCBRfnSdv/xN9nfKmoYybuT+bY7OtNTMc21uHXK0rH+LpN50iR/tCzSK5PAORW4ypzpRNA/IWLqt7IOwZ3wjgfpAgMBAAECgYB6YsqNn+rvo2ZaZhkvLkY9t0KgAMflK7QdkgsN3ka2o8afBKxQ1zpkjU6VKua3IjPYbXGKc9MWyp9pGNknSXW/L09zqp/oOmRo3ap944bw4vW5K0mnzmeIJfV19EUrPX6g/SMKjUd+WaHVvc4Pyn8IuO3viEdEbwFm1q/dmyEd+QJBAMz67ACLyEptWICstydN82Wr6FUyKuIbUh7lHY9kA80QlwhUDVV9zvuJvWnuCM+4QDEzskAxIAfzS1N9K/odUesCQQDDaWRx2OfBvDdjaIRR1SvwyQ3tDRtsFlH429rTSvdUeZe/FqMnXZdF8kr/Y67AbUQZWIS13O5d5h8JsujxkAR7AkBdkkn7sdO7zhCxpKZzRc1PY1tK0PzsfKZPPi3xUCnACcu4XI49sZG0F9ukqKnTEPwUudGsJgDGQFTphuF1ar8DAkBxF8jbHsaaWDzSGoYh+jaRBzs5C1Hoj05nsY4GpSdZS3noTMimGsNW5vBCuEVF9rbn2FQOEMwfqfQin9mzHD+LAkBnS00wd9wG8HAHmqmbhcc0w/hREfpOSmiDY8LRKZZG3hZF1q2e4aEDufkGno521p9JFsgABjEOv/NyanDCcSkc";

	/**
	 * ATET RSA加密中的公钥 
	 */
	public static final String RSA_ATET_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcd4UcY1KRWwy5POoF4+GTqKCkTE6dU9W8z+lOmfn4zRkS1/mioXsKN1Qj9sAoUNZGD8VPgCR9KDE9LV3G8TFwgUX50nb/8TfZ3ypqGMm7k/m2OzrTUzHNtbh1ytKx/i6TedIkf7Qs0iuTwDkVuMqc6UTQPyFi6reyDsGd8I4H6QIDAQAB";

	/**
	 * SecretKeyVo对象的缓存Map
	 */
//	private static final ConcurrentHashMap<String, SecretKeyVo> cachedSecretKeyVoMap = new ConcurrentHashMap<String, SecretKeyVo>();

	/**
	 * SyncDeviceVo对象缓存Map
	 */
//	private static final ConcurrentHashMap<String, SyncDeviceVo> cachedSyncDeviceMap = new ConcurrentHashMap<String, SyncDeviceVo>();

	/**
	 * PayPointVo集合的缓存Map
	 */
	private static final ConcurrentHashMap<String, List<PayPointVo>> cachedPayPointVoMap = new ConcurrentHashMap<String, List<PayPointVo>>();

	private static final SecretKeyBo keyBo = SpringBeanUtils.getBean(SecretKeyBo.class);

	private static final DefaultBoImpl payPointBo = SpringBeanUtils.getBean(DefaultBoImpl.class);

	private static final PaymentOrderBo orderBo = SpringBeanUtils.getBean(PaymentOrderBo.class);

	private static final SyncDeviceBo deviceBo = SpringBeanUtils.getBean(SyncDeviceBo.class);

	private static final JLogger logger = LoggerUtils.getLogger(PayUtils.class);

	static
	{
		payPointBo.setRelateVo(PayPointVo.class);
		Properties prop = PropertiesUtils.getProps("configuration");
		AREA_CODE = prop.getProperty("area_code");
	}

	/**
	 * 校验签名、支付点等信息，并进行记录订单信息
	 * @param data 原数据
	 * @param sign 数字签名
	 * @param reqVo 订单类Vo
	 * @return 返回码ReturnCode
	 */
	public static int verifyAndRecordOrder(String data, String sign, PaymentOrderVo reqVo)
	{
		if (reqVo == null || StringUtils.isNullOrEmpty(reqVo.getAppId()))
		{
			return ReturnCode.REQUEST_PARAM_ERROR.getCode();
		}

		int returnCode = ReturnCode.SUCCESS.getCode();
		SecretKeyVo keyVo = getSecretKeyVoInfo(reqVo.getAppId());
		if (keyVo != null)
		{
			// 校验签名。签名生成规则：AES(MD5(source_data));
			returnCode = verifySign(data, keyVo.getPrivateKey(), sign);

			//校验CPID及packageName是否正确。
			if(returnCode == ReturnCode.SUCCESS.getCode()){
				if(!reqVo.getCpId().equals(keyVo.getCpId()) || !reqVo.getPackageName().equals(keyVo.getPackageName())){
					logger.info(String.format("根据appID从数据库中查询出来的CPID：%s，packageName:%s", keyVo.getCpId(),keyVo.getPackageName()));
					returnCode = ReturnCode.APPID_NOT_MATCH.getCode();
				}
			}
			
			// 校验支付点相关信息
			if (returnCode == ReturnCode.SUCCESS.getCode())
			{
				returnCode = verifyPayPointInfo(reqVo);
				if (returnCode == ReturnCode.SUCCESS.getCode())
				{
					try
					{
						orderBo.saveOrUpdate(reqVo);
					}
					catch (Exception e)
					{
						logger.error("记录支付订单失败！", e);
						returnCode = ReturnCode.RECORD_ORDER_FAIL.getCode();
					}
				}
			}
		}
		else
		{
			returnCode = ReturnCode.NOT_EXIST_MATCHED_INFO.getCode();
		}
		return returnCode;
	}

	/**
	 * 更新订单
	 * @param reqVo 订单对象
	 */
	public static int saveOrUpdateOrder(PaymentOrderVo reqVo)
	{
		try
		{
			orderBo.saveOrUpdate(reqVo);
			return ReturnCode.SUCCESS.getCode();
		}
		catch (BoException e)
		{
			logger.error(String.format("新增或更新订单[%s]信息出错: %s", reqVo.getOrderNo(), e.getMessage()));
			return ReturnCode.RECORD_ORDER_FAIL.getCode();
		}
	}

	/**
	 * 通过订单号查询出订单对象
	 * @param orderNo 订单号
	 * @return PaymentOrderVo 订单对象
	 */
	public static PaymentOrderVo queryByOrderNo(String orderNo,Boolean flag)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentOrderVo.class);
		criteria.add(Restrictions.eq("orderNo", orderNo));
		if(flag)
			criteria.add(Restrictions.eq("state", PaymentOrderVo.PAY_STATE_NONPROCESS));
		return orderBo.uniqueResult(criteria);
	}
	
	/**
	 * 通过用户手机号，订单状态，支付渠道（沃+）查询订单，取最近一条
	 * @param phone
	 * @param flag
	 * @return
	 */
	public static PaymentOrderVo queryByTel(String phone,Boolean flag)
	{
		PaymentOrderVo  orderVo = null; 
		
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentOrderVo.class)
							.add(Restrictions.eq("telephone", phone))
							.add(Restrictions.eq("payType", PaymentOrderVo.PAYTYPE_UNICOM_WO))
							.addOrder(Order.desc("createTime"));
		if(flag)
		{
			criteria.add(Restrictions.eq("state", PaymentOrderVo.PAY_STATE_NONPROCESS));
		}
		else
		{
			criteria.add(Restrictions.eq("state", PaymentOrderVo.PAY_STATE_SUCCESS));
		}
		
		
		List<PaymentOrderVo> list = orderBo.query(criteria);
		
		if(!list.isEmpty() && null != list)
		{
			orderVo = list.get(0);
		}
		
		return orderVo;
	}

	/**
	 * 得到AppId对应的私钥
	 * @param appId 
	 * @return String 私钥
	 */
	public static String getPrivateKey(String appId)
	{
		SecretKeyVo keyVo = getSecretKeyVoInfo(appId);
		return keyVo == null ? null : keyVo.getPrivateKey().replaceAll("\\s*|\t|\r|\n", "");
	}

	/**
	 * 得到AppId对应的CP通知地址
	 * @param appId
	 * @return String CpNotifyUrl
	 */
	public static String getNotifyUrl(String appId)
	{
		SecretKeyVo keyVo = getSecretKeyVoInfo(appId);
		return keyVo == null ? null : keyVo.getCpNotifyUrl();
	}

	/**
	 * 得到AppId对应的CpId
	 * @param appId
	 * @return
	 */
	public static String getCpId(String appId)
	{
		SecretKeyVo keyVo = getSecretKeyVoInfo(appId);
		return keyVo == null ? null : keyVo.getCpId();
	}

	/**
	 * 校验签名。签名生成规则：AES(MD5(SOURCE_DATA))
	 * @param sourceData 原数据
	 * @param key AES密钥
	 * @param sign  签名
	 * @return int ReturnCode
	 */
	public static int verifySign(String sourceData, String key, String sign)
	{
		if (key == null)
			return ReturnCode.REQUEST_PARAM_ERROR.getCode();

		int retCode = ReturnCode.SUCCESS.getCode();
		try
		{
			// 由于之前privatekey长度太长，修改为对key进行MD5之后作为新的key
			key = Coder.getHexStringByEncryptMD5(key);
			// 解密签名，然后和 源数据生成的MD5值进行比对
			if (!Coder.getHexStringByEncryptMD5(sourceData).equalsIgnoreCase(NativeEncryptUtils.decryptAES(sign, key)))
			{
				retCode = ReturnCode.VERIFY_SIGN_DATACHANGED.getCode();
				logger.info("\r\n【校验签名结果不一致！】");
			}
		}
		catch (Exception e)
		{
			logger.info(String.format("【key : %s, key.length(): %s】", key, key.length()));
			logger.error("\r\n【校验签名操作失败！】", e);
			retCode = ReturnCode.VERIFY_SIGN_ERROR.getCode();
		}
		return retCode;
	}

	
	/**
	 * 校验支付点信息：
	 * 根据appId得到支付点表中信息，比对支付点是否存在，支付点金额是否正确，总价格是否相等。
	 * @param vo PaymentOrderVo对象
	 * @return int ReturnCode
	 */
	@SuppressWarnings("unchecked")
	public static int verifyPayPointInfo(PaymentOrderVo vo)
	{
		List<PayPointVo> list = cachedPayPointVoMap.get(vo.getAppId());
		if (list == null || list.size() == 0)
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(PayPointVo.class)
								.add(Restrictions.eq("appId", vo.getAppId()))
								.add(Restrictions.eq("state", 0));
								
			list = payPointBo.query(criteria);
//			cachedPayPointVoMap.put(vo.getAppId(), list);  目前多台服务器间无法实现内在共享，所以不能将信息放到内存中。
		}
		if (list != null)
		{
			for (int i = 0; i < list.size(); i++)
			{
				PayPointVo payPoint = list.get(i);
				if (payPoint.getPayCode().equals(vo.getPayPoint()))
				{
					vo.setPayPointName(payPoint.getPayName());
					vo.setPayPointMoney((int) (payPoint.getMoney() * 1));// PayPoint中的价格单位同样是：分
					if (vo.getAmount() == vo.getCounts() * vo.getPayPointMoney())
						return ReturnCode.SUCCESS.getCode();
					else
						return ReturnCode.PAYPOINT_MONEY_NOT_EQUAL.getCode();
				}
			}
		}
		logger.info(String.format("传入的appId为：%s", vo.getAppId()));
		return ReturnCode.PAYPOINT_NOT_EXIST.getCode();
	}

	/**
	 * 得到SecretKeyVo对象。先从缓存Map中获取，如果获取不到，查询数据库并将查询结果放入缓存Map。
	 * @param appId  
	 * @return SecretKeyVo对象
	 */
	public static SecretKeyVo getSecretKeyVoInfo(String appId)
	{
		if (StringUtils.isNullOrEmpty(appId))
			return null;

		SecretKeyVo vo = keyBo.getSecretKeyVoInfo(appId);
		return vo;
	}

	/**
	 * 获取SyncDeviceVo对象。先从缓存Map中获取，如果获取不到，查询数据库并将查询结果放入缓存Map。
	 * @param deviceId
	 * @return
	 */
	public static SyncDeviceVo getSyncDevice(String deviceId)
	{
		if (StringUtils.isNullOrEmpty(deviceId))
			return null;

		SyncDeviceVo vo = deviceBo.getSyncDevice(deviceId);
		return vo;
	}

	/**
	 * 对数据进行加密，得到签名。加密规则：AES(MD5(content))
	 * @param content 源数据
	 * @param key 密钥
	 */
	public static String getSignByAESandMD5(String content, String key)
	{
		try
		{
			logger.info(String.format("【key.length(): %s】", key.length()));
			// 由于之前privatekey长度太长，修改为对key进行MD5之后作为新的key
			key = Coder.getHexStringByEncryptMD5(key);
			return NativeEncryptUtils.encryptAES(Coder.getHexStringByEncryptMD5(content), key);
		}
		catch (Exception e)
		{
			logger.error("AES(MD5(source)) 加密失败！", e);
			return null;
		}
	}
}
