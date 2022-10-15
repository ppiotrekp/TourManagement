package pl.ppyrczak.busschedulesystem.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.controller.dto.BusDto;
import pl.ppyrczak.busschedulesystem.controller.dto.BusDtoMapper;
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
    public BusDto getBus(@PathVariable Long id) {
        return BusDtoMapper.mapToBusDto(busService.getBus(id));
    }

    @GetMapping("/buses")
    public List<BusDto> getBuses(@RequestParam(required = false) int page, Sort.Direction sort) {
        int pageNumber = page >= 0 ? page : 0;
        return BusDtoMapper.mapToBusDtos(busService.getBuses(pageNumber, sort)) ;
    }

    @PostMapping("/bus")
    public Bus addBus(@Valid @RequestBody Bus bus) {
        return busService.addBus(bus);
    }

    @DeleteMapping("/bus/{id}") // TODO DEZAKTYWACJA
    public ResponseEntity<?> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }
}
