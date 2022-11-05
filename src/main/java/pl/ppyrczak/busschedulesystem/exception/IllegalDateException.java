package pl.ppyrczak.busschedulesystem.exception;

import java.time.LocalDateTime;

public class IllegalDateException extends RuntimeException{
    public IllegalDateException(LocalDateTime arrival) {
        super("You can not add a review before an arrival: " + arrival);
    }
}
