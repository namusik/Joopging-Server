package com.project.joopging.websocket.chat;

import com.project.joopging.dto.chat.ChatRoomRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping
    public void message(ChatMessage chatMessage) {
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getMessageType())) {
            ChatMessage firstChatMessage = new ChatMessage(chatMessage.getMessageType(), chatMessage.getRoomId(), chatMessage.getSender(), chatMessage.getSender() + "님이 입장했습니다");
            simpMessageSendingOperations.convertAndSend("/sub/comm/room" + chatMessage.getRoomId(), chatMessage);

        }
    }

    @PostMapping("api/chat")
    public ChatRoom createRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto) {
        String name = chatRoomRequestDto.getRoomId();
        return chatService.createRoom(name);
    }

    @GetMapping("api/chat")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

    @GetMapping("api/chat/{roomId}")
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatService.findById(roomId);
    }

}