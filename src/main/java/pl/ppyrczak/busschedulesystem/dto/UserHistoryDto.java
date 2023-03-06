package pl.ppyrczak.busschedulesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserHistoryDto {
    private String departureFrom;
    private String arrivalTo;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private int numberOfSeats;
    private int rating;
    private String description;
    private LocalDateTime created;
}
