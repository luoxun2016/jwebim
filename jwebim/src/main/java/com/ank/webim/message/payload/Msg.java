package com.ank.webim.message.payload;

import com.ank.webim.message.Payload;

public class Msg implements Payload{
	private int agentid;//代理商ID
	private int toMember;//接收方ID
	private int toType;//接收方类型
	private int fromMember;//发送发ID
	private int fromType;//发送方类型
	private String body;//消息体

	public int getAgentid() {
		return agentid;
	}

	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	public int getToMember() {
		return toMember;
	}

	public void setToMember(int toMember) {
		this.toMember = toMember;
	}

	public int getToType() {
		return toType;
	}

	public void setToType(int toType) {
		this.toType = toType;
	}

	public int getFromMember() {
		return fromMember;
	}

	public void setFromMember(int fromMember) {
		this.fromMember = fromMember;
	}

	public int getFromType() {
		return fromType;
	}

	public void setFromType(int fromType) {
		this.fromType = fromType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getPayloadType() {
		return TYPE;
	}
	
	public static final int TYPE = 103;
}
