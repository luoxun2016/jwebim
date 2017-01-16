package com.ank.webim.server.service;

import java.util.Random;

import com.ank.webim.message.payload.User;

public class UserService {

	public User getUser(String username, String password){
		User user = new User();
		user.setAgentid(5);
		user.setType(1);
		Random random = new Random(System.currentTimeMillis());
		user.setUserid(random.nextInt(100));
		return user;
	}
	
}
