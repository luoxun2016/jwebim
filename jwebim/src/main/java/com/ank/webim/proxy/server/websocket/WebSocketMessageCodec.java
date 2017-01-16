package com.ank.webim.proxy.server.websocket;

import java.util.List;

import com.ank.webim.message.Message;
import com.ank.webim.message.codec.MessageCodec;
import com.ank.webim.proxy.attrkey.AttributeKeyChannel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class WebSocketMessageCodec extends MessageToMessageCodec<WebSocketFrame, Message>{

	@Override
	protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
		/** 给channel打上属性标签*/
		ctx.channel().attr(AttributeKeyChannel.WEBSOCKET);
		
		/** 解码Mesage*/
        if (frame instanceof TextWebSocketFrame) {
            ByteBuf buffer = ((TextWebSocketFrame) frame).content();
            Message message = MessageCodec.decode(buffer);
            out.add(message);
        } else {
            throw new UnsupportedMessageTypeException("unsupported frame type: " + frame.getClass().getName());
        }
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, List<Object> out) throws Exception {
		ByteBuf buffer = MessageCodec.encode(message);
		TextWebSocketFrame frame = new TextWebSocketFrame(buffer);
        out.add(frame);
	}

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
