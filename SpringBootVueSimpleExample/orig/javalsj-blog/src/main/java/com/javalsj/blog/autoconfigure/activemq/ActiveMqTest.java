package com.javalsj.blog.autoconfigure.activemq;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @description 测试
 * @author WANGJIHONG
 * @date 2018年3月25日 下午11:41:03
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明 @Component
 */
public class ActiveMqTest {

	@Autowired
	private ActiveMqQueueProducer activeMQQueueProducer;

	@Autowired
	private ActiveMqTopicPublisher activeMQTopicPublisher;

	@Scheduled(fixedRate = 10000, initialDelay = 3000)
	public void test() {
		activeMQQueueProducer.sendMsg(ActiveMqQueueConst.WEBSOCKET_QUEUE_CHATROOM_JAVALSJ,
				"队列message" + Instant.now().toString());
		activeMQTopicPublisher.publishMsg(ActiveMqTopicConst.WEBSOCKET_TOPIC_SYSTEM_NOTICE,
				"主题message" + Instant.now().toString());
	}

}
