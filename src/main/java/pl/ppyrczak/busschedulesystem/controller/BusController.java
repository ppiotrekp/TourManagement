package pl.ppyrczak.busschedulesystem.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.service.BusService;

import javax.validation.Valid;
import java.util.List;

@RestController

public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/buses/{id}")
    public Bus getBus(@PathVariable Long id) {
        return busService.getBus(id);
    }

    @GetMapping("/buses")
    public List<Bus> getBuses(@RequestParam(required = false) int page, Sort.Direction sort) {
        int pageNumber = page >= 0 ? page : 0;
        return busService.getBuses(pageNumber, sort);
    }

    @PostMapping("/bus")
    public Bus addBus(@Valid @RequestBody Bus bus) {
        return busService.addBus(bus);
    }

    @PutMapping("/bus/{id}")
    public Bus editBus(@RequestBody Bus busToUpdate, @PathVariable Long id) {
        return busService.editBus(busToUpdate, id);
    }


    @DeleteMapping("/bus/{id}")
    public ResponseEntity<?> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }
}
