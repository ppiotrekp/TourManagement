package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.service.BusService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;
    @GetMapping("/bus/{id}")
    public Bus getBus(@PathVariable Long id) {
        return busService.getBus(id);
    }


    @GetMapping("/buses")
    public List<Bus> getBuses() {
        return busService.getBuses();
    }

    @PostMapping("/add")
    public Bus addBus(@RequestBody Bus bus) {
        return busService.addBus(bus);
    }

    @PutMapping("/edit/{id}")
    public Bus editBus(@RequestBody Bus bus, Long id) {

    }

}
