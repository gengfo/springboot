package com.javalsj.blog.autoconfigure.websocket;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description WebSocket配置属性类 
 * @author WANGJIHONG
 * @date 2018年4月2日 下午9:37:22 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@ConfigurationProperties(prefix = "custom.websocket")
public class WebSocketProperties {
	/** 
	 * WebSocket STOMP代理中间件信息
	 */ 
	private StompBroker stompBroker;

	public StompBroker getStompBroker() {
		return stompBroker;
	}

	public void setStompBroker(StompBroker stompBroker) {
		this.stompBroker = stompBroker;
	}

	/**
	 * @description WebSocket STOMP代理中间件信息 
	 * @author WANGJIHONG
	 * @date 2018年4月2日 下午10:06:06 
	 * @Copyright 版权所有 (c) www.javalsj.com
	 */
	static class StompBroker {
		/** 
		 * 启用 STOMP代理中继来替换内存消息代理（false：表示不启用即使用内存消息代理；true：表示启用即使用STOMP代理中继，可使用第三方消息中间件。）
		 */  
		private boolean enabled = false;
		
		/** 
		 * STOMP代理中继主机IP地址
		 */ 
		private String relayHost = "127.0.0.1";

		/** 
		 * STOMP代理中继端口号
		 */ 
		private int relayPort = 61613;

		/** 
		 * 用户名
		 */ 
		private String clientLogin = "guest";

		/** 
		 * 密码
		 */ 
		private String clientPasscode = "guest";

		/** 
		 * 管理员用户名 
		 */ 
		private String systemLogin = "guest";

		/** 
		 * 管理员密码
		 */ 
		private String systemPasscode = "guest";
		
		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String getRelayHost() {
			return relayHost;
		}

		public void setRelayHost(String relayHost) {
			this.relayHost = relayHost;
		}

		public int getRelayPort() {
			return relayPort;
		}

		public void setRelayPort(int relayPort) {
			this.relayPort = relayPort;
		}

		public String getClientLogin() {
			return clientLogin;
		}

		public void setClientLogin(String clientLogin) {
			this.clientLogin = clientLogin;
		}

		public String getClientPasscode() {
			return clientPasscode;
		}

		public void setClientPasscode(String clientPasscode) {
			this.clientPasscode = clientPasscode;
		}

		public String getSystemLogin() {
			return systemLogin;
		}

		public void setSystemLogin(String systemLogin) {
			this.systemLogin = systemLogin;
		}

		public String getSystemPasscode() {
			return systemPasscode;
		}

		public void setSystemPasscode(String systemPasscode) {
			this.systemPasscode = systemPasscode;
		}
	}
	
}
