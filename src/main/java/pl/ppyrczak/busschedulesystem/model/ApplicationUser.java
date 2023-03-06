package pl.ppyrczak.busschedulesystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.ppyrczak.busschedulesystem.dto.UserHistoryDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NamedNativeQuery(name = "ApplicationUser.getUserHistory",
query = """
            select s.departure_from, s.arrival_to, 
            s.departure,  s.arrival, p.number_of_seats, 
            r.rating, r.description, r.created from schedule s
            join passenger p on s.id = p.schedule_id 
            join review r on s.id = r.schedule_id 
            where p.user_id = :id
""", resultSetMapping = "Mapping.UserHistoryDto")

@SqlResultSetMapping(name = "Mapping.UserHistoryDto",
                    classes = @ConstructorResult(targetClass = UserHistoryDto.class,
                    columns = {@ColumnResult(name = "departure_from", type = String.class),
                            @ColumnResult(name = "arrival_to", type = String.class ),
                            @ColumnResult(name = "departure", type = LocalDateTime.class ),
                            @ColumnResult(name = "arrival", type = LocalDateTime.class ),
                            @ColumnResult(name = "number_of_seats", type = Integer.class ),
                            @ColumnResult(name = "rating", type = Integer.class ),
                            @ColumnResult(name = "description", type = String.class ),
                            @ColumnResult(name = "created", type = LocalDateTime.class )}))

@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "You have to pass your first name")
    private String firstName;
    @NotBlank(message = "You have to pass your last name")
    private String lastName;
    @NotBlank(message = "You have to pass your email")
    private String username;
    @NotBlank(message = "You have to pass your password")
    private String password;

//    @Pattern(message = "Invalid phone number", regexp = "^([1-9][0-9]{8})$")
//    @NotBlank(message = "You have to pass your phone number")
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<UserRole>  roles = new ArrayList<>();
    private Boolean locked = false;
    private Boolean enabled = false;
    private Boolean subscribed = true;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId", updatable = false, insertable = false)
    private List<Passenger> userSchedules;


    public ApplicationUser(String firstName,
                           String lastName,
                           String username,
                           String password,
                           List<UserRole> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public ApplicationUser() {

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
