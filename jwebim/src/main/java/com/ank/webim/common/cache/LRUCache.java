package com.ank.webim.common.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 采用末位访问淘汰制实现内存缓存
 */
public class LRUCache<T> implements Cache<T> {
	private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

	private static final int DEFAULT_MAX_CAPACITY = 1 << 16;

	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	private Map<String, T> cache = null;

	public LRUCache() {
		this(DEFAULT_MAX_CAPACITY);
	}

	public LRUCache(final int maxCapacity) {
		cache = new LinkedHashMap<String, T>(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR, true) {
			private static final long serialVersionUID = -4017210350001281109L;

			@Override
			protected boolean removeEldestEntry(Entry<String, T> eldest) {
				return this.size() > maxCapacity;
			}
		};
	}

	public T get(String key) {
		return cache.get(key);
	}

	public void put(String key, T value) {
		cache.put(key, value);
	}
}
