package pl.ppyrczak.busschedulesystem.exception;

public class UserNotAuthorizedException extends IllegalAccessException{
    public UserNotAuthorizedException() {
        super("Access forbidden");
    }
}
