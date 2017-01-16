package com.ank.webim.proxy.channel;

import org.apache.log4j.Logger;

import com.ank.webim.message.Message;

import io.netty.channel.ChannelHandlerContext;

public class SocketChannel implements Channel{
	private static final Logger LOG = Logger.getLogger(SocketChannel.class);
	
	private ChannelHandlerContext context;
	
	public SocketChannel(ChannelHandlerContext context) {
		this.context = context;
	}

	public boolean isOpen() {
		return this.context.channel().isOpen();
	}

	public void writeMessage(Message message) {
		try {
			if(isOpen()){
				this.context.channel().write(message);
			}else{
				LOG.error("SocketChannel is close");
			}
		} catch (Exception e) {
			LOG.error("SocketChannel write message fail");
		}
	}

	public Message readMessage() {
		throw new UnsupportedOperationException();
	}

}
