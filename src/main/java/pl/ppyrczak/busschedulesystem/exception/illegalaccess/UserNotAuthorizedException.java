package pl.ppyrczak.busschedulesystem.exception.illegalaccess;

public class UserNotAuthorizedException extends IllegalAccessException{
    public UserNotAuthorizedException() {
        super("Access forbidden");
    }
}
