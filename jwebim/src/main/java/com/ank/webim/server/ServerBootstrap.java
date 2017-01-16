package com.ank.webim.server;

import com.ank.webim.Bootstrap;
import com.ank.webim.common.config.Config;
import com.ank.webim.common.mq.MQManager;

public class ServerBootstrap implements Bootstrap{
	
	public void start(){
		/** 启动发往Server消息队列消费者*/
		MQManager manager = MQManager.singleInstance();
		String queueName = Config.MQ_SERVER_MESSAGE;
		manager.listenMessage(queueName, new ServerMessageListener());
	}
}
