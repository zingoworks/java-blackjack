package com.codesquad.blackjack.service;

import com.codesquad.blackjack.socket.GameSession;
import com.codesquad.blackjack.socket.SocketRequest;
import com.codesquad.blackjack.socket.SocketResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static com.codesquad.blackjack.domain.ResponseType.DEALERTURN;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private ObjectMapper objectMapper;

    public void processToDealerTurn(GameSession gameSession) {
        sendToAll(new SocketResponse<>(DEALERTURN, null), gameSession);
    }

    public <T> void sendToAll(T messageObject, GameSession gameSession) {
        gameSession.getSessions().forEach(session -> {
            try {
                send(messageObject, session);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public <T> void send(T messageObject, WebSocketSession session) throws IOException {
        log.debug("send : {}", messageObject);

        //TODO 예외처리, synchronized 확인
        TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
        session.sendMessage(message);
    }

    public SocketRequest getSocketRequest(TextMessage message) throws IOException {
        return objectMapper.readValue(message.getPayload(), SocketRequest.class);
    }

}
