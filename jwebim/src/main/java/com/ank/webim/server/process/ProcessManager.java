package com.ank.webim.server.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ank.webim.message.Message;
import com.ank.webim.message.Subject;

public class ProcessManager {
	private static final Logger LOG = Logger.getLogger(ProcessManager.class);
	
	private static final Map<Integer, Process> MAPPER = new HashMap<Integer, Process>();
	
	static{
		MAPPER.put(Subject.SUBJECT_LOGIN, new LoginProcess());
		MAPPER.put(Subject.SUBJECT_SEND_MESSAGE, new RouteProcess());
	}

	public static Message process(Message message){
		Process process = MAPPER.get(message.getSubject());
		try {
			if(process != null){
				Message result = process.onMessage(message);
				return result;
			}
		} catch (Exception e) {
			LOG.error("ProcessManager process fail", e);
		}
		return null;
	}
	
}
