package pl.ppyrczak.busschedulesystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long busId;
    private String departureFrom;
    private String departureTo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departure;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrival;
    private String ticketPrice;

}
