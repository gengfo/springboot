/*package com.javalsj.blog.autoconfigure.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;

*//**
 * @description 采用Undertow作为服务器,支持Https服务配置
 * @author WANGJIHONG
 * @date 2018年3月7日 下午8:34:18
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 备注信息
 * 由于websocket兼容问题暂时停用Undertow作为服务器，否则报错
 *//*
@Configuration
@EnableConfigurationProperties({ ServerProperties.class, WebServerProperties.class })
public class UndertowWebServerAutoConfiguration {

	@Autowired
	private ServerProperties serverProperties;

	@Autowired
	private WebServerProperties webServerProperties;
	
	*//**
	 * 采用Undertow作为服务器。 Undertow是一个用java编写的、灵活的、高性能的Web服务器，提供基于NIO的阻塞和非阻塞API，特点：
	 * 非常轻量级，Undertow核心瓶子在1Mb以下。它在运行时也是轻量级的，有一个简单的嵌入式服务器使用少于4Mb的堆空间。
	 * 支持HTTP升级，允许多个协议通过HTTP端口进行多路复用。 提供对Web套接字的全面支持，包括JSR-356支持。 提供对Servlet
	 * 4.0的支持，包括对嵌入式servlet的支持。还可以在同一部署中混合Servlet和本机Undertow非阻塞处理程序。
	 * 可以嵌入在应用程序中或独立运行，只需几行代码。 通过将处理程序链接在一起来配置Undertow服务器。它可以对各种功能进行配置，方便灵活。
	 *//*
	@Bean
	public ServletWebServerFactory undertowFactory() {
		UndertowServletWebServerFactory undertowFactory = new UndertowServletWebServerFactory();
		undertowFactory.addBuilderCustomizers((Undertow.Builder builder) -> {
			// 设置http多端口监听
			webServerProperties.getHttpListenerPorts().stream().forEach((httpListerPort) -> {
				if (httpListerPort != null && httpListerPort != 0) {
					builder.addHttpListener(httpListerPort, "0.0.0.0");
				}
			});
			// 开启HTTP2
			builder.setServerOption(UndertowOptions.ENABLE_HTTP2, serverProperties.getHttp2().isEnabled());
		});
		undertowFactory.addDeploymentInfoCustomizers(deploymentInfo -> {
			deploymentInfo
					.addSecurityConstraint(new SecurityConstraint()
							.addWebResourceCollection(new WebResourceCollection().addUrlPattern("/*"))
							.setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
							.setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
					.setConfidentialPortManager(exchange -> serverProperties.getPort());
		});
		return undertowFactory;
	}

	*//**
	 * 配置WebSocket对应的Servlet支持异步请求
	 *//*
	@Bean
	@ConditionalOnClass(DispatcherServlet.class)
	public ServletRegistrationBean<DispatcherServlet> dispatcherWebsocketServlet(
			final DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean<DispatcherServlet> registration = new ServletRegistrationBean<>(dispatcherServlet);
		registration.setName("dispatcherWebsocketServlet");
		registration.addUrlMappings("/endpoint-websocket/*", "/websocket/*");
		registration.setAsyncSupported(true);
		registration.setLoadOnStartup(1);
		registration.setOrder(1);
		return registration;
	}
}
*/