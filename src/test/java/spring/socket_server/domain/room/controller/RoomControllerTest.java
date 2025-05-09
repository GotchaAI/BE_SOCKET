//package spring.socket_server.domain.room.controller;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.data.redis.core.RedisTemplate;
//import spring.socket_server.domain.room.dto.CreateRoomRequest;
//import spring.socket_server.domain.room.dto.RoomJoinedEvent;
//import spring.socket_server.domain.room.dto.UpdateRoomFieldRequest;
//import spring.socket_server.domain.room.model.RoomMetadata;
//import spring.socket_server.domain.room.service.RoomService;
//import spring.socket_server.domain.room.service.RoomUserService;
//import spring.socket_server.domain.game.enumType.AiMode;
//import spring.socket_server.domain.game.enumType.GameMode;
//
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class RoomControllerTest {
//
//    @InjectMocks
//    private RoomController roomController;
//
//    @Mock
//    private RoomService roomService;
//
//    @Mock
//    private RoomUserService roomUserService;
//
//    @Mock
//    private RedisTemplate<String, Object> objectRedisTemplate;
//    @Mock
//    private RedisTemplate<String, String> stringRedisTemplate;
//
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void 대기방_입장_성공() {
//
//        String roomId = "room1";
//        String ownerId = "owner1";
//        RoomMetadata metadata = new RoomMetadata(
//                "Test Room", ownerId, false, "", 4, 2, "NORMAL", "STANDARD"
//        );
//        List<String> usersAfterJoin = List.of(ownerId);
//
//        String userId = "user1";
//
//        // 전제 조건 1: 아직 어떤 방에도 없고, 해당 방에도 미입장 상태
//        when(roomUserService.getCurrentRoomOfUser(userId))
//                .thenReturn(Optional.empty());
//        when(roomUserService.isUserInRoom(userId, roomId))
//                .thenReturn(false);
//
//        // 전제 조건 2: 방 정보 조회 및 입장 후 유저 리스트
//        when(roomService.getRoomInfo(roomId))
//                .thenReturn(metadata);
//        when(roomUserService.getUsersInRoom(roomId))
//                .thenReturn(usersAfterJoin);
//
//        //로직 실행
//        roomController.enterRoom(roomId, userId);
//
//        // 결과 확인
//        // 1) 실제로 joinRoom 호출 됐는지
//        verify(roomUserService).joinRoom(userId, roomId);
//
//        // 2) 정확한 채널로 이벤트 발행됐는지, 페이로드 내부 검증
//        ArgumentCaptor<RoomJoinedEvent> captor =
//                ArgumentCaptor.forClass(RoomJoinedEvent.class);
//        verify(objectRedisTemplate)
//                .convertAndSend(eq(ROOM_INIT_INFO + roomId), captor.capture());
//
//        RoomJoinedEvent event = captor.getValue();
//        // payload 검증
//        assertEquals(metadata, event.metadata(),
//                "RoomMetadata가 그대로 전달되어야 합니다.");
//        assertEquals(usersAfterJoin, event.users(),
//                "입장 후 방에 남은 사용자 리스트가 전달되어야 합니다.");
//        assertEquals(userId, event.userId(),
//                "입장한 사용자 ID가 전달되어야 합니다.");
//    }
//
////    @Test
////    void testCreateRoom_success() {
////        String roomId = "room123";
////        String userId = "userX";
////
////        CreateRoomRequest request = new CreateRoomRequest(
////                roomId, "test title", false, "", AiMode.BASIC, GameMode.GAME_1
////        );
////
////        RoomMetadata metadata = new RoomMetadata("test title", userId, false, "", 4, 2, "NORMAL", "STANDARD");
////
////        when(roomUserService.getCurrentRoomOfUser(userId)).thenReturn(Optional.empty());
////        when(roomService.getRoomInfo(roomId)).thenReturn(metadata);
////
////        roomController.createRoom(request, userId);
////
////        verify(roomService).createRoom(request, userId);
////        verify(redisTemplate).convertAndSend(eq("room:create"), any(RoomMetadata.class));
////    }
////
////    @Test
////    void testLeaveRoom_successAndDeletesIfEmpty() {
////        String roomId = "roomA";
////        String userId = "userA";
////
////        when(roomUserService.getUsersInRoom(roomId)).thenReturn(List.of()); // Empty → 삭제
////
////        roomController.leaveRoom(roomId, userId);
////
////        verify(roomUserService).leaveRoom(userId, roomId);
////        verify(roomService).deleteRoom(roomId);
////        verify(redisTemplate).convertAndSend("room:left:" + roomId, userId);
////    }
////
////    @Test
////    void testUpdateRoomField_ownerTransferSuccess() {
////        String roomId = "roomX";
////        String userId = "admin";
////        String newOwnerId = "newGuy";
////
////        UpdateRoomFieldRequest req = new UpdateRoomFieldRequest(roomId, "owner", newOwnerId);
////        RoomMetadata oldMetadata = new RoomMetadata("title", userId, false, "", 4, 2, "EASY", "STANDARD");
////
////        when(roomService.getRoomInfo(roomId)).thenReturn(oldMetadata);
////        when(roomUserService.isUserInRoom(newOwnerId, roomId)).thenReturn(true);
////
////        roomController.updateRoomField(req, userId);
////
////        verify(roomService).updateRoomField(roomId, "owner", newOwnerId);
////        verify(redisTemplate).convertAndSend(eq("room:update:" + roomId), any(RoomMetadata.class));
////    }
////
////    @Test
////    void testUpdateRoomField_failIfNotOwner() {
////        String roomId = "roomY";
////        String userId = "nonOwner";
////
////        UpdateRoomFieldRequest req = new UpdateRoomFieldRequest(roomId, "owner", "someone");
////        RoomMetadata metadata = new RoomMetadata("test", "actualOwner", false, "", 4, 2, "NORMAL", "STANDARD");
////
////        when(roomService.getRoomInfo(roomId)).thenReturn(metadata);
////
////        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () ->
////                roomController.updateRoomField(req, userId));
////    }
//}
