package com.ank.webim.proxy.core.http;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ank.webim.payload.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class HttpMessageEecoder extends MessageToMessageEncoder<Message>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
		out.add(JSON.toJSONString(msg));
	}
	
}
