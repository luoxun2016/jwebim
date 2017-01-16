package com.ank.webim.server.process;

import com.ank.webim.message.Message;
import com.ank.webim.message.Result;
import com.ank.webim.message.payload.Msg;
import com.ank.webim.message.payload.User;
import com.ank.webim.server.channel.ChannelManager;
import com.ank.webim.server.session.Session;
import com.ank.webim.server.session.SessionManager;

public class RouteProcess implements Process{

	public Message onMessage(Message message) {
		Msg msg = (Msg) message.getPayload();
		
		User toUser = new User(msg.getAgentid(),msg.getToType(),msg.getToMember());
		Session toSession = SessionManager.getSession(toUser);
		
		Message toMessage = new Message();
		if(toSession != null){
			message.setResult(Result.SUCCESS);
			
			toMessage.setMessageid(message.getMessageid());
			toMessage.setSubject(message.getSubject());
			toMessage.setVersion(message.getVersion());
			toMessage.setSessionid(toSession.getSessionid());
			toMessage.setQueueName(toSession.getQueueName());
			toMessage.setPayload(msg);
			ChannelManager.writeMessage(toMessage);
		}else{
			message.setResult(Result.FAIL);
		}
		return message;
	}
}
