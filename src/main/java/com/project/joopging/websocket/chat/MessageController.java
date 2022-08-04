package com.project.joopging.websocket.chat;

import com.project.joopging.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
    
    //특정 Borker로 메세지 전달
    private final SimpMessageSendingOperations sendingOperations;


    //ChatConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //Handler의 역할을 대신해서 Handler는 필요없어진다
    //@messagemapping은 @requestMapping과 비슷한 역할. 메세지의 destination해더와 @messagemapping 경로가 일치하는 handler를 찾고 처리하게 됨
    //config에서 설정한 prefix /app과 합쳐진다 /app/chat/enter
    @MessageMapping("/chat/message")
    //이 handler에서 처리를 마친 메세지를 /topic/greeting이란 경로로 다시 메세지를 보내겠다. 앞에 /topic이 붙어있으니까 simplebroker로 전달된다
//    @SendTo("/topic/greeting")
    public void enter(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);

    }

    //실제경로는 /app/chat/message
//    @MessageMapping("/chat/message")
//    public void message(ChatMessageDto messageDto) {
//        sendingOperations.convertAndSend("/topic/chat/room/"+messageDto.getRoomId(), messageDto);
//    }

}
