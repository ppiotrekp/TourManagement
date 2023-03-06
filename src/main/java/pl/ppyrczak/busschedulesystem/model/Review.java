package pl.ppyrczak.busschedulesystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

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
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "passenger id is mandatory")
    private Long passengerId;
    @NotNull(message = "schedule id is mandatory")
    private Long scheduleId;
    @NotNull
    @Range(max = 5, min = 1)
    private int rating;
    @NotBlank(message = "review description must not be null")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;

    public void setCreated() {
        this.created = LocalDateTime.now();
    }

    public Review(Long passengerId,
                  Long scheduleId,
                  int rating,
                  String description,
                  LocalDateTime created) {
        this.passengerId = passengerId;
        this.scheduleId = scheduleId;
        this.rating = rating;
        this.description = description;
        this.created = created;
    }
}
