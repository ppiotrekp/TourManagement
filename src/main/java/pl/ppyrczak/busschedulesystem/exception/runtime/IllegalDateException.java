package pl.ppyrczak.busschedulesystem.exception.runtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalDateException extends RuntimeException{
    public IllegalDateException(LocalDateTime arrival) {
        super("You can not add a review before an arrival: " + arrival);
    }
}
