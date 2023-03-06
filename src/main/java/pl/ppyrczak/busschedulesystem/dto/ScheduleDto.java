package pl.ppyrczak.busschedulesystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ScheduleDto {
    private Long id;
    private Long busId;
    private String departureFrom;
    private String arrivalTo;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private int ticketPrice;
}
