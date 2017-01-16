package com.ank.webim.common.redis;

/**
 * redis 操作工具类
 */
import org.apache.log4j.Logger;

import com.ank.webim.common.config.Config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JredisClient {
	private static final Logger LOG = Logger.getLogger(JredisClient.class);
	
	private static volatile JredisClient instance = null;

	private JedisPool jedisPool;

	public JredisClient() {
		try {
			JedisPoolConfig jedisConfig = new JedisPoolConfig();
			jedisConfig.setMaxIdle(30);
			jedisConfig.setTestOnBorrow(false);
			jedisPool = new JedisPool(jedisConfig, Config.REDIS_HOST, Config.REDIS_PORT);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}

	public static JredisClient shareClient() {
		if(instance == null)
			synchronized(JredisClient.class){
				if(instance == null)
					instance = new JredisClient();
			}
		return instance;
	}
	
	public Jedis getJedis(){
		try {
			Jedis jedis = jedisPool.getResource();
			return jedis;
		} catch (Exception e) {
			LOG.error("get jedis fail", e);
			return null;
		}
	}
	
	public String get(String key){
		Jedis jedis = this.getJedis();
		return jedis.get(key);
	}
	
	public String set(String key, String value){
		Jedis jedis = this.getJedis();
		return jedis.set(key, value);
	}
	
	public Long hset(String key, String field, String value){
		Jedis jedis = this.getJedis();
		return jedis.hset(key, field, value);
	}
	
	public String hget(String key , String field){
		Jedis jedis = this.getJedis();
		return jedis.hget(key, field);
	}
}
