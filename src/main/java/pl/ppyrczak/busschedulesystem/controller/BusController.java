package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.service.BusService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;
   /* @GetMapping("/bus")
    public Bus getBus() {
        return new Bus(1L, "Mercedes", "Tourismo", 20, "None");
    }*/

    @GetMapping("/buses")
    public List<Bus> getBuses() {
        return busService.getBuses();
    }
}
