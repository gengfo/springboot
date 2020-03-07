package com.javalsj.blog.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javalsj.blog.autoconfigure.websocket.WebSocketRequestMessage;
import com.javalsj.blog.autoconfigure.websocket.WebSocketResponseMessage;
import com.javalsj.blog.autoconfigure.websocket.WebSocketService;

/**
 * @description WebSocket聊天室控制器
 * @author WANGJIHONG
 * @date 2018年3月19日 下午8:22:20
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@Controller
public class ChatRoomContoller {

	@Autowired
	private WebSocketService webSocketService;

	@RequestMapping("/websocket/chatroom")
	public String chatroom(Model model) throws Exception {
		model.addAttribute("chatroom_all_message","");
		model.addAttribute("chatroom_javalsj_message","");
		model.addAttribute("chatroom_python_message","");
		return "websocket/chatroom";
	}

	/**
	 * 精准推送，推送消息至Java老司机讨论组 @MessageMapping("/send-message-to-chatroom-javalsj")
	 * 表示服务端接收客户端通过主题“/websocket/send-message-to-chatroom-javalsj”发送过来的消息(服务端接收消息接口)，
	 * 如：控制器的@MessageMapping("/sendMsgToChatRoom")注解，
	 * 则前端发送方法为：stompClient.send("/websocket/send-message-to-chatroom-javalsj", {},
	 * messageJson)。 @SendTo("/websocket/chatroom-javalsj")
	 * 表示这个函数执行完成后服务端会把响应数据广播到"/topic/chatroom-javalsj"这个主题，
	 * 前端如果订阅了stompClient.subscribe('/websocket-queue/chatroom-javalsj',
	 * function(response){})这个主题就会收到这个响应数据信息。
	 */
	@MessageMapping("/send-message-to-chatroom-javalsj")
	@SendTo("/websocket-queue/chatroom-javalsj")
	public WebSocketResponseMessage sendQueueMsgToJavalsjChatroom(WebSocketRequestMessage requestMessage)
			throws Exception {
		WebSocketResponseMessage responseMessage = new WebSocketResponseMessage(Instant.now().toString(),
				"服务器收到消息：" + requestMessage.getMsg());
		return responseMessage;
	}

	/**
	 * 推送消息至Python老司机讨论组
	 */
	@MessageMapping("/send-message-to-chatroom-pythonlsj")
	@SendTo(value = "/websocket-queue/chatroom-pythonlsj")
	public String sendQueueMsgToPythonlsjChatroom(String msg, Principal principal) {
		return "精准推送，只推送到" + principal.getName();
	}

	/**
	 * 广播消息至所有讨论组
	 */
	@MessageMapping("/send-message-to-chatroom-all")
	@SendTo(value = "/websocket-topic/chatroom-all")
	public String sendTopicMsgToAllChatroom(String msg, Principal principal) {
		webSocketService.sendServerInfo();
		return "精准推送，只推送到" + principal.getName();
	}

}
