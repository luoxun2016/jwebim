package com.ank.webim.proxy.handler;

import static com.ank.webim.message.Subject.SUBJECT_LOGIN;
import static com.ank.webim.message.Subject.SUBJECT_RECV_MESSAGE;
import static com.ank.webim.message.Subject.SUBJECT_VISTOR_INIT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import com.alibaba.fastjson.JSON;
import com.ank.webim.common.config.Config;
import com.ank.webim.common.mq.MQManager;
import com.ank.webim.exception.SessionNotFoundException;
import com.ank.webim.message.Message;
import com.ank.webim.proxy.attrkey.AttributeKeyChannel;
import com.ank.webim.proxy.channel.Channel;
import com.ank.webim.proxy.channel.ChannelManager;
import com.ank.webim.proxy.channel.QueueChannel;
import com.ank.webim.proxy.channel.SocketChannel;
import com.ank.webim.proxy.session.Session;
import com.ank.webim.proxy.session.SessionManager;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

public class ProxyProcessHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message) msg;

		if (message.getSubject() == SUBJECT_LOGIN || message.getSubject() == SUBJECT_VISTOR_INIT) {
			/** 用户登录、访客初始化 创建Session */
			Session session = SessionManager.createSession(message);

			/** 创建Channel */
			Channel channel = null;
			if (ctx.channel().hasAttr(AttributeKeyChannel.HTTP)) {
				channel = new QueueChannel(ctx);
			} else {
				channel = new SocketChannel(ctx);
			}
			/** 创建Sessoin和Channel的映射 */
			ChannelManager.putChannel(session, channel);
		} else {
			/** 其它请求判断Session是否存在 */
			Session session = SessionManager.getSession(message.getSessionid());
			if (session == null) {
				throw new SessionNotFoundException("Not Found Session");
			}

			/** 非轮询消息的请求在消息接收完毕后立即返回 */
			if (message.getSubject() == SUBJECT_RECV_MESSAGE) {
				/** 轮询消息 */
				Channel channel = ChannelManager.getChannel(session);
				Message m = channel.readMessage();
				ctx.writeAndFlush(m).addListener(ChannelFutureListener.CLOSE);
			} else {
				/** 判断HTTP请求 */
				if (ctx.channel().hasAttr(AttributeKeyChannel.HTTP)) {
					/** 非轮询消息的请求在消息接收完毕后立即返回 */
					if (message.getSubject() != SUBJECT_RECV_MESSAGE) {
						ctx.writeAndFlush(new DefaultFullHttpResponse(HTTP_1_1, OK)).addListener(ChannelFutureListener.CLOSE);
					}
				}
			}
		}

		/** 投递消息到Server端 */
		MQManager manager = MQManager.singleInstance();
		String queueName = Config.MQ_SERVER_MESSAGE;
		message.setQueueName(Config.MQ_PROXY_MESSAGE);
		manager.sendMessage(queueName, JSON.toJSONString(message));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}
}
