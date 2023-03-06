package pl.ppyrczak.busschedulesystem.exception.runtime.model;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class BusNotFoundException extends RuntimeException {

    public BusNotFoundException(Long id) {
        super("Bus with id " + id + " does not exist");
    }
}
