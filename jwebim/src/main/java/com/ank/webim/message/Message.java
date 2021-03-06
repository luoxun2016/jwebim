package com.ank.webim.message;

import com.ank.webim.message.codec.MessageCodec;

public class Message {
	private int messageid;		//消息ID
	private int version;		//消息版本号
	private int subject;		//消息主题
	private int result;			//消息结果0：成功 1：失败
	private String sessionid;	//会话ID
	private Payload payload;	//消息主体
	
	private String queueName;	//队列名称
	
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
	
	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@Override
	public String toString() {
		return MessageCodec.encodeString(this);
	}
}
