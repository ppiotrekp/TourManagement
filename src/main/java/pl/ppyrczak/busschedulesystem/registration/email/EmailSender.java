package pl.ppyrczak.busschedulesystem.registration.email;

public interface EmailSender {
    void send(String to, String email);
}
