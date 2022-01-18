package com.project.joopging.websocket.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    public enum MessageType {
        ENTER, COMM
    }

    private MessageType messageType;
    private String roomId;
    private String sender;
    private String message;
}
