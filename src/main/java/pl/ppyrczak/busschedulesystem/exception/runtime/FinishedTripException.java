package pl.ppyrczak.busschedulesystem.exception.runtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FinishedTripException extends RuntimeException {
    public FinishedTripException() {
        super("You can not change finished trip");
    }
}
