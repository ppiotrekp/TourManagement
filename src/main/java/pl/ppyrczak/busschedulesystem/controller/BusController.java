package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.controller.dto.BusDto;
import pl.ppyrczak.busschedulesystem.controller.dto.BusDtoMapper;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.service.BusService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;
    @GetMapping("/bus/{id}")
    public BusDto getBus(@PathVariable Long id) {
        return BusDtoMapper.mapToBusDto(busService.getBus(id));
    }

    @GetMapping("/buses")
    public List<BusDto> getBuses(@RequestParam(required = false) int page, Sort.Direction sort) {
        int pageNumber = page >= 0 ? page : 0;
        return BusDtoMapper.mapToBusDtos(busService.getBuses(pageNumber, sort));
    }

    @PostMapping("/add")
    public Bus addBus(@RequestBody Bus bus) {
        return busService.addBus(bus);
    }

    @PutMapping("/edit/{id}")
    public Bus editBus(@RequestBody Bus busToUpdate, @PathVariable Long id) {
        return busService.editBus(busToUpdate, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBus( @PathVariable Long id) {
        busService.deleteBus(id);
    }
}
