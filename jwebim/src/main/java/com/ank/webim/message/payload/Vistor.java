package com.ank.webim.message.payload;

import com.ank.webim.message.Payload;

public class Vistor implements Payload{
	
	public int getPayloadType() {
		return TYPE;
	}
	
	public static final int TYPE = 102;
}
