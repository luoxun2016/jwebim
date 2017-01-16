package com.ank.webim.server.session;

public class Session {
	private String sessionid;
	private String queueName;
	
	public Session(String sessionid, String queueName) {
		this.sessionid = sessionid;
		this.queueName = queueName;
	}
	
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
