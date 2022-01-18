package com.project.joopging.dto.chat;

import lombok.Getter;

@Getter
public class ChatMessageDto {
    //채팅방 입장, 채팅방에 메세지 보내기 두가지 상황
    public enum MessageType {
        ENTER, COMM
    }

    private MessageType messageType;
    private Long roomId;
    private String sender;
    private String message;
}
