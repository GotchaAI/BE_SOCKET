package spring.socket_server.domain.room.dto;

import spring.socket_server.domain.room.model.RoomMetadata;

import java.util.List;

public record RoomJoinedEvent (
    RoomMetadata metadata,
    List<String> users,
    String userId
){}
