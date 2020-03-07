package com.javalsj.blog.autoconfigure.websocket;

/**
 * @description WebSockect客户端消息
 * @author WANGJIHONG
 * @date 2018年3月19日 下午8:43:46
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class WebSocketRequestMessage {
	/**
	 * 客户端消息时间
	 */
	private String date;
	/**
	 * 客户端消息信息
	 */
	private String msg;

	public WebSocketRequestMessage(String date, String msg) {
		this.date = date;
		this.msg = msg;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
