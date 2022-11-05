package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.auth.UserService;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.service.BusService;
import pl.ppyrczak.busschedulesystem.service.PassengerService;
import pl.ppyrczak.busschedulesystem.service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/passengers")
    public List<Passenger> getPassengers() {
        return passengerService.getPassengers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/passengers/{id}")
    public Passenger getPassenger(@PathVariable Long id) {
        return passengerService.getPassenger(id);
    }

    @PostMapping("/passenger")
    public Passenger addPassenger(@Valid @RequestBody Passenger passenger,
                                  Authentication authentication) throws IllegalAccessException {
        List<ApplicationUser> users = userService.getAllUsersInfo();
        Long currentId = 0L;

        for (ApplicationUser user : users) {
            if (user.getUsername().equals(authentication.getName())) {
                currentId = user.getId();
            }
        }

        if (currentId == passenger.getUserId()) {
            return passengerService.addPassenger(passenger);
        } else {
            throw new IllegalAccessException("FORBIDDEN");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/schedules/{id}/passengers")
    public List<Passenger> getSchedulesWithPassengers(@PathVariable Long id) {
        return passengerService.getPassengersForSpecificSchedule(id);
    }

}
