package pl.ppyrczak.busschedulesystem.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @NotNull(message = "schedule id is mandatory")
    private Long scheduleId;
    @Range(min = 1)
    private Integer numberOfSeats;
    //TODO NAPRAWIC RELACJE MIEDZY PASAZEREM A OPINIA (NIE MA RELACJI)

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", updatable = false, insertable = false)
    private Review review;
}
