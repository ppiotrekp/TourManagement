package pl.ppyrczak.busschedulesystem.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException() {
        super("Token expired");
    }
}
