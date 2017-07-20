package com.newspace.common.redis;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;
import com.newspace.common.utils.PropertiesUtils;

/**
 * {@link RedisUtils.java}
 * @description: redis缓存工具类
 * @author Huqili
 * @date 2016年7月18日
 * @since JDK1.6, commons-pool2.jar
 *
 */
public class RedisUtils {

	private static final JLogger logger = LoggerUtils.getLogger(RedisUtils.class);
	
	//redis连接池对象
	private static final JedisPool JEDISPOOL;
	
	//redis服务器IP
	private static final String REDIS_IP;
	
	//reids服务器端口，默认为6379
	private static final int REDIS_PORT; 
	
	//redis最大空闲连接数，默认为10
	private static final int REDIS_MAXIDLE;

	//redis最大连接数
	private static final int REDIS_MAXCONN;

	//redis最大等待时间 ，默认为3000
	private static final int REDIS_MAXWAITMILLIS;
	
	
	/**
	 * 构建Redis连接池
	 */
	static
	{
		Properties prop = PropertiesUtils.getProps("configuration");
		
		REDIS_IP = prop.getProperty("redis_ip");
		REDIS_PORT = Integer.parseInt(prop.getProperty("redis_port"));
		REDIS_MAXIDLE = Integer.parseInt(prop.getProperty("redis_maxidle"));
		REDIS_MAXCONN = Integer.parseInt(prop.getProperty("redis_maxconn"));
		REDIS_MAXWAITMILLIS = Integer.parseInt(prop.getProperty("redis_maxwaitmillis"));
		
		//配置redis连接池
		JedisPoolConfig config = new JedisPoolConfig();
		
		config.setMaxIdle(REDIS_MAXIDLE);
		config.setMaxTotal(REDIS_MAXCONN);
		config.setMaxWaitMillis(REDIS_MAXWAITMILLIS);
		config.setTestOnBorrow(true);
		
		logger.info("开始初始化Redis线程池...");
		long t = System.currentTimeMillis();
		System.out.println("init jedispool....");
		JEDISPOOL = new JedisPool(config, REDIS_IP, REDIS_PORT);
		
		long t1 = System.currentTimeMillis();
		logger.info("初始化线程池所用的时间是："+ (t1 - t)+"ms");
	}
	
	/**
	 * 从连接池中获取Redis对象
	 * @return
	 * @throws MyCacheException
	 */
	@SuppressWarnings("deprecation")
	public synchronized static Jedis getJedis()
	{
		Jedis jedis = null;
		try
		{
			jedis = JEDISPOOL.getResource();
		}
		catch (Exception e)
		{
			logger.error("RedisUtils 从连接池获取redis对象失败！",e);
			if (jedis != null)
			{
				logger.error("抛弃存在异常的Redis实例......");
				//释放redis对象
				JEDISPOOL.returnBrokenResource(jedis);
			}
		}
		return jedis;
	}
	
	
	/**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    @SuppressWarnings("deprecation")
	public static void returnResource(Jedis jedis) {
    	try
		{
			if (null != jedis)
			{
				JEDISPOOL.returnResource(jedis);
			}
		}
		catch (Exception e)
		{
			logger.error("RedisUtils 返回redis资源到连接池时出错！",e);
		}
    }
}
