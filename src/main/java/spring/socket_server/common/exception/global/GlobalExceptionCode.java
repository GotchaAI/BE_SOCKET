package spring.socket_server.common.exception.global;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.socket_server.common.exception.ExceptionCode;

@AllArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL-500-001", "서버 에러입니다. 서버 팀에 문의해주세요."),
    FIELD_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "GLOBAL-400-001", "요청한 필드 값이 유효하지 않습니다."),
    CSRF_INVALID(HttpStatus.FORBIDDEN, "GLOBAL-403-001", "CSRF 토큰이 유효하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "AUTH-401-001", "인증된 사용자를 찾을 수 없습니다."),
    INVALID_MESSAGE_FORMAT(HttpStatus.BAD_REQUEST, "GLOBAL_400_002", "유효하지 않은 메시지 포맷 입니다."),
    INVALID_CHANNEL(HttpStatus.BAD_REQUEST, "GLOBAL_400_003", "유효하지 않은 채널입니다.");

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
