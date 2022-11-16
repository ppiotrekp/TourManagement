package pl.ppyrczak.busschedulesystem.exception.runtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class AllSeatsTakenException extends RuntimeException {
    public AllSeatsTakenException() {
        super("No sufficient amount of available seats");
    }
}
