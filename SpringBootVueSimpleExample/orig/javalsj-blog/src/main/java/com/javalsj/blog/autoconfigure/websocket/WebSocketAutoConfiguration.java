package com.javalsj.blog.autoconfigure.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @description 使用WebSocket为浏览器和服务器之间提供了双工异步通信功能
 * @author WANGJIHONG
 * @date 2018年3月19日 下午7:01:06
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo spring boot的消息功能是基于消息代理构建的，因此我们必须要配置一个消息代理 和 其他的一些消息目的地。 <br/>
 *       1.EnableWebSocketMessageBroker注解开启STOMP协议来传输代理消息，这个配置类不仅配置了
 *       WebSocket，还配置了基于代理的 STOMP消息。 <br/>
 *       2.实现WebSocketMessageBrokerConfigurer之后要实现的两个方法： <br/>
 *       registerStompEndpoints设置SockJS的节点，与前端JS中的代码要统一。 <br/>
 *       configureMessageBroker配置一个指定url的消息代理。
 */
@Configuration
@EnableConfigurationProperties(WebSocketProperties.class)
@EnableWebSocketMessageBroker
public class WebSocketAutoConfiguration implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private WebSocketProperties webSocketProperties;

	/**
	 * 注册一个Stomp协议的端点（endpoint）并指定使用SockJS协议，可跨域。 <br/>
	 * 端点的作用:客户端在订阅或发布消息到目的地址前，要先连接该端点， <br/>
	 * 如：在前端js中我们可以通过var websocket = new SockJS("/websocket-endpoint")来和服务器当前端点连接。
	 * 
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 注册Java老司机聊天室连接地址端点, 服务器要监听的端口，消息会从这里进来，要对这里加一个Handler,
		// 这样在网页中就可以通过websocket连接上服务了
		registry.addEndpoint("/endpoint-websocket")
				// 允许跨域
				.setAllowedOrigins("*")
				// // 自定义握手处理器
				// .setHandshakeHandler(new MyHandshakeHandler())
				// // 自定义握手拦截器
				// .addInterceptors(new MyHandshakeInterceptor())
				// 指定使用SockJS协议
				.withSockJS();
	}

	/**
	 * 配置消息代理和消息目的地，
	 * stomp协议，配置时注意一个地方，stomp协议使用的中继器(路由)或者叫消息中介，默认在configureMessageBroker方法中，
	 * registry.enableSimpleBroker是使用内存中介，不依赖第三方组件，
	 * registry.enableStompBrokerRelay是使用第三方中间件，需要事先下载安装中间件，如activeMQ/RabbitMQ.本例子中使用内存中继。
	 * 
	 * <br/>
	 * 1.registry.enableSimpleBroker：内存消息代理，不依赖第三方组件 。<br/>
	 * 如:registry.enableSimpleBroker("/topic"", "/queue"); 并将其代理目的地前缀设置为"/topic"和
	 * "/queue" ，发送到 代理上的消息如@sendTo注解的地址所形成的消息，将会路由到该代理上，并最终发送到订阅这些目的地客户端。
	 * 
	 * <br/>
	 * 2.registry.enableStompBrokerRelay：启用 STOMP代理中继来替换内存消息代理，支持 STOMP 的代理来支持
	 * WebSocket 消息，如RabbitMQ 或 ActiveMQ，
	 * 这样的代理提供了可扩展性和健壮性更好的消息功能，当然，他们也支持STOMP命令，适合生产环节下使用。<br/>
	 * 如：registry.enableStompBrokerRelay("/topic",
	 * "/queue")表示所有目的地为"/topic"和"/queue"开头的URL都会路由到此消息代理队列或主题中，供订阅的客户端消费使用。;
	 * 
	 * 
	 * <br/>
	 * 3.registry.setApplicationDestinationPrefixes：设置客户端发送消息的URL路径前缀。
	 * 如：registry.setApplicationDestinationPrefixes("/websocket")，表示所有目的地以
	 * "/websocket" 开头的客户端请求消息URL都会路由到带有 @MessageMapping 注解的方法中，而不会发布到消息代理队列或主题中；
	 * 如：stompClient.send("/websocket/sendMsgToChatRoom", {},messageJson)，
	 * 该请求会映射到服务端控制器注解为@MessageMapping("/sendMsgToChatRoom")的 方法中。
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/websocket");
		
		String[] destinationPrefixes = {"/websocket-topic", "/websocket-queue", "/websocket-chat"};
		if (webSocketProperties.getStompBroker().isEnabled()) {
			// 服务端启用了STOMP代理中继功能， 代理中继将消息传送到消息中间件来进行处理；
			registry
			// 消息中间件的主题标识、队列标识
			.enableStompBrokerRelay(destinationPrefixes)
			// 消息中间件主机
			.setRelayHost(webSocketProperties.getStompBroker().getRelayHost())
			// 消息中间件端口
			.setRelayPort(webSocketProperties.getStompBroker().getRelayPort())
			// 消息中间件用户名/消息中间件密码
			.setClientLogin(webSocketProperties.getStompBroker().getClientLogin())
			.setClientPasscode(webSocketProperties.getStompBroker().getClientPasscode())
			.setSystemLogin(webSocketProperties.getStompBroker().getSystemLogin())
			.setSystemPasscode(webSocketProperties.getStompBroker().getSystemPasscode())
			// 设置心跳信息接收时间间隔
			.setSystemHeartbeatReceiveInterval(2000)
			// 设置心跳信息发送时间间隔
			.setSystemHeartbeatSendInterval(2000);
		} else {
			// 内存消息代理
			registry.enableSimpleBroker(destinationPrefixes);
		}
	}

	/**
	 * 消息传输参数配置
	 *//*
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry
				// 设置消息字节数大小
				.setMessageSizeLimit(8192)
				// 设置消息缓存大小
				.setSendBufferSizeLimit(8192)
				// 设置消息发送时间限制毫秒
				.setSendTimeLimit(10000);
	}

	*//**
	 * 输入通道参数设置
	 *//*
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// 线程信息
		registration.taskExecutor()
				// 设置消息输入通道的线程池线程数
				.corePoolSize(4)
				// 最大线程数
				.maxPoolSize(8)
				// 线程活动时间
				.keepAliveSeconds(60);
	}

	*//**
	 * 输出通道参数配置
	 *//*
	@Override
	public void configureClientOutboundChannel(ChannelRegistration registration) {
		// 线程信息
		registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
	}

	*//**
	 * 消息转换器配置
	 *//*
	@Override
	public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
		return true;
	}

	*//**
	 * @description 自定义握手处理器
	 * @author WANGJIHONG
	 * @date 2018年3月22日 下午10:05:59
	 * @Copyright 版权所有 (c) www.javalsj.com
	 * @memo 无备注说明
	 *//*
	static final class MyHandshakeHandler extends DefaultHandshakeHandler {

		*//**
		 * 该方法可以重写用来为用户 添加标识
		 * 
		 * @return 返回principal
		 *//*
		@Override
		protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
				Map<String, Object> attributes) {
			return super.determineUser(request, wsHandler, attributes);
		}
	}

	*//**
	 * @description 自定义 STOMP握手拦截器
	 * @author WANGJIHONG
	 * @date 2018年3月22日 下午10:06:40
	 * @Copyright 版权所有 (c) www.javalsj.com
	 * @memo 无备注说明
	 *//*
	static final class MyHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

		private static final Logger logger = LoggerFactory.getLogger(MyHandshakeInterceptor.class);

		@Override
		public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
				WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
			logger.info("===============before handshake=============");
			return super.beforeHandshake(request, response, wsHandler, attributes);
		}

		@Override
		public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
				Exception ex) {
			logger.info("===============after handshake=============");
			super.afterHandshake(request, response, wsHandler, ex);
		}

	}
*/
}