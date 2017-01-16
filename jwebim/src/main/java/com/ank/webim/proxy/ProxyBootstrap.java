package com.ank.webim.proxy;

import com.ank.webim.Bootstrap;
import com.ank.webim.common.config.Config;
import com.ank.webim.common.mq.MQManager;
import com.ank.webim.proxy.server.ProxyServer;
import com.ank.webim.proxy.server.http.HttpProxyServer;
import com.ank.webim.proxy.server.tcp.TcpProxyServer;
import com.ank.webim.proxy.server.websocket.WebsocketProxyServer;

public class ProxyBootstrap implements Bootstrap {

	public void start() {
		/** 启动HTTP代理服务 */
		ProxyServer httpProxyServer = new HttpProxyServer();
		httpProxyServer.start();

		/** 启动TCP代理服务 */
		ProxyServer tcpProxyServer = new TcpProxyServer();
		tcpProxyServer.start();

		/** 启动Websocket代理服务 */
		ProxyServer websocketProxyServer = new WebsocketProxyServer();
		websocketProxyServer.start();
		
		/** 启动发往Proxy的消息队列消费者*/
		MQManager manager = MQManager.singleInstance();
		String queueName = Config.MQ_PROXY_MESSAGE;
		manager.listenMessage(queueName, new ProxyMessageListener());
	}
}
