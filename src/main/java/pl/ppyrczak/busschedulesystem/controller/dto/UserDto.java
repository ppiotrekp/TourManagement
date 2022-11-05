package pl.ppyrczak.busschedulesystem.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.ppyrczak.busschedulesystem.security.UserRole;

import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
//    private Collection<UserRole> roles = new ArrayList<>(); //TODO ZROBIC TAK ZEBY ROLE SIE WYSWIETLALY ALE NIE BYLO DUZO ZAPYTAN
    private Boolean locked = false;
    private Boolean enabled = false;
}
