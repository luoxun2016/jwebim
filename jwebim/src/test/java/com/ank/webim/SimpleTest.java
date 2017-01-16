package com.ank.webim;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.ank.webim.payload.Login;
import com.ank.webim.payload.Message;

public class SimpleTest {
	
	public static void main(String[] args) {
		Login payload = new Login();
		payload.setUsername("luoxun");
		payload.setPassword("123");
		
		Message message = new Message();
		message.setMessageid(1);
		message.setVersion(1);
		message.setSubject(1);
		message.setResult(1);
		message.setPayload(payload);
		
		String json = JSON.toJSONString(message);
		
		System.out.println(json);
		
		message = JSON.parseObject(json, Message.class, new ExtraProcessor() {
			public void processExtra(Object object, String key, Object value) {
				System.out.println(object);
				System.out.println(key);
				System.out.println(value);
				
				System.out.println("=============");
			}
		});
		System.out.println(message.getPayload());
	}
	
}
