package pl.ppyrczak.busschedulesystem.exception.illegalstate;

public class EmailConfirmedException extends IllegalStateException{
    public EmailConfirmedException() {
        super("Email already confirmed");
    }
}
