package com.ank.webim.proxy.attrkey;

import io.netty.util.AttributeKey;

public interface AttributeKeyChannel {
	public static final AttributeKey<String> HTTP 		= AttributeKey.valueOf("HTTP");
	public static final AttributeKey<String> TCP 		= AttributeKey.valueOf("TCP");
	public static final AttributeKey<String> WEBSOCKET 	= AttributeKey.valueOf("WEBSOCKET");
}
