package pl.ppyrczak.busschedulesystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "bus id is mandatory")
    private Long busId;
    @NotBlank(message = "set off is mandatory")
    private String departureFrom;
    @NotBlank(message = "destination is mandatory")
    private String departureTo;
    @NotBlank(message = "departure is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departure;
    @NotBlank(message = "arrival is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrival;
    @NotBlank(message = "price is mandatory")
    private String ticketPrice;

}
