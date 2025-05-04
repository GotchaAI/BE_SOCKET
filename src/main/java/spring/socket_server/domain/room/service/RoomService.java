package spring.socket_server.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import spring.socket_server.domain.room.RoomField.RoomField;
import spring.socket_server.domain.room.dto.CreateRoomRequest;
import spring.socket_server.domain.room.model.RoomMetadata;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RedisTemplate<String, String> redisTemplate;

    public void createRoom(CreateRoomRequest request, String ownerId) {
        String key = getRoomKey(request.roomId());

        Map<String, String> roomData = Map.of(
                RoomField.TITLE.getRedisField(), request.title(),
                RoomField.OWNER.getRedisField(), ownerId,
                RoomField.HAS_PASSWORD.getRedisField(), String.valueOf(request.hasPassword()),
                RoomField.PASSWORD.getRedisField(), request.password(),
                RoomField.MAX.getRedisField(), String.valueOf(request.gameMode().getMaxPlayers()),
                RoomField.MIN.getRedisField(), String.valueOf(request.gameMode().getMinPlayers()),
                RoomField.AI_LEVEL.getRedisField(), request.Aimode().name(),
                RoomField.GAME_MODE.getRedisField(), request.gameMode().name()
        );

        redisTemplate.opsForHash().putAll(key, roomData);
    }

    public void deleteRoom (String roomId) {
        redisTemplate.delete(getRoomKey(roomId));
        redisTemplate.delete(getRoomKey(roomId) + ":users");
        redisTemplate.delete(getRoomKey(roomId) + ":chatlog");
    }

    public RoomMetadata updateRoomField(String roomId, String fieldName, String value) {
        RoomField field = RoomField.from(fieldName);
        redisTemplate.opsForHash().put(getRoomKey(roomId), field.getRedisField(), value);
        return getRoomInfo(roomId);
    }

    public RoomMetadata getRoomInfo(String roomId) {
        Map<Object, Object> fields = redisTemplate.opsForHash().entries(getRoomKey(roomId));
        return RoomMetadata.fromRedisMap(fields);
    }

    private String getRoomKey(String roomId) {
        return "room:"  + roomId;
    }
}
