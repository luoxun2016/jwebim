package com.ank.webim.common.cache;

public class CacheManager<T> {
	/** 内存缓存	 */
	private Cache<T> memoryCache;
	/**	物理缓存 */
	private Cache<T> diskCache;
	
	public CacheManager(String name, Class<T> clazz) {
		memoryCache = new LRUCache<T>();
		diskCache = new RedisCache<T>(name, clazz);
	}

	public T get(String key) {
		try {
			T value = memoryCache.get(key);
			
			if (value != null) return value;
			
			// 多线程进入为避免重复获取diskCache缓存降低性能加入同步块
			synchronized(memoryCache){
				value = memoryCache.get(key);
				if(value == null){
					value = diskCache.get(key);
					if(value != null){
						memoryCache.put(key, value);
					}
				}
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void put(String key, T value) {
		try {
			memoryCache.put(key, value);
			diskCache.put(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
