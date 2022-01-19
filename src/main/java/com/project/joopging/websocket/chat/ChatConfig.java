package com.project.joopging.websocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@RequiredArgsConstructor
//@EnableWebSocket
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

//    private final ChatHandler chatHandler;

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//
//        //endpoint ws/chat으로 설정
//        //CORS 열어주기
//        //클라이언트가 ws://localhost:8070/ws/chat으로 커넥션 연결하면 됨
//        //sockJS 허용 추가
//        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
//    }


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //메시지 구독 요청
        registry.enableSimpleBroker("/sub");
        //메시지 발행 요청
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
