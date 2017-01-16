package com.ank.webim.proxy.server.http;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.COOKIE;
import static io.netty.handler.codec.http.HttpHeaderNames.SET_COOKIE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.List;
import java.util.Set;

import com.ank.webim.exception.MessageDecodeException;
import com.ank.webim.exception.UnsupportedHttpRequestMethodException;
import com.ank.webim.message.Message;
import com.ank.webim.message.codec.MessageCodec;
import com.ank.webim.proxy.attrkey.AttributeKeyChannel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;

public class HttpMessageCodec extends MessageToMessageCodec<HttpObject, Message> {
	/** Buffer that stores the response content */
	private ByteBuf buffer = Unpooled.buffer();
	private HttpRequest request;

	/**
	 * HttpRequest to Message
	 */
	@Override
	protected void decode(ChannelHandlerContext ctx, HttpObject httpObject, List<Object> out) throws Exception {
		/** 给Channel打上属性标签*/
		ctx.channel().attr(AttributeKeyChannel.HTTP);
		
		/** 接收Request*/
		if (httpObject instanceof HttpRequest) {
			buffer.clear();
			request = (HttpRequest) httpObject;
			
			/** 过滤非POST请求 */
			if (request.method() != HttpMethod.POST) {
				throw new UnsupportedHttpRequestMethodException(request.method().name());
			}
		}

		/** 接收Content*/
		if (httpObject instanceof HttpContent) {
			ByteBuf content = ((HttpContent) httpObject).content();
			if (content.isReadable()) {
				buffer.writeBytes(content, content.readerIndex(), content.readableBytes());
			}
			
			/** 解码Message */
			if (httpObject instanceof LastHttpContent) {
				try {
					Message message = MessageCodec.decode(buffer);
					out.add(message);
				} catch (Exception e) {
					throw new MessageDecodeException("Message decode fail");
				}
			}
		}
	}

	/**
	 * Message to HttpResponse
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, Message message, List<Object> out) throws Exception {
		ByteBuf content = MessageCodec.encode(message);

		/** 设置Header头信息 */
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, content);
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.headers().set(CONTENT_LENGTH, content.readableBytes());

		/** 设置Cookie */
		String cookieString = request.headers().get(COOKIE);
		if (cookieString != null) {
			Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieString);
			if (!cookies.isEmpty()) {
				for (Cookie cookie : cookies) {
					response.headers().add(SET_COOKIE, ServerCookieEncoder.STRICT.encode(cookie));
				}
			}
		}
		out.add(response);
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
