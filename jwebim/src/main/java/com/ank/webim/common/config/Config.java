package com.ank.webim.common.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {
	private static final Logger LOG = Logger.getLogger(Config.class);

	private static final String CONFIG_NAME = "config.properties";
	private static final Properties CONFIG;
	
	/** jdbc */
	public static final String JDBC_USER;
	public static final String JDBC_PASS;
	public static final String JDBC_URL;

	/** redis */
	public static final String REDIS_HOST;
	public static final Integer REDIS_PORT;

	/** ActiveMQ */
	public static final String MQ_USERNAME;
	public static final String MQ_PASSWORD;
	public static final String MQ_BROKER_URL;
	public static final String MQ_SERVER_MESSAGE;
	public static final String MQ_PROXY_MESSAGE;
	

	/** proxy config */
	public static final Integer PROXY_HTTP_PORT;
	public static final Integer PROXY_TCP_PORT;
	public static final Integer PROXY_WEBSOCKET_PORT;

	/** server config */

	static {
		try {
			LOG.info("Configuration Init!");
			CONFIG = new Properties();
			CONFIG.load(ClassLoader.getSystemResourceAsStream(CONFIG_NAME));

			/** jdbc */
			JDBC_USER = getString("jdbc.user");
			JDBC_PASS = getString("jdbc.pass");
			JDBC_URL = getString("jdbc.url");

			/** redis */
			REDIS_HOST = getString("redis.host");
			REDIS_PORT = getInteger("redis.port");

			/** ActiveMQ */
			MQ_USERNAME = getString("mq.username");
			MQ_PASSWORD = getString("mq.password");
			MQ_BROKER_URL = getString("mq.broker.url");
			MQ_SERVER_MESSAGE = getString("mq.server.message");
			MQ_PROXY_MESSAGE = getString("mq.proxy.message");

			/** proxy config */
			PROXY_HTTP_PORT = getInteger("proxy.http.port", 58080);
			PROXY_TCP_PORT = getInteger("proxy.tcp.port", 58081);
			PROXY_WEBSOCKET_PORT = getInteger("proxy.websocket.port", 58082);

			/** server config */
		} catch (IOException e) {
			LOG.error(CONFIG_NAME + " load error!", e);
			throw new RuntimeException(e);
		}
	}

	private static String getString(String name) {
		return getString(name, null);
	}

	private static Integer getInteger(String name) {
		return getInteger(name, null);
	}

	private static String getString(String name, String defaultValue) {
		return CONFIG.getProperty(name, defaultValue);
	}

	private static Integer getInteger(String name, Integer defaultValue) {
		String value = CONFIG.getProperty(name, null);
		if (value == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(value);
		}
	}

	public static String[] getStringArray(String name) {
		String value = getString(name, null);
		if (value != null) {
			return value.split(",");
		}
		return null;
	}
}
