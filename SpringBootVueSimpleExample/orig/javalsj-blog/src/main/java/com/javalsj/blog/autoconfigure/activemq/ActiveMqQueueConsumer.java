package com.javalsj.blog.autoconfigure.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @description ActiveMQ队列消息消费者
 * @author WANGJIHONG
 * @date 2018年3月25日 下午10:59:10
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Component
public class ActiveMqQueueConsumer {

	private final static Logger logger = LoggerFactory.getLogger(ActiveMqQueueConsumer.class);

	/**
	 * WebSocket的Java老司机聊天室队列消息消费者
	 */
	@JmsListener(destination = ActiveMqQueueConst.WEBSOCKET_QUEUE_CHATROOM_JAVALSJ, containerFactory = ActiveMqConfiguration.BEAN_NAME_QUEUE_JMSLISTENERCONTAINERFACTORY)
	public void receiveQueueWebSocketJavalsjChatroomMsg(String message) {
		logger.info("消费了一条队列{}消息{}。", ActiveMqQueueConst.WEBSOCKET_QUEUE_CHATROOM_JAVALSJ, message);
	}

}
