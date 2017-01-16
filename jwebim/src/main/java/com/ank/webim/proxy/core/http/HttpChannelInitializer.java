package com.ank.webim.proxy.core.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
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
        p.addLast(new HttpRequestDecoder());
        // Uncomment the following line if you don't want to handle HttpChunks.
        //p.addLast(new HttpObjectAggregator(1048576));
        p.addLast(new HttpResponseEncoder());
        // Remove the following line if you don't want automatic content compression.
        //p.addLast(new HttpContentCompressor());
        p.addLast(new HttpMessageDecoder());
        p.addLast(new HttpMessageEecoder());
        
	}
	
}
