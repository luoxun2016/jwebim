package com.ank.webim.proxy.server.http;

import org.apache.log4j.Logger;

import com.ank.webim.common.config.Config;
import com.ank.webim.proxy.server.ProxyServer;
import com.ank.webim.proxy.server.ProxyServerAdapter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpProxyServer extends ProxyServerAdapter {
	private static final Logger LOG = Logger.getLogger(ProxyServer.class);

	private static final int DEFAULT_PORT = Config.PROXY_HTTP_PORT;

	private int port;

	public HttpProxyServer() {
		this(DEFAULT_PORT);
	}

	public HttpProxyServer(int port) {
		this.port = port;
	}

	public void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.handler(new LoggingHandler(LogLevel.DEBUG));
			b.childHandler(new HttpChannelInitializer());
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture f = b.bind(port).sync();

			LOG.info("start proxy HTTP port " + port);

			f.channel().closeFuture().sync();
		} catch (Exception e) {
			LOG.error("start proxy HTTP port " + port + " fail", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
