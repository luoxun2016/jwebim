package com.ank.webim.proxy.core.tcp;

import org.apache.log4j.Logger;

import com.ank.webim.proxy.core.ProxyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TcpProxyServer implements ProxyServer {
	private static final Logger LOG = Logger.getLogger(ProxyServer.class);

	private static final int DEFAULT_PORT = 58081;

	private int port;

	public TcpProxyServer() {
		this(DEFAULT_PORT);
	}

	public TcpProxyServer(int port) {
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
			b.childHandler(new TcpChannelInitializer());
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture f = b.bind(port).sync();

			LOG.info("start proxy TCP port " + port);

			f.channel().closeFuture().sync();
		} catch (Exception e) {
			LOG.error("start proxy TCP port " + port + " fail", e);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

}