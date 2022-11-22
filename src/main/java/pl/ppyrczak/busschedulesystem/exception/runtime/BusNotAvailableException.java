package pl.ppyrczak.busschedulesystem.exception.runtime;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)

public class BusNotAvailableException extends RuntimeException{
    public BusNotAvailableException() {
        super("Bus is not available");
    }
}
