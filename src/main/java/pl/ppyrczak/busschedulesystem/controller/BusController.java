package pl.ppyrczak.busschedulesystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ppyrczak.busschedulesystem.model.Bus;

@RestController
public class BusController {

    @GetMapping("/bus")
    public Bus getBus() {
        return new Bus(1L, "Mercedes", "Tourismo", 20, "None");
    }
}
