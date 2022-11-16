package pl.ppyrczak.busschedulesystem.exception.runtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class IllegalPassengerException extends RuntimeException{
    public IllegalPassengerException() {
        super("You have not been a participant of that trip");
    }
}
