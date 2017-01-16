package com.ank.webim.proxy.core.ws;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;

public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel>{

    private static final String WEBSOCKET_PATH = "/websocket";

    private SslContext sslCtx;
    
    public WebSocketChannelInitializer() {
    	this(null);
	}

    public WebSocketChannelInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpServerCodec());
        p.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
    }
	
}
