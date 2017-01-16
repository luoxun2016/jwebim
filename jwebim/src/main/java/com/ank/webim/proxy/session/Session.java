package com.ank.webim.proxy.session;

import java.util.Random;

public class Session {
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	
	private String sessionid;
	
	public Session() {
		this.sessionid = String.valueOf(0xFFFFFFFFL + (RANDOM.nextInt() & 0x7FFFFFFF));
	}

	public String getSessionid() {
		return this.sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
}
