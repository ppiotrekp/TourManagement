package pl.ppyrczak.busschedulesystem.exception.runtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ArrivalInPastException extends RuntimeException {
    public ArrivalInPastException() {
        super("You can not set arrival in the past");
    }
}
