package pl.ppyrczak.busschedulesystem.exception.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.ppyrczak.busschedulesystem.exception.ApiException;
import pl.ppyrczak.busschedulesystem.exception.ApiRequestException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
//
//    @ResponseStatus(BAD_REQUEST)
//    @ExceptionHandler(value = {ConstraintViolationException.class})
//    public Map<String, String> handleConstraintViolation(ConstraintViolationException e) {
//        Map<String, String> errorMap = new HashMap<>();
//        e.getConstraintViolations().forEach(error -> {
//            errorMap.put((String) error.getInvalidValue(), error.getMessage());
//        });
//        return errorMap;
//    } TODO nie dziala
}
