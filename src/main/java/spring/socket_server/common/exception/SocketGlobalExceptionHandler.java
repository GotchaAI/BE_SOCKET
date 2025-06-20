package spring.socket_server.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import spring.socket_server.common.exception.global.CustomException;
import spring.socket_server.common.exception.global.FieldValidationException;
import spring.socket_server.common.exception.global.GlobalExceptionCode;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class SocketGlobalExceptionHandler {

    @MessageExceptionHandler(CustomException.class)
    @SendToUser("/queue/errors")
    public ExceptionRes handleCustomException(final CustomException ex) {
        ExceptionCode error = ex.getExceptionCode();
        return ExceptionRes.from(error);
    }

    @MessageExceptionHandler(FieldValidationException.class)
    @SendToUser("/queue/errors")
    public ExceptionRes handleInvalidField(final FieldValidationException e) {
        ExceptionCode error = GlobalExceptionCode.FIELD_VALIDATION_ERROR;
        return ExceptionRes.from(error, e.getFieldErrors());
    }

    @MessageExceptionHandler(MethodArgumentNotValidException.class)
    @SendToUser("/queue/errors")
    public ExceptionRes handleValidationException(final MethodArgumentNotValidException e) {
        Map<String, String> fieldErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap (
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));
        return ExceptionRes.from(GlobalExceptionCode.FIELD_VALIDATION_ERROR, fieldErrors);
    }
}

