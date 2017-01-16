package com.ank.webim.message.codec;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.ank.webim.message.Message;
import com.ank.webim.message.Payload;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Message序列化与反序列化
 * 序列化数据格式为JSON
 */
public class MessageCodec {
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	static{
		/**自定义Payload反序列化*/
		ParserConfig.getGlobalInstance().putDeserializer(Payload.class, new PayloadJSONDeserializer());
	}
	
	/**
	 * 解码
	 * @param buffer
	 * @return
	 */
	public static Message decode(ByteBuf buffer) {
		String text = buffer.toString(DEFAULT_CHARSET);
		Message message = decodeString(text);
		return message;
	}
	
	/**
	 * 编码
	 * @param message
	 * @return
	 */
	public static ByteBuf encode(Message message) {
		String text = encodeString(message);
		ByteBuf buffer = Unpooled.wrappedBuffer(text.getBytes(DEFAULT_CHARSET));
		return buffer;
	}
	
	/**
	 * 解码
	 * @param buffer
	 * @return
	 */
	public static Message decodeString(String text) {
		Message message = JSON.parseObject(text, Message.class);
		return message;
	}
	
	/**
	 * 编码
	 * @param message
	 * @return
	 */
	public static String encodeString(Message message) {
		String text = JSON.toJSONString(message);
		return text;
	}
}
