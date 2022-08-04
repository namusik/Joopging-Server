package com.project.joopging.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    private String roomId;
    private String sender;
    private String message;
}
