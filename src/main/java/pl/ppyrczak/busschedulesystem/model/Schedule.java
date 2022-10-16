package pl.ppyrczak.busschedulesystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
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
    private String departureTo;
    @NotNull(message = "departure is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departure;
    @NotNull(message = "arrival is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrival;
    @NotBlank(message = "price is mandatory")
    private String ticketPrice;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "scheduleId", updatable = false, insertable = false)
    private List<Passenger> passengers;

}
