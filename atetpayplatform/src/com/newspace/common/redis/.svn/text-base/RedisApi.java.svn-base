package com.newspace.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.newspace.common.log.JLogger;
import com.newspace.common.log.LoggerUtils;

import redis.clients.jedis.Jedis;

/**
 * @description 操作Redis的接口类 
 * @author Huqili
 * @date 2016年7月18日
 *
 */
public enum RedisApi{

	INSTANCE;

	private static final JLogger logger = LoggerUtils.getLogger(RedisApi.class);
	
	/**
	 * 设置一个key的value值
	 * @param key  键
	 * @param value 键对应的值
	 */
	public void setValue(String key, String value)   
	{
		Jedis jedis = null;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.set(key, value);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_setValue 插入或更新字符中时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
	}

	/**
	 * 获取key的值
	 * @param key  
	 * @throws Exception 
	 */
	public String getValue(String key)  
	{
		Jedis jedis = null;
		String value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.get(key);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_getValue 从Redis中获取value值时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		return value;
	}

	/**
	 * 自增键值
	 * @param key
	 */
	public void incr(String key)  
	{
		Jedis jedis = null;
		try 
		{
			jedis = RedisUtils.getJedis();
			jedis.incr(key);
		} 
		catch (Exception e) 
		{
			logger.error("RedisApi_incr 自增键值时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
	}
	
	/**
	 * 一次性返回多个键的值
	 * @param keys
	 * @return
	 */
	public List<String> mget(String... keys) 
	{
		Jedis jedis = null;
		List<String> list = null;
		try
		{
			jedis = RedisUtils.getJedis();
			list = jedis.mget(keys);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_mget 一次性查询多个键的值时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return list;
	}
	
	/**
	 * 从redis中删除某一些键值对应的值
	 * @param key  要删除的键的集合
	 */
	public void del(String... keys)  
	{
		Jedis jedis = null;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.del(keys);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_del 删除key所对应的值时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
	}

	/**
	 * 设置hash字段的值
	 * @param key  插入的键
	 * @param mapData  插入的map
	 */
	public void hmset(String key, Map<String, String> mapData)
	{
		Jedis jedis = null;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.hmset(key, mapData);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_hmset 插入或更新map类型数据时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
	}

	/**
	 * 从hash集中读取全部的域和值
	 * @param key 
	 */
	
	public Map<String, String> hgetAll(String key)  
	{
		Jedis jedis = null;
		Map<String, String> resultMap = null;
		try
		{
			jedis = RedisUtils.getJedis();
			resultMap = (Map<String, String>) jedis.hgetAll(key);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_hgetAll 从Redis中获取某个键值对应的map时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}

		return resultMap;
	}

	/**
	 * 读取哈希域的值
	 * @param key  
	 * @param mapKey 
	 */
	public String hget(String key, String mapKey)
	{
		Jedis jedis = null;
		String value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.hget(key, mapKey);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_hget 从Redis中获取map中某个对应键的值",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}

		return value;
	}

	/**
	 * 设置hash里面一个字段的值
	 * @param key  
	 * @param mapKey 
	 */
	public void hset(String key, String mapKey, String value)
	{
		Jedis jedis = null;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.hset(key, mapKey, value);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_hset 设置hash里面一个字段的值时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
	}

	
	/**
	 * 删除一个或多个哈希域
	 * @param key
	 * @param mapKeys
	 */
	public void hdel(String key, String... mapKeys)
	{
		Jedis jedis = null;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.hdel(key, mapKeys);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_hdel 删除一个或多个哈希域时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
	}

	/**
	 * 当队列存在时，从队列左边插入一个元素
	 * @param key
	 * @param value
	 */
	public void lpush(String key, String... value)  
	{
		Jedis jedis = null;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.lpush(key, value);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_lpush 从队列左边插入元素时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
	}

	/**
	 * 从队列的左边获取一个元素
	 * @param key
	 * @return
	 */
	public String lpop(String key)  
	{
		Jedis jedis = null;
		String value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.lpop(key);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_lpop 从列队的左边获取元素时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	/**
	 * 当队列存在时，从队列右边插入一个元素
	 * @param key
	 * @param values
	 */
	public void rpush(String key, String... values)  
	{
		Jedis jedis = null;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.rpush(key, values);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_rpush 从列队右边插入元素时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
	}

	/**
	 * 从队列右边获取一个元素
	 * @param key
	 * @return
	 */
	public String rpop(String key)  
	{
		Jedis jedis = null;
		String value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.rpop(key);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_rpop 从列队的右边获取元素时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	/**
	 * 从列表中获取指定返回的元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end)
	{
		Jedis jedis = null;
		List<String> list = null;
		try
		{
			jedis = RedisUtils.getJedis();
			list = jedis.lrange(key, start, end);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_lrange 从列表中获取指定返回的元素时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return list;
	}

	/**
	 * 获取一个元素，通过其索引一个列表
	 * @param key
	 * @param index
	 * @return
	 */
	public String lindex(String key, long index)  
	{
		Jedis jedis = null;
		String value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.lindex(key, index);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_lindex 通过元素索引列表时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	/**
	 * 添加一个或多个元素到Set集合里
	 * @param key
	 * @param members
	 */
	public void sadd(String key, String... members)  
	{
		Jedis jedis = null;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.sadd(key, members);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_sadd 添加元素到set集合时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
	}

	
	/**
	 * 删除并获取一个集合里的元素
	 * @param key
	 * @return
	 */
	public String spop(String key)  
	{
		Jedis jedis = null;
		String value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.spop(key);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_spop 删除集合里的元素时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	
	/**
	 * 获取集合里的所有key
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key)  
	{
		Jedis jedis = null;
		Set<String> set = null;
		try
		{
			jedis = RedisUtils.getJedis();
			set = jedis.smembers(key);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_smembers 获取集合里所有key时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return set;
	}

	
	/**
	 * 确定一个给定的值是否是集合中的成员
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(String key, String member)  
	{
		Jedis jedis = null;
		boolean flag = true;
		try
		{
			jedis = RedisUtils.getJedis();
			jedis.sismember(key, member);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_sismember 判断member是否是集合中的key时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return flag;
	}

	/**
	 * 从集合中删除一个或多个key
	 * @param key
	 * @param members
	 * @return
	 */
	public Long srem(String key, String... members)  
	{
		Jedis jedis = null;
		Long value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.srem(key, members);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_srem 从集合中删除key时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	
	/**
	 * 获取集合里面的元素数量
	 * @param key
	 * @return
	 */
	public Long scard(String key)  
	{
		Jedis jedis = null;
		Long value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.scard(key);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_scard 获取集合里面的元素数量时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	
	/**
	 * 添加或更新一个成员 到有序set
	 * @param key
	 * @param score 每个成员所对应 的分数
	 * @param member
	 * @return
	 */
	public Long zadd(String key, Double score, String member)
	{
		Jedis jedis = null;
		Long value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.zadd(key, score, member);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_zadd 添加或更新一个成员 到有序set时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}
	
	
	/**
	 * 添加或更新一个或多个成员 到有序set中
	 * @param key 
	 * @param score  成员所对应的分数
	 * @param scoreMembers
	 * @return
	 */
	public Long zadd(String key, Double score, Map<String, Double> scoreMembers)
	{
		Jedis jedis = null;
		Long value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.zadd(key, scoreMembers);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_zadd 添加或更新一个或多个成员到有序set时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	/**
	 * 返回有序集元素的个数
	 * @param key
	 * @return
	 */
	public Long zcard(String key)  
	{
		Jedis jedis = null;
		Long value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.zcard(key);
		}
		catch (Exception e)
		{
			 logger.error("RedisApi_zcard 返回有序集元素个数时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}


	/**
	 * 被移除的成员的数量
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByRank(String key, long start, long end)
	{
		Jedis jedis = null;
		Long value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.zremrangeByRank(key, start, end);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_zremrangeByRank 获取被移除成员数量时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	
	/**
	 * 删除的元素的个数
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByScore(String key, double start, double end)
			 
	{
		Jedis jedis = null;
		Long value = null;
		try
		{
			jedis = RedisUtils.getJedis();
			value = jedis.zremrangeByScore(key, start, end);
			
		}
		catch (Exception e)
		{
			logger.error("RedisApi_zremrangeByScore 获取删除元素个数时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return value;
	}

	/**
	 * 返回指定范围的元素列表
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrange(String key, long start, long end)
			 
	{
		Jedis jedis = null;
		Set<String> set = null;
		try
		{
			jedis = RedisUtils.getJedis();
			set = jedis.zrange(key, start, end);
		}
		catch (Exception e)
		{
			logger.error("RedisApi_zrange 返回指定范围元素列表时出错！",e);
		}
		finally
		{
			RedisUtils.returnResource(jedis);
		}
		
		return set;
	}


}
