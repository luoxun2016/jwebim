package com.ank.webim.proxy.server;

public abstract class ProxyServerAdapter implements ProxyServer{
	
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}
}
