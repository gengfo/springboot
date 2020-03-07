package com.javalsj.blog.autoconfigure.activemq;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;

/**
 * @description ActiveMQ消息队列配置类
 * @author WANGJIHONG
 * @date 2018年3月25日 下午10:52:26
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Configuration
public class ActiveMqConfiguration {
	/**
	 * 在Queue模式中，对消息的监听需要对containerFactory进行配置，工厂标识
	 */
	public static final String BEAN_NAME_QUEUE_JMSLISTENERCONTAINERFACTORY = "queueJmsListenerContainerFactory";
	
	/**
	 * 在Topic模式中，对消息的监听需要对containerFactory进行配置，工厂标识
	 */
	public static final String BEAN_NAME_TOPIC_JMSLISTENERCONTAINERFACTORY = "topicJmsListenerContainerFactory";

	/**
	 * 在Queue模式中，对消息的监听需要对containerFactory进行配置
	 */
	@Bean(BEAN_NAME_QUEUE_JMSLISTENERCONTAINERFACTORY)
	public JmsListenerContainerFactory<?> queueJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(false);
		return factory;
	}

	/**
	 * 在Topic模式中，对消息的监听需要对containerFactory进行配置
	 */
	@Bean(BEAN_NAME_TOPIC_JMSLISTENERCONTAINERFACTORY)
	public JmsListenerContainerFactory<?> topicJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}
}
