package com.ank.webim.message.payload;

import com.ank.webim.message.Message;
import com.ank.webim.message.Subject;
import com.ank.webim.message.codec.MessageCodec;

public class LoginTest {

	public static void main(String[] args) {
		Login login = new Login();
		login.setUsername("luoxun");
		login.setPassword("123456");
		Message message = new Message();
		message.setVersion(1);
		message.setMessageid(1);
		message.setSubject(Subject.SUBJECT_LOGIN);
		message.setPayload(login);
		
		String s = message.toString();
		Message m = MessageCodec.decodeString(s);
		System.out.println(m);
	}
}
