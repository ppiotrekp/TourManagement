package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;


    @GetMapping("/schedules/{id}/reviews")
    public List<Review> getReviewsForSpecificSchedule(@PathVariable Long id) {
        return reviewService.getReviewsForSpecificSchedule(id);
    }

    @PostMapping("/review")
    public Review addReview(@Valid @RequestBody Review review) {
        return reviewService.addReview(review);
    }


    @DeleteMapping("/review/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}
