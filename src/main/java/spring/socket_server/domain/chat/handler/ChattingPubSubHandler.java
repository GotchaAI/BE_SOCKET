package spring.socket_server.domain.chat.handler;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.socket_server.common.listener.PubSubHandler;
import spring.socket_server.domain.chat.dto.ChatMessageReq;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;
import static spring.socket_server.common.constants.WebSocketConstants.*;

@Service
@Qualifier("chattingPubSubHandler")
public class ChattingPubSubHandler extends PubSubHandler {

    public ChattingPubSubHandler(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    @Override
    protected void initHandlers() {
        handlers.put(CHAT_ALL_CHANNEL, (channel, message) -> handleAllChat(message));
        handlers.put(CHAT_PRIVATE_CHANNEL, this:: handlePrivateChat);
        handlers.put(CHAT_ROOM_CHANNEL, this:: handleRoomChat);
    }

    // 채팅 처리 메소드
    private void handleAllChat( Object object) {

    }

    private void handlePrivateChat(String channel,  Object object) {
    }

    private void handleRoomChat(String channel,  Object object) {
    }
}
