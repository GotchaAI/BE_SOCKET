package spring.socket_server.common.exception.global;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import spring.socket_server.common.exception.ExceptionCode;

@AllArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode {
INVALID_FIELD(HttpStatus.BAD_REQUEST, "GLOBAL_001", "지원하지 않는 필드입니다.");

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
