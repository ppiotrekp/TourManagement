package pl.ppyrczak.busschedulesystem.model;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public UserRole(String name) {
        this.name = name;
    }

    public UserRole() {

    }
}
