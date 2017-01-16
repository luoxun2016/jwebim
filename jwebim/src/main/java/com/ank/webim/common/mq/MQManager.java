package com.ank.webim.common.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.log4j.Logger;

import com.ank.webim.common.config.Config;

public class MQManager {
	private static final Logger LOG = Logger.getLogger(MQManager.class);
	
	// 默认连接数
	public final static int DEFAULT_MAX_CONNECTIONS = 5;
	// 默认每个连接中使用的最大活动会话数
	public final static int DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION = 300;
	// 默认强制使用同步返回数据的格式
	public final static boolean DEFAULT_USE_ASYNC_SEND_FOR_JMS = true;
	// 默认是否持久化消息
	public final static boolean DEFAULT_IS_PERSISTENT = true;

	// ActiveMQ连接地址
	private String brokerUrl;
	// ActiveMQ用户名
	private String username;
	// ActiveMQ密码
	private String password;

	// 设置连接的最大连接数
	private int maxConnections;
	// 设置每个连接中使用的最大活动会话数
	private int maximumActiveSessionPerConnection;
	// 强制使用同步返回数据的格式
	private boolean useAsyncSendForJMS;
	// 是否持久化消息
	private boolean isPersistent;

	// ActiveMQ连接工厂
	private PooledConnectionFactory connectionFactory;

	private static volatile MQManager instance;

	/** 创建单例 */
	public static MQManager singleInstance() {
		if (instance == null) {
			synchronized (MQManager.class) {
				if (instance == null)
					instance = new MQManager();
			}
		}
		return instance;
	}

	private MQManager() {
		super();
		this.brokerUrl = Config.MQ_BROKER_URL;
		this.username = Config.MQ_USERNAME;
		this.password = Config.MQ_PASSWORD;
		this.maxConnections = DEFAULT_MAX_CONNECTIONS;
		this.maximumActiveSessionPerConnection = DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION;
		this.useAsyncSendForJMS = DEFAULT_USE_ASYNC_SEND_FOR_JMS;
		this.isPersistent = DEFAULT_IS_PERSISTENT;

		this.init();
	}

	private void init() {
		// ActiveMQ的连接工厂
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(this.username, this.password, this.brokerUrl);
		factory.setUseAsyncSend(this.useAsyncSendForJMS);
		// Active中的连接池工厂
		this.connectionFactory = new PooledConnectionFactory(factory);
		this.connectionFactory.setCreateConnectionOnStartup(true);
		this.connectionFactory.setMaxConnections(this.maxConnections);
		this.connectionFactory.setMaximumActiveSessionPerConnection(this.maximumActiveSessionPerConnection);
	}

	public void sendMessage(String queueName, String text) {
		Connection connection = null;
		Session session = null;
		try {
			connection = this.connectionFactory.createConnection();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(this.isPersistent ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);
			TextMessage message = new ActiveMQTextMessage();
			message.setText(text);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			close(session);
			close(connection);
		}
	}

	public void listenMessage(String queueName, MessageListener messageListener) {
		Connection connection = null;
		Session session = null;
		try {
			connection = this.connectionFactory.createConnection();
			connection.start();
			// 会话采用非事务级别，消息到达机制使用自动通知机制
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(messageListener);
			LOG.info("listen MQ " + queueName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void close(Session session) {
		try {
			if (session != null)
				session.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private void close(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
