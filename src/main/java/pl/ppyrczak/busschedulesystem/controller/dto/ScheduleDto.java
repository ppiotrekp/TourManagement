package pl.ppyrczak.busschedulesystem.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
