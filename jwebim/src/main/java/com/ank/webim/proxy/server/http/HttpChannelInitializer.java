package com.ank.webim.proxy.server.http;

import com.ank.webim.proxy.handler.ProxyProcessHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

public class HttpChannelInitializer extends ChannelInitializer<SocketChannel>{
	
    private SslContext sslCtx;
    
    public HttpChannelInitializer() {
    	this(null);
	}

    public HttpChannelInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }
    

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(65536));//消息聚合
        p.addLast(new HttpContentCompressor());//HTTP压缩
        p.addLast(new HttpMessageCodec());
        p.addLast(new ProxyProcessHandler());
	}
	
}
