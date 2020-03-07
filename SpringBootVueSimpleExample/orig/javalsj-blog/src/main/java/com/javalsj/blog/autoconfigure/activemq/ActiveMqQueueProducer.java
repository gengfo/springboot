package com.javalsj.blog.autoconfigure.activemq;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @description ActiveMQ消息生产者
 * @author WANGJIHONG
 * @date 2018年3月25日 下午10:57:54
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Component
public class ActiveMqQueueProducer {
	
	private final static Logger logger = LoggerFactory.getLogger(ActiveMqQueueProducer.class);

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	/** 
	 * 发送队列消息
	 * @param destinationName 消息目的地标识
	 * @param message 消息文本
	 */ 
	public void sendMsg(String destinationName, String message) {
		logger.info("发布了一条队列{}消息{}。", destinationName, message);
		Destination destination = new ActiveMQQueue(destinationName);
		jmsMessagingTemplate.convertAndSend(destination, message);
	}
	
}
