package pl.ppyrczak.busschedulesystem.dto.mapper;

import pl.ppyrczak.busschedulesystem.dto.UserDto;
import pl.ppyrczak.busschedulesystem.model.ApplicationUser;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoMapper {

    public static UserDto mapToUserDto(ApplicationUser user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .enabled(user.getEnabled())
                .locked(user.getLocked())
//                .roles(user.getRoles())
                .build();
    }

    public static List<UserDto> mapToUserDtos(List<ApplicationUser> users) {

        return users.stream()
                .map(user -> mapToUserDto(user))
                .collect(Collectors.toList());
    }
}
