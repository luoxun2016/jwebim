package com.ank.webim.proxy.session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.ank.webim.message.Message;

public class SessionManager {
	private static final Map<String, Session> MAPPER = new HashMap<String, Session>();
	
	private static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
	private static final ReadLock 				READ_LOCK 		= READ_WRITE_LOCK.readLock();
	private static final WriteLock 				WRITE_LOCK 		= READ_WRITE_LOCK.writeLock();

	public static Session createSession(Message message) {
		Session session = new Session();
		message.setSessionid(session.getSessionid());
		try{
			WRITE_LOCK.lock();
			MAPPER.put(session.getSessionid(), session);
		}finally{
			WRITE_LOCK.unlock();
		}
		return session;
	}

	public static Session getSession(String sessionid) {
		try{
			READ_LOCK.lock();
			return MAPPER.get(sessionid);
		}finally{
			READ_LOCK.unlock();
		}
	}

}
