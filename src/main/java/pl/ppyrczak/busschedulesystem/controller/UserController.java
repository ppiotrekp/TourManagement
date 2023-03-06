package pl.ppyrczak.busschedulesystem.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.model.ApplicationUser;
import pl.ppyrczak.busschedulesystem.service.UserService;
import pl.ppyrczak.busschedulesystem.dto.UserDto;
import pl.ppyrczak.busschedulesystem.dto.mapper.UserDtoMapper;
import pl.ppyrczak.busschedulesystem.dto.UserHistoryDto;
import pl.ppyrczak.busschedulesystem.controller.util.UserPermission;
import pl.ppyrczak.busschedulesystem.exception.illegalaccess.UserNotAuthorizedException;
import pl.ppyrczak.busschedulesystem.security.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserPermission userPermission;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public List<UserDto> getUsers(@RequestParam(required = false) int page, Sort.Direction sort) {
        int pageNumber = page >= 0 ? page : 0;
        return UserDtoMapper.mapToUserDtos(userService.getUsers(pageNumber, sort));
    }

    @GetMapping("/users/{id}")
    public ApplicationUser getUsers(@PathVariable Long id,
                                    HttpServletRequest request,
                                    Authentication authentication) throws UserNotAuthorizedException {
        if (!userPermission.hasPermissionToRetrieveUser(id, request, authentication)) {
            throw new UserNotAuthorizedException();
        }
        return userService.getUser(id);
    }

    @GetMapping("/users/{id}/history")
    public List<UserHistoryDto> getUserHistory(@PathVariable Long id) {
        return userService.getUserHistory(id);
    }

    @ResponseStatus(CREATED)
    @PostMapping("/roles")
    public UserRole saveRole(@RequestBody UserRole role) {
        return userService.saveRole(role);
    } //todo stworzyc roleservice

    @GetMapping("/api/token/refresh")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                ApplicationUser user = userService.getUser(username);

                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles()
                                .stream()
                                .map(UserRole::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
// todo przeniesc do service
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

