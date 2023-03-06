package pl.ppyrczak.busschedulesystem.exception.runtime.model;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("User with username " + username + " does not exist");
    }
}
