package pl.ppyrczak.busschedulesystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Bus {

    private Long id;
    private String brand;
    private String model;
    private int passengersLimit;
    private String equipment;
}
