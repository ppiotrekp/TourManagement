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
    @NotNull(message = "schedule id is mandatory")
    private Long scheduleId;
    @NotBlank(message = "firstname is mandatory")
    private String firstName;
    @NotBlank(message = "lastname is mandatory")
    private String lastName;
    @Range(min = 1)
    private int numberOfSeats;
    @NotBlank(message = "email is mandatory")
    @Email(message = "email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\" +
            ".[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\" +
            "x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\" +
            ".)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\" +
            ".){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f" +
            "\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;
    @NotBlank(message = "phone number is mandatory")
    @Pattern(message = "Invalid phone number", regexp = "^([1-9][0-9]{8})$")
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", updatable = false, insertable = false)
    private Review review;
}
