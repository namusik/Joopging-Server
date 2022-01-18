package com.project.joopging.websocket.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.joopging.dto.chat.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper objectMapper;
    //생성된 모든 채팅방의 정보를 모아둔 map
    //DB로 옮기는 작업 필요
    private Map<String, ChatRoom> chatRooms;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 생성
    public ChatRoom createRoom(String name) {
        String roomId = name;
        return new ChatRoom(roomId);
    }

    //채팅보내기
    //지정한 Websocket 세션에 메시지를 발송
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}