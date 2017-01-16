package com.ank.webim.payload;

public class Message {
	private int messageid;	//消息ID
	private int version;	//消息版本号
	private int subject;	//消息主题
	private int result;		//消息结果0：成功 1：失败
	private int session;	//会话ID
	private Payload payload;//消息主体
	
	public Message() {
		// TODO Auto-generated constructor stub
	}

	public int getMessageid() {
		return messageid;
	}

	public void setMessageid(int messageid) {
		this.messageid = messageid;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getSubject() {
		return subject;
	}

	public void setSubject(int subject) {
		this.subject = subject;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
}
