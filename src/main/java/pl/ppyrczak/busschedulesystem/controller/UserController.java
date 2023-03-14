package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.controller.util.UserPermission;
import pl.ppyrczak.busschedulesystem.dto.UserDto;
import pl.ppyrczak.busschedulesystem.dto.UserHistoryDto;
import pl.ppyrczak.busschedulesystem.dto.mapper.UserDtoMapper;
import pl.ppyrczak.busschedulesystem.exception.illegalaccess.UserNotAuthorizedException;
import pl.ppyrczak.busschedulesystem.model.UserRole;
import pl.ppyrczak.busschedulesystem.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserPermission userPermission;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(required = false) int page) {
        int pageNumber = page >= 0 ? page : 0;
        return UserDtoMapper.mapToUserDtos(userService.getUsers(pageNumber));
    }

    @GetMapping("/users/{id}")
    public UserDto getUser(@PathVariable Long id,
                                    HttpServletRequest request,
                                    Authentication authentication) throws UserNotAuthorizedException {
        if (!userPermission.hasPermissionToRetrieveUser(id, request, authentication)) {
            throw new UserNotAuthorizedException();
        }
        return UserDtoMapper.mapToUserDto(userService.getUser(id));
    }

    @GetMapping("/users/{id}/history")
    public List<UserHistoryDto> getUserHistory(@PathVariable Long id,
                                               @RequestParam(required = false) Integer page,
                                               Direction sort) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        Direction sortDirection = sort != null ? sort : Direction.ASC;
        return userService.getUserHistory(id, pageNumber, sortDirection);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/roles")
    public UserRole saveRole(@RequestBody UserRole role) {
        return userService.saveRole(role);
    }

    @GetMapping("/ids/{username}")
    public Long getIdByUsername(@PathVariable String username) {
        return userService.mapUsernameToId(username);
    }
}

