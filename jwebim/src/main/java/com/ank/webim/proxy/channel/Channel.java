package com.ank.webim.proxy.channel;

import com.ank.webim.message.Message;

public interface Channel {
	
	boolean isOpen();
	
	void writeMessage(Message message);
	
	Message readMessage();
}
