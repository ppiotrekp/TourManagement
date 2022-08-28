package pl.ppyrczak.busschedulesystem.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

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
    private LocalDate departure;
    private LocalDate arrival;
    private String ticketPrice;
}
