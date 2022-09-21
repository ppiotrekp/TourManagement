package pl.ppyrczak.busschedulesystem.controller.dto;

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
    private String departureTo;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private String ticketPrice;
}
