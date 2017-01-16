package com.ank.webim;

import com.ank.webim.proxy.core.ProxyServer;
import com.ank.webim.proxy.core.http.HttpProxyServer;
import com.ank.webim.proxy.core.tcp.TcpProxyServer;
import com.ank.webim.proxy.core.ws.WebsocketProxyServer;

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
	}
}
