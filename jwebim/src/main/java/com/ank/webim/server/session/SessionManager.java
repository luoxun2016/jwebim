package com.ank.webim.server.session;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.alibaba.fastjson.JSON;
import com.ank.webim.common.cache.CacheManager;
import com.ank.webim.common.redis.JredisClient;
import com.ank.webim.message.payload.User;

public class SessionManager {
	private static final CacheManager<Session> CACHE = new CacheManager<Session>("server_session", Session.class);
	
	private static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
	private static final ReadLock 				READ_LOCK 		= READ_WRITE_LOCK.readLock();
	private static final WriteLock 				WRITE_LOCK 		= READ_WRITE_LOCK.writeLock();

	public static void putSession(User user, Session session){
		if(session == null) return;
		
		String key = getKey(user);
		SessionManager.putSession(key, session);
	}
	
	private static void putSession(String key, Session session){
		try{
			WRITE_LOCK.lock();
			CACHE.put(key, session);
		}finally{
			WRITE_LOCK.unlock();
		}
		
		JredisClient client = JredisClient.shareClient();
		String value = JSON.toJSONString(session);
		client.set(key, value);
	}
	
	public static Session getSession(User user){
		Session session = null;
		
		String key = getKey(user);
		try{
			READ_LOCK.lock();
			session = CACHE.get(key);
		}finally{
			READ_LOCK.unlock();
		}
		
		try {
			if(session == null){
				JredisClient client = JredisClient.shareClient();
				String value = client.get(key);
				session = JSON.parseObject(value, Session.class);
				SessionManager.putSession(key, session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return session;
	}
	
	private static String getKey(User user){
		return String.format("user_%d_%d_%d", user.getAgentid(), user.getType(), user.getUserid());
	}
	
}
