package pl.ppyrczak.busschedulesystem.exception.runtime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ArrivalBeforeDepartureException extends RuntimeException {
    public ArrivalBeforeDepartureException() {
        super("Arrival can not be before departure");
    }
}
