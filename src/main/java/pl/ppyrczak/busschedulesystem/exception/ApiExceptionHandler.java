package pl.ppyrczak.busschedulesystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                notFound,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, notFound);
    }

    @ExceptionHandler(value = {IllegalDateException.class})
    public ResponseEntity<Object> handleIllegalDateException(IllegalDateException e) {
        HttpStatus badRequest = BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {IllegalPassengerException.class})
    public ResponseEntity<Object> handleIllegalPassengerException(IllegalPassengerException e) {
        HttpStatus badRequest = BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
// todo polaczyc wszystko w 1 metode
    @ExceptionHandler(value = {EmailTakenException.class})
    public ResponseEntity<Object> handleEmailTakenException(EmailTakenException e) {
        HttpStatus badRequest = BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {UserNotAuthorizedException.class})
    public ResponseEntity<Object> handleUserNotAuthorizedException(UserNotAuthorizedException e) {
        HttpStatus badRequest = BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiException, badRequest);
    }
}
