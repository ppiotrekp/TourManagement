package pl.ppyrczak.busschedulesystem.controller.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.auth.UserService;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.service.PassengerService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserPermission {
    private final UserService userService;
    private final PassengerService passengerService;

    public boolean hasPermissionToAddPassenger(Passenger passenger, Authentication authentication) {
        List<ApplicationUser> users = userService.getAllUsersInfo();
        Long userLoggedId = 0L;

        for (ApplicationUser user : users) {
            if (user.getUsername().equals(authentication.getName())) {
                userLoggedId = user.getId();
            }
        }
        return Objects.equals(userLoggedId, passenger.getUserId());
    }

    public boolean hasPermissionToAddReview(Review review, Authentication authentication) {
        List<ApplicationUser> users = userService.getAllUsersInfo();
        Long userLoggedId = 0L;

        for (ApplicationUser user : users) {
            if (user.getUsername().equals(authentication.getName())) {
                userLoggedId = user.getId();
            }
        }
        return Objects.equals(userLoggedId, passengerService.mapPassengerIdToUserId(review.getPassengerId()));
    }

    public boolean hasPermissionToRetrieveUser(Long id,
                                               HttpServletRequest request,
                                               Authentication authentication) {
        List<ApplicationUser> users = userService.getAllUsersInfo();
        Long currentId = 0L;
        boolean isAdmin = request.isUserInRole("ROLE_ADMIN");

        for (ApplicationUser user : users) {
            if (user.getUsername().equals(authentication.getName())) {
                currentId = user.getId();
            }
        }
        return Objects.equals(currentId, id) || isAdmin;
    }
}
