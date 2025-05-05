package spring.socket_server.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import spring.socket_server.common.exception.global.CustomException;
import spring.socket_server.common.exception.global.FieldValidationException;
import spring.socket_server.common.exception.global.GlobalExceptionCode;

@Slf4j
@ControllerAdvice
public class SocketGlobalExceptionHandler {

    @MessageExceptionHandler(CustomException.class)
    @SendToUser("/queue/errors")
    public ExceptionRes handleCustomException(final CustomException ex) {
        ExceptionCode error = ex.getExceptionCode();
        return ExceptionRes.from(error);
    }

    @MessageExceptionHandler(IllegalArgumentException.class)
    @SendToUser("/queue/errors")
    public ExceptionRes handleInvalidField(final FieldValidationException e) {
        e.getFieldErrors().forEach((field, message) ->
                log.warn("[Field Validation Exception] {}: {}", field, message)
        );
        ExceptionCode error = GlobalExceptionCode.FIELD_VALIDATION_ERROR;
        return ExceptionRes.from(error, e.getFieldErrors());
    }

}