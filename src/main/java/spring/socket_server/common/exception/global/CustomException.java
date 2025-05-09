package spring.socket_server.common.exception.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import spring.socket_server.common.exception.ExceptionCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ExceptionCode exceptionCode;

    @Override
    public String getMessage() {
        return exceptionCode.getMessage();
    }
}
