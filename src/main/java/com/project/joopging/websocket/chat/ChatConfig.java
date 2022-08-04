package com.project.joopging.websocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@RequiredArgsConstructor
//@EnableWebSocket
@EnableWebSocketMessageBroker
//메세지 브로커에대한 설정
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

//    private final ChatHandler chatHandler;

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//
//        //endpoint ws/chat으로 설정
//        //CORS 열어주기
//        //클라이언트가 ws://localhost:8070/ws/chat으로 커넥션 연결하면 됨
//        //sockJS 허용 추가
          //스프링에서 웹소켓을 사용하려면 handler가 필요하므로 핸드쉐이크할 handler의 주소를 인자로 넣어주어야 함.
//        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
//    }


    @Override
    //클라이언트에서 websocket을 연결하는 api를 설정
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //addhandler와 비슷함.
        //해당 경로는 웹소켓의 핸드쉐이크를 위한 주소.
        // WebSocket 또는 SockJS Client가 웹소켓 핸드셰이크 커넥션을 생성할 경로
        //handler를 따로 설정할 필요가 없음
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //바로 Broker로 메세지 전달
        //해당 경로로 내장브로커를 사용하겠다 (SimpleBroker를 등록.)
        //메시지 구독 요청. 메세지 브로커가 해당 api경로를 구독하고있는 클라이언트에게 메세지를 전달
        //아래의 prefix가 붙은 메세지가 송신되었을 때 메세지 브로커가 처리하겠다
        // "/queue"는 1대1 메세지, "/topic"은 1대다일 때 주로  사용
        registry.enableSimpleBroker("/queue", "/topic");

        //바로 broker로 안가고 handler 거쳐서 가공/처리되도록
        // app 경로로 시작하는 STOMP 메세지의 "destination" 헤더는 @Controller 객체의 @MessageMapping 메서드로 라우팅된다.
        //메시지 발행 요청. Client에서 SEND 요청을 처리 서버에서 클라이언트로부터의 메세지를 받을 api의 prefix를 설정
        //내장된 메세지 브로커를 사용해 Client에게 Subscriptions, Broadcasting 기능을 제공 
        //도착 경로에 대한 prefix 설정
        // /topic/hello 라는 경로에 구독을 신청했을 경우 실제 경로는 /app/topic/hello가 된다
        registry.setApplicationDestinationPrefixes("/app");
    }
}
