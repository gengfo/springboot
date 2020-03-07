package com.javalsj.blog.autoconfigure.webserver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description TODO(这里用一句话描述这个类的作用) 
 * @author WANGJIHONG
 * @date 2018年4月2日 下午11:21:56 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
@ConfigurationProperties("custom.server")
public class WebServerProperties {
	
	/**
	 * http协议监听端口（访问这些监听http端口会自动跳转到系统启动端口，实现跳转功能）
	 */
	private List<Integer> httpListenerPorts = new ArrayList<>();

	public List<Integer> getHttpListenerPorts() {
		return httpListenerPorts;
	}

	public void setHttpListenerPorts(List<Integer> httpListenerPorts) {
		this.httpListenerPorts = httpListenerPorts;
	}

}
