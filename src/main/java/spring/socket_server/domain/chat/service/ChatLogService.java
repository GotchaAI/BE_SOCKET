package spring.socket_server.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatLogService  {
    private final RedisTemplate<String, String> redisTemplate;

    private String chatKey(String roomId) {
        return "room:" + roomId + ":chatlog";
    }

    public void saveChatMessage(String roomId, String message) {
        redisTemplate.opsForList().rightPush(chatKey(roomId), message);
        redisTemplate.opsForList().trim(chatKey(roomId), -10, -1); // 최신 10개만 유지
    }

    public List<String> getLast10Messages(String roomId) {
        return redisTemplate.opsForList().range(chatKey(roomId), 0, -1);
    }
}
