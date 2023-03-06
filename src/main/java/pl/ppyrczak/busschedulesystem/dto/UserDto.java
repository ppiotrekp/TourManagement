package pl.ppyrczak.busschedulesystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private Boolean locked;
    private Boolean enabled;
}
