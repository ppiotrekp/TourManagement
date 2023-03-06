package pl.ppyrczak.busschedulesystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BusDto {
    private Long id;
    private String brand;
    private String model;
    private Integer passengersLimit;
    private String equipment;
}
