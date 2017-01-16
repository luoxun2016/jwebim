package com.ank.webim.common.cache;

public interface Cache<T> {
	
	T get(String key);
	
	void put(String key, T value);
}
