package com.javalsj.blog.autoconfigure.websocket;

/**
 * @description WebSockect服务端消息
 * @author WANGJIHONG
 * @date 2018年3月19日 下午8:44:00
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class WebSocketResponseMessage {
	
	/**
	 * 服务端消息时间
	 */
	private String date;
	
	/**
	 * 服务端消息信息
	 */
	private String msg;

	public WebSocketResponseMessage(String date, String msg) {
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
