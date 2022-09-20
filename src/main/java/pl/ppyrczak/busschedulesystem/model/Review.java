package pl.ppyrczak.busschedulesystem.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "schedule id is mandatory")
    private Long scheduleId;
    @NotNull(message = "passenger id is mandatory")
    private Long passengerId;
    @NotNull
    @Range(max = 5, min = 1)
    private int rating;
    @NotBlank(message = "review description must not be null")
    private String description;

}
