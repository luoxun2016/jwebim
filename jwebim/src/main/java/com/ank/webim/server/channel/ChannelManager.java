package com.ank.webim.server.channel;

import com.alibaba.fastjson.JSON;
import com.ank.webim.common.mq.MQManager;
import com.ank.webim.message.Message;

public class ChannelManager {
	public static void writeMessage(Message message) {
		if(message != null){
			MQManager manager = MQManager.singleInstance();
			String queueName = message.getQueueName();
			manager.sendMessage(queueName, JSON.toJSONString(message));
		}
	}
}
