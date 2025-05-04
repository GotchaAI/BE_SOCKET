package spring.socket_server.domain.room.handler;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import spring.socket_server.common.listener.PubSubHandler;
import spring.socket_server.domain.room.dto.RoomJoinedEvent;
import spring.socket_server.domain.room.model.RoomMetadata;

import static spring.socket_server.common.constants.WebSocketConstants.*;

@Service
public class RoomHandler extends PubSubHandler {

    public RoomHandler(SimpMessagingTemplate messagingTemplate) {
        super(messagingTemplate);
    }

    @Override
    protected void initHandlers() {
        handlers.put(ROOM_INIT_INFO, this::roomInitInfo);
        handlers.put(ROOM_LEAVE, this::roomLeave);
        handlers.put(ROOM_CREATE, this::roomCreate);
        handlers.put(ROOM_UPDATE, this::roomUpdate);
    }

    private void roomInitInfo(String channel, String message) {
        RoomJoinedEvent event = convertMessageToDto(message, RoomJoinedEvent.class);
        messagingTemplate.convertAndSend(channel, event);
    }

    private void roomLeave(String channel, String message) {
        messagingTemplate.convertAndSend(channel, message);
    }

    private void roomCreate(String channel, String message) {
        RoomMetadata roomMetadata = convertMessageToDto(message, RoomMetadata.class);
        messagingTemplate.convertAndSend(channel, roomMetadata);
    }

    private void roomUpdate(String channel, String message) {
        RoomMetadata updatedRoomMetadata = convertMessageToDto(message, RoomMetadata.class);
        messagingTemplate.convertAndSend(channel, updatedRoomMetadata);
    }

}
