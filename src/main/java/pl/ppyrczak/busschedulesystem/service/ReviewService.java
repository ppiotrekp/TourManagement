package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PassengerRepository passengerRepository;
    private final ScheduleRepository scheduleRepository;

    public Review addReview(Review review) {
        review.setCreated();
        if (checkConstraintsForReview(review) == false) {
            throw new RuntimeException("Error during adding review");
        } else {
            return reviewRepository.save(review);
        }
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsForSpecificSchedule(Long id) {
        List<Passenger> passengers = passengerRepository.findByScheduleId(id);
        List<Review> allReviews = passengers.stream()
                .map(passenger -> passenger.getReview())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return allReviews;
    }

    public boolean checkConstraintsForReview(Review review) {
        Passenger passenger = passengerRepository.findById(review.getPassengerId()).
                orElseThrow(() -> new RuntimeException("Passenger not found"));
        Schedule schedule = scheduleRepository.findById(passenger.getScheduleId()).
                orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (reviewRepository.existsByPassengerId(review.getPassengerId()) ||
                review.getCreated().isBefore(schedule.getArrival()))
            return false;
        else
            return true;
    }
}
