package com.ank.webim.message.codec;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.ank.webim.message.payload.Login;
import com.ank.webim.message.payload.Msg;
import com.ank.webim.message.payload.User;
import com.ank.webim.message.payload.Vistor;

public class PayloadRegister {
	private static final Map<Integer, Type> MAPPER = new HashMap<Integer, Type>(); 
	
	static{
		MAPPER.put(Login.TYPE, Login.class);
		MAPPER.put(User.TYPE, User.class);
		MAPPER.put(Vistor.TYPE, Vistor.class);
		MAPPER.put(Msg.TYPE, Msg.class);
	}
	
	public static Type getType(int subject){
		return MAPPER.get(subject);
	}
}
