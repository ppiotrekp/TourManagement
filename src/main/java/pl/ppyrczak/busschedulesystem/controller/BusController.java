package pl.ppyrczak.busschedulesystem.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.dto.BusDto;
import pl.ppyrczak.busschedulesystem.dto.mapper.BusDtoMapper;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.service.BusService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController

public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/buses/{id}")
    public BusDto getBus(@PathVariable Long id) {
        return BusDtoMapper.mapToBusDto(busService.getBus(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')") //TODO CZEMU NIE DZIALA?
    @GetMapping("/buses")
    public List<BusDto> getBuses(@RequestParam(required = false) int page, Sort.Direction sort) {
        int pageNumber = page >= 0 ? page : 0;
        return BusDtoMapper.mapToBusDtos(busService.getBuses(pageNumber, sort)) ;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/buses")
    public ResponseEntity<Bus> addBus(@Valid @RequestBody Bus bus) {
//        return busService.addBus(bus);
        return new ResponseEntity<>(busService.addBus(bus), CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/buses/{id}") // TODO DEZAKTYWACJA
    public ResponseEntity<?> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }
}
