package com.ank.webim.message.payload;

import com.ank.webim.message.Payload;

public class User implements Payload{
	private int agentid;// 代理商ID
	private int type;// 用户类型
	private int userid;// 用户ID
	
	public User() {
		
	}
	
	public User(int agentid, int type, int userid) {
		super();
		this.agentid = agentid;
		this.type = type;
		this.userid = userid;
	}

	public int getAgentid() {
		return agentid;
	}

	public void setAgentid(int agentid) {
		this.agentid = agentid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public int getPayloadType() {
		return TYPE;
	}
	
	public static final int TYPE = 101;
}
