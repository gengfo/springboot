package com.javalsj.blog.autoconfigure.activemq;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @description ActiveMQ主题消息发布者
 * @author WANGJIHONG
 * @date 2018年3月25日 下午11:19:45 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Component
public class ActiveMqTopicPublisher {
	private final static Logger logger = LoggerFactory.getLogger(ActiveMqTopicPublisher.class);

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	/** 
	 * 发布主题消息
	 */
	public void publishMsg(String destinationName, String message) {
		logger.info("发布了一条主题{}消息{}。", destinationName, message);
		Destination destination = new ActiveMQTopic(destinationName);
		jmsMessagingTemplate.convertAndSend(destination, message);
	}
}
