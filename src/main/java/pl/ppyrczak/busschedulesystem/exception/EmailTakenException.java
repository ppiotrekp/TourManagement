package pl.ppyrczak.busschedulesystem.exception;

public class EmailTakenException extends RuntimeException{
    public EmailTakenException() {
        super("Email taken");
    }
}
