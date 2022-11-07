package pl.ppyrczak.busschedulesystem.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.security.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
