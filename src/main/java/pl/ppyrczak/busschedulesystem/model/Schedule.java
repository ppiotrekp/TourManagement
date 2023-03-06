package pl.ppyrczak.busschedulesystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "bus id is mandatory")
    private Long busId;
    @NotBlank(message = "set off is mandatory")
    private String departureFrom;
    @NotBlank(message = "destination is mandatory")
    private String arrivalTo;
    @NotNull(message = "departure is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departure;
    @NotNull(message = "arrival is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrival;
    @NotNull(message = "price is mandatory")
    private int ticketPrice;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "scheduleId", updatable = false, insertable = false)
    private List<Passenger> passengers;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "scheduleId", updatable = false, insertable = false)
    private List<Review> reviews;

    public Schedule(Long busId,
                    String departureFrom,
                    String departureTo,
                    LocalDateTime departure,
                    LocalDateTime arrival,
                    int ticketPrice,
                    List<Passenger> passengers,
                    List<Review> reviews) {
        this.busId = busId;
        this.departureFrom = departureFrom;
        this.arrivalTo = departureTo;
        this.departure = departure;
        this.arrival = arrival;
        this.ticketPrice = ticketPrice;
        this.passengers = passengers;
        this.reviews = reviews;
    }
}
