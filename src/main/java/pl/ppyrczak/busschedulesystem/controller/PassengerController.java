package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.controller.util.UserPermission;
import pl.ppyrczak.busschedulesystem.exception.illegalaccess.UserNotAuthorizedException;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.service.PassengerService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;
    private final UserPermission userPermission;

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

    @ResponseStatus(CREATED)
    @PostMapping("/passengers")
    public Passenger addPassenger(@Valid @RequestBody Passenger passenger,
                                  Authentication authentication) throws UserNotAuthorizedException {

        if (!userPermission.hasPermissionToAddPassenger(passenger, authentication)) {
            throw new UserNotAuthorizedException();
        }
        return passengerService.addPassenger(passenger);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/schedules/{id}/passengers")
    public List<Passenger> getSchedulesWithPassengers(@PathVariable Long id) {
        return passengerService.getPassengersForSpecificSchedule(id);
    }
}
