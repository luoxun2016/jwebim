package com.ank.webim.server.process;

import com.ank.webim.message.Message;
import com.ank.webim.message.Result;
import com.ank.webim.message.payload.Login;
import com.ank.webim.message.payload.User;
import com.ank.webim.server.service.UserService;
import com.ank.webim.server.session.Session;
import com.ank.webim.server.session.SessionManager;

public class LoginProcess implements Process{
	private UserService service = new UserService();

	public Message onMessage(Message message) {
		Login login = (Login) message.getPayload();
		String username = login.getUsername();
		String password = login.getPassword();
		
		User user = service.getUser(username, password);
		if(user != null){
			message.setResult(Result.SUCCESS);
			message.setPayload(user);
			
			Session session = new Session(message.getSessionid(), message.getQueueName());
			SessionManager.putSession(user, session);
		}else{
			message.setResult(Result.FAIL);
		}
		return message;
	}
	
}
