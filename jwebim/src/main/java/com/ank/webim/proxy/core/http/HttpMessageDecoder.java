package com.ank.webim.proxy.core.http;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ank.webim.payload.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class HttpMessageDecoder extends MessageToMessageDecoder<HttpObject>{
    /** Buffer that stores the response content */
    private final StringBuilder buf = new StringBuilder();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
    
    @Override
    protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
    	if(msg instanceof HttpRequest){
    		buf.setLength(0);
    	}
    	
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;

            ByteBuf content = httpContent.content();
            if (content.isReadable()) {
                buf.append(content.toString(CharsetUtil.UTF_8));
            }

            if (msg instanceof LastHttpContent) {
            	String text = buf.toString();
            	Message message = JSON.parseObject(text, Message.class);
            	out.add(message);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
