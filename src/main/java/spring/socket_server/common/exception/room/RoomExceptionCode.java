package spring.socket_server.common.exception.room;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.socket_server.common.exception.ExceptionCode;

@AllArgsConstructor
public enum RoomExceptionCode implements ExceptionCode {
    USER_ALREADY_IN_ANOTHER_ROOM(HttpStatus.BAD_REQUEST, "ROOM_400_001", "이미 다른 방에 입장 중 입니다."),
    USER_ALREADY_IN_ROOM(HttpStatus.BAD_REQUEST, "ROOM_400_002", "이미 이 방에 존재 합니다."),
    USER_NOT_IN_ROOM(HttpStatus.BAD_REQUEST, "ROOM_400_003", "방에 존재하지 않는 유저입니다."),
    NOT_ROOM_OWNER(HttpStatus.FORBIDDEN, "ROOM_403_001", "방장 권한이 없습니다."),
    INVALID_ROOM_ID(HttpStatus.BAD_REQUEST, "ROOM_400_004", "유효하지 않은 고유 방 코드 입니다."),
    ROOM_ID_EXHAUSTED(HttpStatus.SERVICE_UNAVAILABLE, "GLOBAL-503-001", "사용 가능한 방 코드가 모두 소진되었습니다. 서버 팀에 문의해주세요.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
