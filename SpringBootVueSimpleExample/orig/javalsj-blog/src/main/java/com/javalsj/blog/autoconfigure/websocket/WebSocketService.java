package com.javalsj.blog.autoconfigure.websocket;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @description TODO(这里用一句话描述这个类的作用) 
 * @author WANGJIHONG
 * @date 2018年4月2日 下午10:19:22 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Service
public class WebSocketService {
	
	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * 功能描述：获取系统信息，推送给客户端
	 */
	public void sendServerInfo() {
		int processors = Runtime.getRuntime().availableProcessors();
		Long freeMem = Runtime.getRuntime().freeMemory();
		Long maxMem = Runtime.getRuntime().maxMemory();
		String message = String.format("服务器可用处理器:%s; 虚拟机空闲内容大小: %s; 最大内存大小: %s", processors,freeMem,maxMem );
		template.convertAndSend("/websocket-topic/server_info",new WebSocketResponseMessage(Instant.now().toString(), message));
		
	}
}
