package pl.ppyrczak.busschedulesystem.exception.handler.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.ppyrczak.busschedulesystem.exception.model.ApiException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class UserValidationExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleUserValidationException(ConstraintViolationException e) {
        HttpStatus badRequest = BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getLocalizedMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
}
