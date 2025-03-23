package spring.socket_server.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import spring.socket_server.Manager.ChannelManager;
import spring.socket_server.dto.ChatMessageReq;
import spring.socket_server.dto.GameEventReq;
import spring.socket_server.dto.GameReadyStatus;

import static spring.socket_server.config.WebSocketConstants.*;

//WebSocket으로 들어온 메시지를 Redis에 발행
@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final RedisTemplate<String, Object> pubSubHandler;
    private final ChannelManager channelManager;

    // WebSocket 첫 연결 시 초기 채널 관리
    @MessageMapping("/connect")
    public void onConnect(@Payload String nickName, @Header("simpSessionId") String sessionId) {
        channelManager.subscribeToInitialChannels(nickName, sessionId);
    }

    // 1. 전체 채팅방 메시지 전송
    @MessageMapping("/chat/all")
    public void sendMessageToAll(@Payload ChatMessageReq message, @Header("simpSessionId") String sessionId) {
        // 현재 세션이 구독한 채널 조회
        pubSubHandler.convertAndSend(CHAT_ALL_CHANNEL, message);
    }

    // 2. 귓속말 전송
    @MessageMapping("/chat/private")
    public void sendPrivateMessage(@Payload ChatMessageReq message) {
        pubSubHandler.convertAndSend(CHAT_PRIVATE_CHANNEL + message.nickName(), message);
    }

    // 3. 대기방 입장시 채널 관리
    @MessageMapping("/game/room/{roomId}")
    public void enterGameRoom(@DestinationVariable String roomId, @Header("simpSessionId") String sessionId) {
        channelManager.subscribeToWaitingRoom(roomId, sessionId);

        // 현재 세션이 구독한 채널 조회
        channelManager.getSubscribedChannels(sessionId);
    }

    // 4. 대기방 내 채팅
    @MessageMapping("/chat/room/{roomId}")
    public void sendRoomMessage(@DestinationVariable String roomId, @Payload ChatMessageReq message) {
        pubSubHandler.convertAndSend(CHAT_ROOM_CHANNEL + roomId, message);

    }

    // 4. 대기방 레디 상태 업데이트
    @MessageMapping("/game/ready/{roomId}")
    public void sendReadyStatus(@DestinationVariable String roomId, @Payload GameReadyStatus readyStatus) {
        pubSubHandler.convertAndSend(GAME_READY_CHANNEL   + roomId, readyStatus);
    }

    //todo : 5. 게임 시작 전 게임 정보(제시어) 전달
    @MessageMapping("/game/info/{roomId}")
    public void sendGameInfoBeforeStart(@DestinationVariable String roomId, String roundCount) { //라운드 수 같이 줘야 함.
    }

    // todo : 6. 게임 시작 ( 레디 상태가 최소 인원 넘는지 확인,
    @MessageMapping("/game/start/{roomId}")
    public void startGame(@DestinationVariable String roomId, @Payload String nickName, @Header("simpSessionId") String sessionId) {

    }

    // todo : 7. 게임 종료
    @MessageMapping("/game/end/{roomId}")
    public void endGame(@DestinationVariable String roomId, @Payload String nickName,  @Header("simpSessionId") String sessionId) {
    }

    //todo : 8. 대기방 퇴장
    @MessageMapping("/game/exit/{roomId}")
    public void exitGameRoom(@DestinationVariable String roomId, @Header("simpSessionId") String sessionId) {
    }

}
