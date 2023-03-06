package pl.ppyrczak.busschedulesystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "user id is mandatory")
    private Long userId;
    @NotNull(message = "schedule id is mandatory")
    private Long scheduleId;
    @Range(min = 1)
    private Integer numberOfSeats;

    public Passenger(Long userId, Long scheduleId, Integer numberOfSeats) {
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.numberOfSeats = numberOfSeats;
    }
}
