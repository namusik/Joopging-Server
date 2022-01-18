package com.project.joopging.websocket.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {

    private String roomId;
    //채팅방에 속해있는 클라이언트의 sessiong 정보를 담은 Set
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }
    
    //입장 통신기능 분기처리
    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        //메세지의 타입이 ENTER면 채팅방 세션Set 클라이언트의 session을 추가해주기
        if (chatMessage.getMessageType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
        }
        sendMessage(chatMessage, chatService);

    }

    //채팅방에 메시지가 도착할 경우  채팅방의 모든 session에 메시지를 발송
    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
