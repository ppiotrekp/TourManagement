package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.auth.UserService;
import pl.ppyrczak.busschedulesystem.exception.UserNotAuthorizedException;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.service.PassengerService;
import pl.ppyrczak.busschedulesystem.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final PassengerService passengerService;

    @GetMapping("/schedules/{id}/reviews")
    public List<Review> getReviewsWithDetailsForSpecificSchedule(@PathVariable Long id) {
        return reviewService.getReviewsWithDetailsForSpecificSchedule(id);
    }

    @PostMapping("/review")
    public Review addReview(@Valid @RequestBody Review review,
                            Authentication authentication) throws UserNotAuthorizedException {

        List<ApplicationUser> users = userService.getAllUsersInfo();
        Long currentId = 0L;

        for (ApplicationUser user : users) {
            if (user.getUsername().equals(authentication.getName())) {
                currentId = user.getId();
            }
        }

        if (currentId == passengerService.mapPassengerIdToUserId(review.getPassengerId())) {
            return reviewService.addReview(review);
        } else {
            throw new UserNotAuthorizedException();
        }
    }

    @PreAuthorize("hasRole('ROLE_USER')")

    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
