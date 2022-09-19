package pl.ppyrczak.busschedulesystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "brand is mandatory")
    private String brand;
    @NotBlank(message = "model is mandatory")
    private String model;
    @NotNull(message = "passengers limit is mandatory")
    private int passengersLimit;
    @NotBlank(message = "equipment is mandatory")
    private String equipment;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "busId", updatable = false, insertable = false)
    private List<Schedule> scheduleList;
}