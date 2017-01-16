package com.ank.webim.message.codec;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.ank.webim.message.Payload;

/**
 * 自定义Payload反序列化
 * @需优化
 */
public class PayloadJSONDeserializer implements ObjectDeserializer{
	private static final String PROXY_TYPE = "payloadType";
	
	@SuppressWarnings("unchecked")
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		Payload payload = null;
		
		JSONObject object = parser.parseObject();
		if(object.containsKey(PROXY_TYPE)){
			int payloadType = object.getInteger(PROXY_TYPE);
			Type clazz = PayloadRegister.getType(payloadType);
			if(clazz != null){
				payload = JSON.parseObject(object.toJSONString(), clazz);
			}
		}
		
		return (T) payload;
	}

	public int getFastMatchToken() {
		return JSONToken.LBRACE;
	}
}