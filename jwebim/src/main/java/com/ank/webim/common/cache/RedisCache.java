package com.ank.webim.common.cache;

import com.alibaba.fastjson.JSON;
import com.ank.webim.common.redis.JredisClient;

/**
 * 采用redis缓存数据
 */
public class RedisCache<T> implements Cache<T> {
	private String name;
	private Class<T> clazz;
	private JredisClient client = JredisClient.shareClient();

	public RedisCache(String name, Class<T> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public T get(String key) {
		String text = client.hget(name, key);
		if(text != null){
			return JSON.parseObject(text, clazz);
		}else{
			return null;
		}
	}

	public void put(String key, T value) {
		String text = JSON.toJSONString(value);
		client.hset(name, key, text);
	}
}
