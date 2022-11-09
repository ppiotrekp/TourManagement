package pl.ppyrczak.busschedulesystem.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.ppyrczak.busschedulesystem.exception.illegalaccess.UserNotAuthorizedException;
import pl.ppyrczak.busschedulesystem.exception.illegalstate.EmailConfirmedException;
import pl.ppyrczak.busschedulesystem.exception.model.ApiException;
import pl.ppyrczak.busschedulesystem.exception.runtime.*;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {UserNotAuthorizedException.class})
    public ResponseEntity<Object> handleUserNotAuthorizedException(UserNotAuthorizedException e) {
        HttpStatus forbidden = FORBIDDEN;
        ApiException apiException = new ApiException(
                e.getMessage(),
                forbidden,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, forbidden);
    }

    @ExceptionHandler(value = {EmailConfirmedException.class})
    public ResponseEntity<Object> handleEmailConfirmedException(EmailConfirmedException e) {
        HttpStatus badRequest = BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }


    @ExceptionHandler(value = {ResourceNotFoundException.class,
            TokenExpiredException.class,
            IllegalPassengerException.class,
            IllegalDateException.class,
            EmailTakenException.class})

    public ResponseEntity<Object> handleApiRequestRuntimeException(RuntimeException e) {
        HttpStatus httpStatus;
        if (e instanceof ResourceNotFoundException) {
            httpStatus = NOT_FOUND;
        } else {
            httpStatus = BAD_REQUEST;
        }
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
