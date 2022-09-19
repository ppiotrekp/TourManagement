package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.service.BusService;
import pl.ppyrczak.busschedulesystem.service.PassengerService;
import pl.ppyrczak.busschedulesystem.service.ScheduleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @GetMapping("/passengers")
    public List<Passenger> getPassengers() {
        return passengerService.getPassengers();
    }

    @GetMapping("/passenger/{id}")
    public Passenger getPassenger(@PathVariable Long id) {
        return passengerService.getPassenger(id);
    }

    @PostMapping("/addpassenger")
    public Passenger addPassenger(@Valid @RequestBody Passenger passenger) {
        return passengerService.addPassenger(passenger);
    }
}
