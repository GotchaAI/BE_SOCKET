package spring.socket_server.domain.room.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.socket_server.common.config.RedisMessage;
import spring.socket_server.common.exception.global.CustomException;
import spring.socket_server.common.exception.global.GlobalExceptionCode;
import spring.socket_server.common.listener.PubSubHandler;
import spring.socket_server.domain.room.dto.RoomJoinedEvent;
import spring.socket_server.domain.room.model.RoomMetadata;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;
import static spring.socket_server.common.constants.WebSocketConstants.*;

@Slf4j
@Service
@Qualifier("roomPubSubHandler")
public class RoomPubSubHandler extends PubSubHandler {

    private final RoomMetadata roomMetadata;

    public RoomPubSubHandler(SimpMessagingTemplate messagingTemplate, RoomMetadata roomMetadata) {
        super(messagingTemplate);
        this.roomMetadata = roomMetadata;
    }

    @Override
    protected void initHandlers() {
//        handlers.put(ROOM_LIST_INFO, this::roomListInfo);
//        handlers.put(ROOM_LEAVE, this::roomLeave);
        handlers.put(ROOM_CREATE_INFO, this::roomCreateInfo);
//        handlers.put(ROOM_UPDATE, this::roomUpdate);
    }

//    private void roomListInfo(String channel, Object object) {
//        RoomJoinedEvent event = convertMessageToDto(message, RoomJoinedEvent.class);
//        messagingTemplate.convertAndSend(channel, event);
//    }
//
//    private void roomLeave(String channel,  Object object) {
//        String roomId = extractParam(channel, ROOM_LEAVE);
//        messagingTemplate.convertAndSend(channel+roomId, message);
//    }

    private void roomCreateInfo(String channel, Object object) {
        RedisMessage redisMessage = (RedisMessage) object;
        RoomMetadata roomMetadata = convertMessageToDto(redisMessage.payload(), RoomMetadata.class);
        messagingTemplate.convertAndSend(channel, roomMetadata);
    }

//    private void roomUpdate(String channel,  Object object) {
//        RoomMetadata updatedRoomMetadata = convertMessageToDto(message, RoomMetadata.class);
//        String roomId = extractParam(channel, ROOM_UPDATE);
//        messagingTemplate.convertAndSend(channel+roomId, updatedRoomMetadata);
//    }

}
