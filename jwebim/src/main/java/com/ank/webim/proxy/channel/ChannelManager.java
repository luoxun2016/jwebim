package com.ank.webim.proxy.channel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.log4j.Logger;

import com.ank.webim.message.Message;
import com.ank.webim.proxy.session.Session;
import com.ank.webim.proxy.session.SessionManager;

public class ChannelManager {
	private static final Logger LOG = Logger.getLogger(ChannelManager.class);
	private static final Map<Session, Channel> MAPPER = new HashMap<Session, Channel>();
	
	private static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
	private static final ReadLock 				READ_LOCK 		= READ_WRITE_LOCK.readLock();
	private static final WriteLock 				WRITE_LOCK 		= READ_WRITE_LOCK.writeLock();

	public static void putChannel(Session session, Channel ctx) {
		if(!ctx.isOpen()) return;
		
		try{
			WRITE_LOCK.lock();
			MAPPER.put(session, ctx);
		}finally{
			WRITE_LOCK.unlock();
		}
	}
	
	public static Channel getChannel(Session session) {
		try{
			READ_LOCK.lock();
			return MAPPER.get(session);
		}finally{
			READ_LOCK.unlock();
		}
	}
	
	public static void removeChannel(Session session){
		try{
			WRITE_LOCK.lock();
			MAPPER.remove(session);
		}finally{
			WRITE_LOCK.unlock();
		}
	}

	public static void writeMessage(Message message) {
		Session session = SessionManager.getSession(message.getSessionid());
		Channel channel = getChannel(session);
		if(channel != null){
			channel.writeMessage(message);
		}else{
			LOG.warn("Write Message:" + message + " Not Found Session");
		}
	}
}
