package pl.ppyrczak.busschedulesystem.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private int passengersLimit;
    private String equipment;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "busId", updatable = false, insertable = false)
    private List<Schedule> scheduleList;
}