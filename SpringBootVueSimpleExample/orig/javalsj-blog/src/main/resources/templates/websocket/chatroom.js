var stompClient;
	var isConnected = false;
	/* 连接服务 */
	function connectServer() {
		// 建立连接Java老司机聊天室通信端点，连接SockJS的endpoint是“/endpoint-websocket”，与服务端代码WebSocketMessageBrokerConfigurer.registerStompEndpoints(StompEndpointRegistry)中注册的endpoint要一样。
		var websocket = new SockJS("/endpoint-websocket");
		// 获取STOMP子协议的客户端对象
		stompClient = Stomp.over(websocket);
		// 客户端向服务端发起websocket连接并发送CONNECT帧
		stompClient.connect({},
			// 连接成功时（服务器响应 CONNECTED 帧）的回调方法
			function connectSuccess(frame) {
				isConnected = true;
				alert("您成功连接聊天室。");
			},
			// 连接失败时（服务器响应 ERROR 帧）的回调方法
			function connectFault(error) {
				isConnected = false;
				alert("您连接聊天室失败，原因： " + error);
			}
		);
		/* 连接成功后订阅消息,通过stompClient.subscribe()订阅服务器的目标与后台代码中的@SendTo中的地址对应。 */
		if (isConnected) {
			// 订阅所有讨论组消息
			subscribe('/websocket-topic/chatroom-all', function (response) {
				var returnData = JSON.parse(response.body);
				setMsgInnerHTML("chatroom-all", "收到消息:" + returnData.responseMessage);
			});
			// 订阅Java老司机讨论组消息
			subscribe('/websocket-queue/chatroom-javalsj', function (response) {
				var returnData = JSON.parse(response.body);
				setMsgInnerHTML("chatroom-javalsj", "收到消息:" + returnData.responseMessage);
			});
			// 订阅Python老司机讨论组消息
			subscribe('/websocket-queue/chatroom-pythonlsj', function (response) {
				var returnData = JSON.parse(response.body);
				setMsgInnerHTML("chatroom-pythonlsj", "收到消息:" + returnData.responseMessage);
			});
		};
	};

	/* 断开服务 */
	function disConnectserver() {
		if (stompClient != null) {
			stompClient.disconnect();
			setMsgInnerHTML("您离开聊天室！");
		}
	};

	/* 订阅websocket消息  */
	function subscribe(subscribeDestination, callback) {
		if (stompClient != null) {
			stompClient.subscribe(subscribeDestination, callback);
		}
	}

	/* 取消订阅websocket消息  */
	function unSubscribe(subscription) {
		if (subscription != null) {
			subscription.unsubscribe();
		}
	}

	/* 发送消息至讨论组 */
	function sendMsgToChatRoom(sendMessageDestination) {
		if (stompClient == null) {
			return;
		}
		var message = document.getElementById('todo-message').value;
		var messageJson = JSON.stringify({
			"msg": message
		});
		// 通过stompClient.send()向服务器地址发起请求，与后台代码中的@MessageMapping(/send-message-to-chatroom-all)里的地址对应。
		// stompClient.send第一个参数：json 负载消息发送的 目的地； 第二个参数：是一个头信息的Map，它会包含在 STOMP 帧中；第三个参数：负载消息；
		stompClient.send(sendDestination, {}, messageJson);
	};

	/* 将消息显示在网页聊天室中上 */
	function setMsgInnerHTML(chatRoomId, innerHTML) {
		document.getElementById(chatRoomId).innerHTML += innerHTML + '<br/>';
	};