package com.ank.webim.message.payload;

import com.ank.webim.message.Message;
import com.ank.webim.message.Subject;
import com.ank.webim.message.codec.MessageCodec;

public class MsgTest {

	public static void main(String[] args) {
		Msg msg = new Msg();
		msg.setAgentid(5);
		msg.setFromType(1);
		msg.setFromMember(1);
		msg.setToType(1);
		msg.setToMember(1);
		msg.setBody("消息类容");
		
		Message message = new Message();
		message.setVersion(1);
		message.setMessageid(1);
		message.setSessionid("6107890607");
		message.setSubject(Subject.SUBJECT_SEND_MESSAGE);
		message.setPayload(msg);
		
		String s = message.toString();
		Message m = MessageCodec.decodeString(s);
		System.out.println(m);
	}
}
