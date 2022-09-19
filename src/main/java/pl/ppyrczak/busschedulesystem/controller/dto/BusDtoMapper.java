package pl.ppyrczak.busschedulesystem.controller.dto;

import pl.ppyrczak.busschedulesystem.model.Bus;

import java.util.List;
import java.util.stream.Collectors;

public class BusDtoMapper {

    private BusDtoMapper() {
    }

    public static List<BusDto> mapToBusDtos(List<Bus> buses) {
        return buses.stream()
                .map(bus -> mapToBusDto(bus))
                .collect(Collectors.toList());
    }

    public static BusDto mapToBusDto(Bus bus) {
        return BusDto.builder()
                .id(bus.getId())
                .brand(bus.getBrand())
                .model(bus.getModel())
                .equipment(bus.getEquipment())
                .passengersLimit(bus.getPassengersLimit())
                .build();
    }
}
