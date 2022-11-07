package pl.ppyrczak.busschedulesystem.exception;

public class EmailConfirmedException extends IllegalStateException{
    public EmailConfirmedException() {
        super("Email already confirmed");
    }
}
