package com.project.joopging.dto.chat;

import com.project.joopging.websocket.chat.ChatService;
import lombok.Getter;
import org.aspectj.bridge.Message;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoomDto {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoomDto(String roomId) {
        this.roomId = roomId;
    }

}
