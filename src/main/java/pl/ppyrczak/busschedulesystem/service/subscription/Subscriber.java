package pl.ppyrczak.busschedulesystem.service.subscription;

import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.util.List;

public interface Subscriber {
    void sendInfoAboutNewTrip(Schedule schedule, List<ApplicationUser> users);
}
