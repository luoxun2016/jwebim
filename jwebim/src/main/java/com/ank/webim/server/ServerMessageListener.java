package com.ank.webim.server;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.ank.webim.message.Message;
import com.ank.webim.message.codec.MessageCodec;
import com.ank.webim.server.channel.ChannelManager;
import com.ank.webim.server.process.ProcessManager;

public class ServerMessageListener implements MessageListener{
	private static final Logger LOG = Logger.getLogger(ServerMessageListener.class);

	public void onMessage(javax.jms.Message message) {
		try {
			TextMessage textMessage = (TextMessage)message;
			Message m = MessageCodec.decodeString(textMessage.getText());
			Message result = ProcessManager.process(m);
			ChannelManager.writeMessage(result);
		} catch (JMSException e) {
			LOG.error("Server onMessage fail", e);
		}
	}

}
