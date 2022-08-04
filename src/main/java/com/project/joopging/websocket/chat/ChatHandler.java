package com.project.joopging.websocket.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatHandler extends TextWebSocketHandler { //문자열 메시지 기반으로 진행하기 때문에 texthandler 상속

    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    //http의 session과는 다르다
    //웹소켓이 연결될 떄 생기는 연결정보를 담고 있는 객체
    //보통 collection으로 관리하는 경우가 많다
    //연결되는 add, 끊기면 remove
    private static List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        //client에 받은 메세지 log로 출력
        log.info("payload : {}" + payload);

        //Json String to Java Object ChatMessage
//        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
//        ChatRoom room = chatService.findById(chatMessage.getRoomId());
//        room.handleActions(session, chatMessage, chatService);


        // 클라에서 보낸 메세지 다시 출력
//        for(WebSocketSession sess: list) {
//            sess.sendMessage(message);
//        }
    }

    /* Client가 접속 시 호출되는 메서드 */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//
//        sessionList.add(session);
//
//        log.info(session + " 클라이언트 접속");
//    }
//
//    /* Client가 접속 해제 시 호출되는 메서드드 */
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//
//        log.info(session + " 클라이언트 접속 해제");
//        sessionList.remove(session);
//    }
}
