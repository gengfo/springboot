package com.javalsj.blog.autoconfigure.activemq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @description ActiveMQ主题消息订阅者
 * @author WANGJIHONG
 * @date 2018年3月25日 下午11:22:50
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Component
public class ActiveMqTopicSubscriber {

	private final static Logger logger = LoggerFactory.getLogger(ActiveMqTopicSubscriber.class);

	@JmsListener(destination = ActiveMqTopicConst.WEBSOCKET_TOPIC_SYSTEM_NOTICE, containerFactory = ActiveMqConfiguration.BEAN_NAME_TOPIC_JMSLISTENERCONTAINERFACTORY)
	public void subscribeTopicWebsocketSystemNoticeMsg(String message) {
		logger.info("消费了一条主题{}消息{}。", ActiveMqTopicConst.WEBSOCKET_TOPIC_SYSTEM_NOTICE, message);
	}
}
