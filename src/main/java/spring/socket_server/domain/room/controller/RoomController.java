package spring.socket_server.domain.room.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import spring.socket_server.common.exception.global.CustomException;
import spring.socket_server.common.exception.room.RoomExceptionCode;
import spring.socket_server.domain.room.RoomField.RoomField;
import spring.socket_server.domain.room.dto.CreateRoomRequest;
import spring.socket_server.domain.room.dto.RoomJoinedEvent;
import spring.socket_server.domain.room.dto.UpdateRoomFieldRequest;
import spring.socket_server.domain.room.model.RoomMetadata;
import spring.socket_server.domain.room.service.RoomService;
import spring.socket_server.domain.room.service.RoomUserService;

import java.util.List;

import static spring.socket_server.common.constants.WebSocketConstants.*;


@Controller
@MessageMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final RoomUserService roomUserService;
    private final RedisTemplate redisTemplate;

    @MessageMapping("/enter/{roomId}")
    public void enterRoom(@DestinationVariable String roomId, @Header("user-id") String userId) {

        if (roomUserService.getCurrentRoomOfUser(userId).isPresent()) {
            throw new CustomException(RoomExceptionCode.USER_ALREADY_IN_ANOTHER_ROOM);
        }
        if (roomUserService.isUserInRoom(userId, roomId)) {
            throw new CustomException(RoomExceptionCode.USER_ALREADY_IN_ROOM);
        }

        roomUserService.joinRoom(userId, roomId);

        RoomMetadata metadata = roomService.getRoomInfo(roomId);
        List<String> users = roomUserService.getUsersInRoom(roomId);

        // 현재 방 정보 + 유저 리스트 전송
        redisTemplate.convertAndSend(ROOM_INIT_INFO  + roomId,
                new RoomJoinedEvent(metadata, users, userId));
    }

    @MessageMapping("/leave/{roomId}")
    public void leaveRoom(@DestinationVariable String roomId, @Header("user-id") String userId) {
        roomUserService.leaveRoom(userId, roomId);

        List<String> remainingUsers = roomUserService.getUsersInRoom(roomId);
        if (remainingUsers.isEmpty()) {
            roomService.deleteRoom(roomId);
        }

        redisTemplate.convertAndSend(ROOM_LEAVE + roomId, userId);
    }

    @MessageMapping("/create")
    public void createRoom(@Payload CreateRoomRequest request, @Header("user-id") String userId) {
        if (roomUserService.getCurrentRoomOfUser(userId).isPresent()) {
            throw new CustomException(RoomExceptionCode.USER_ALREADY_IN_ANOTHER_ROOM);
        }

        roomService.createRoom(request, userId);

        RoomMetadata metadata = roomService.getRoomInfo(request.roomId());
        redisTemplate.convertAndSend(ROOM_CREATE, metadata);
    }

    @MessageMapping("/update")
    public void updateRoomField(@Payload UpdateRoomFieldRequest request,
                                @Header("user-id") String userId) {

        //현재 user가 해당 방의 onwer 가 맞는지 검사
        RoomMetadata room = roomService.getRoomInfo(request.roomId());
        if (!room.getOwner().equals(userId)) {
            throw new CustomException(RoomExceptionCode.NOT_ROOM_OWNER);
        }

        //방장 변경 요청일 시, 변경하려는 상대방의 존재 확인
        RoomField requestField = RoomField.from(request.field());
        if (requestField.equals(RoomField.OWNER)
                && !roomUserService.isUserInRoom(request.value(), request.roomId())) {
            throw new CustomException(RoomExceptionCode.USER_NOT_IN_ROOM);
        }

        // 클라이언트에게 방 정보 최신화 알림
        RoomMetadata updated = roomService.updateRoomField(request.roomId(), request.field(), request.value());
        redisTemplate.convertAndSend(ROOM_UPDATE + request.roomId(), updated);
    }

}
