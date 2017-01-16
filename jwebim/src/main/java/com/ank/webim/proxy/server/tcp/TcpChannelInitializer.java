package com.ank.webim.proxy.server.tcp;

import com.ank.webim.proxy.handler.ProxyProcessHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

public class TcpChannelInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new DelimiterBasedFrameDecoder(65536, Delimiters.lineDelimiter()));
        p.addLast(new TcpMessageCodec());
        p.addLast(new ProxyProcessHandler());
	}
	
}
