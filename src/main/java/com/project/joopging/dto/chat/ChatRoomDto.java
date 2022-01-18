package com.project.joopging.dto.chat;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoomDto {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();


}
