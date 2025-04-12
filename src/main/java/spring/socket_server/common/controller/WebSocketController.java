package spring.socket_server.common.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import spring.socket_server.common.Manager.ChannelManager;

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

}
