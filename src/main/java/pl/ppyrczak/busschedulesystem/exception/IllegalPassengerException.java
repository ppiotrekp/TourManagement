package pl.ppyrczak.busschedulesystem.exception;

public class IllegalPassengerException extends RuntimeException{
    public IllegalPassengerException() {
        super("You have not been a participant of that trip");
    }
}
