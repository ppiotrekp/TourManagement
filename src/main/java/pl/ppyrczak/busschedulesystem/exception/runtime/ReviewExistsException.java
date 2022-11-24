package pl.ppyrczak.busschedulesystem.exception.runtime;

public class ReviewExistsException extends RuntimeException {
    public ReviewExistsException(Long id) {
        super("Passenger with id: " + id + " has already had review");
    }
}
