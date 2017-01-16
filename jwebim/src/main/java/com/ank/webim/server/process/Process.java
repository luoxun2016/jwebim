package com.ank.webim.server.process;

import com.ank.webim.message.Message;

public interface Process {

	Message onMessage(Message message);
	
}
