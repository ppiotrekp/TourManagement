package pl.ppyrczak.busschedulesystem.exception.runtime.model;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Long id) {
        super("Resource with id " + id + " not found");
    }
}
