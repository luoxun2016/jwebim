package com.ank.webim.proxy;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.ank.webim.message.Message;
import com.ank.webim.message.codec.MessageCodec;
import com.ank.webim.proxy.channel.ChannelManager;

public class ProxyMessageListener implements MessageListener{
	private static final Logger LOG = Logger.getLogger(ProxyMessageListener.class);

	public void onMessage(javax.jms.Message message) {
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Message result = MessageCodec.decodeString(text);
			ChannelManager.writeMessage(result);
		} catch (JMSException e) {
			LOG.error("Proxy onMessage fail", e);
		}
	}
}
