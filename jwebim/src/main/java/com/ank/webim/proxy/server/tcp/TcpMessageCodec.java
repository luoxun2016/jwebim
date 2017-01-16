package com.ank.webim.proxy.server.tcp;

import java.util.List;

import com.ank.webim.message.Message;
import com.ank.webim.message.codec.MessageCodec;
import com.ank.webim.proxy.attrkey.AttributeKeyChannel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class TcpMessageCodec extends MessageToMessageCodec<ByteBuf, Message>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		/** 给Channel打上属性标签*/
		ctx.channel().attr(AttributeKeyChannel.TCP);
		
		/** 解码Mesage*/
		Message message = MessageCodec.decode(buffer);
		out.add(message);
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, List<Object> out) throws Exception {
		ByteBuf buffer = MessageCodec.encode(message);
		out.add(buffer);
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
