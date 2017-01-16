package com.ank.webim.proxy.channel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.ank.webim.message.Message;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class QueueChannel implements Channel{
	private static final Logger LOG = Logger.getLogger(QueueChannel.class);
	
	private ChannelHandlerContext context = null;
	
	private BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
	
	public QueueChannel( ChannelHandlerContext context ) {
		this.context = context;
	}

	public boolean isOpen() {
		return true;
	}

	public void writeMessage(Message message) {
		try {
			if(this.context != null){
				this.context.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE);
				this.context = null;
				return;
			}
			queue.put(message);
		} catch (InterruptedException e) {
			LOG.error("QueueChannel write message fail");
		}
	}

	public Message readMessage() {
		try {
			return queue.poll(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			return null;
		}
	}

}
