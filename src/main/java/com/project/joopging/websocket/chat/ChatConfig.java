package com.project.joopging.websocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class ChatConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        //endpoint ws/chat으로 설정
        //CORS 열어주기
        //클라이언트가 ws://localhost:8070/ws/chat으로 커넥션 연결하면 됨
        //sockJS 허용 추가
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }
}
