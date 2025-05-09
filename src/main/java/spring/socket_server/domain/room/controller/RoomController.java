package spring.socket_server.domain.room.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import spring.socket_server.common.config.RedisMessage;
import spring.socket_server.common.exception.global.CustomException;
import spring.socket_server.common.exception.global.FieldValidationException;
import spring.socket_server.common.exception.global.GlobalExceptionCode;
import spring.socket_server.common.exception.room.RoomExceptionCode;
import spring.socket_server.domain.room.RoomField.RoomField;
import spring.socket_server.domain.room.dto.CreateRoomRequest;
import spring.socket_server.domain.room.dto.RoomJoinedEvent;
import spring.socket_server.domain.room.dto.UpdateRoomFieldRequest;
import spring.socket_server.domain.room.model.RoomMetadata;
import spring.socket_server.domain.room.service.RoomService;
import spring.socket_server.domain.room.service.RoomUserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spring.socket_server.common.constants.WebSocketConstants.*;

@Slf4j
@Controller
@MessageMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final RoomUserService roomUserService;
    private final RedisTemplate<String, String> stringRedisTemplate;
    @Qualifier("jsonRedisTemplate")
    private final RedisTemplate<String, Object> objectRedisTemplate;

//    @MessageMapping("/enter/{roomId}")
//    public void enterRoom(@DestinationVariable String roomId, Principal principal) {
//        String userId = principal.getName();
//
//        if (roomUserService.getCurrentRoomOfUser(userId).isPresent()) {
//            throw new CustomException(RoomExceptionCode.USER_ALREADY_IN_ANOTHER_ROOM);
//        }
//        if (roomUserService.isUserInRoom(userId, roomId)) {
//            throw new CustomException(RoomExceptionCode.USER_ALREADY_IN_ROOM);
//        }
//
//        roomUserService.joinRoom(userId, roomId);
//
//        RoomMetadata metadata = roomService.getRoomInfo(roomId);
//        List<String> users = roomUserService.getUsersInRoom(roomId);
//
//        // 현재 방 정보 + 유저 리스트 전송
//        objectRedisTemplate.convertAndSend(ROOM_LIST_INFO, new RoomJoinedEvent(metadata, users, userId));
//    }
//
//    @MessageMapping("/leave/{roomId}")
//    public void leaveRoom(@DestinationVariable String roomId, Principal principal) {
//        String userId = principal.getName();
//        roomUserService.leaveRoom(userId, roomId);
//
//        List<String> remainingUsers = roomUserService.getUsersInRoom(roomId);
//        if (remainingUsers.isEmpty()) {
//            roomService.deleteRoom(roomId);
//        }
//
//        stringRedisTemplate.convertAndSend(ROOM_LEAVE + roomId, userId);
//    }

    @MessageMapping("/create")
    public void createRoom(@Payload CreateRoomRequest request, Principal principal) {
        String userId = principal.getName();
        //request 수동 유효성 검사해주는 코드 필요함.

        if (roomUserService.getCurrentRoomOfUser(userId).isPresent()) {
            throw new CustomException(RoomExceptionCode.USER_ALREADY_IN_ANOTHER_ROOM);
        }

        String roomId = roomService.createRoom(request, userId);
        RoomMetadata metadata = roomService.getRoomInfo(roomId);

        objectRedisTemplate.convertAndSend(ROOM_CREATE_INFO, new RedisMessage(userId, ROOM_CREATE_INFO, metadata)); //방 목록 생성 브로드 캐스트 용
        objectRedisTemplate.convertAndSend(PERSONAL_ROOM_CREATE_RESPONSE,  new RedisMessage(userId, PERSONAL_ROOM_CREATE_RESPONSE, metadata)); //본인의 대기방 생성 확인 용
    }

//    @MessageMapping("/update/{roomId}")
//    public void updateRoomField(@DestinationVariable String roomId, @Payload UpdateRoomFieldRequest request, Principal principal) {
//        String userId = principal.getName();
//
//        //현재 user가 해당 방의 onwer 가 맞는지 검사
//        RoomMetadata room = roomService.getRoomInfo(roomId);
//        if (!room.getOwner().equals(userId)) {
//            throw new CustomException(RoomExceptionCode.NOT_ROOM_OWNER);
//        }
//
//        //방장 변경 요청일 시, 변경 하려는 상대방의 존재 확인
//        RoomField requestField = RoomField.from(request.field());
//        if (requestField.equals(RoomField.OWNER)
//                && !roomUserService.isUserInRoom(request.value(), roomId)) {
//            throw new CustomException(RoomExceptionCode.USER_NOT_IN_ROOM);
//        }
//
//        // 클라이언트에게 방 정보 최신화 알림
//        RoomMetadata updated = roomService.updateRoomField(roomId, request.field(), request.value());
//        objectRedisTemplate.convertAndSend(ROOM_UPDATE + roomId, updated);
//    }

}
