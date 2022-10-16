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
        if (!checkIfReviewIsNotBeforeArrival(review)) {
            throw new RuntimeException("You can not add review before arrival");
        } /*else if (!checkIfScheduleHasPassenger(review)) {
            throw new RuntimeException("passenger does not exist");
        }*/ else {
            return reviewRepository.save(review);
        }

    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }


    public List<Review> getReviewsForSpecificSchedule(Long id) { //TODO N+1
        /*List<Passenger> passengers = passengerRepository.findByScheduleId(id);
        List<Review> allReviews = passengers.stream()
                .map(passenger -> passenger.getReview())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return allReviews;*/
        return null;
    }

    private boolean checkIfReviewIsNotBeforeArrival(Review review) { //chyba dziala
        boolean returnStat = true;
        Long scheduleId = review.getScheduleId();
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        if (review.getCreated().isBefore(schedule.getArrival())) {
            returnStat = false;
        }

        return returnStat;
    }

    private boolean checkIfScheduleHasPassenger(Review review) {
        boolean returnStat = false;
        /*Long scheduleId = review.getScheduleId();
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        Passenger passenger = passengerRepository.findByScheduleId(scheduleId);
        if (scheduleRepository.existsScheduleByPassengers(passenger.getId())) {
            returnStat = true;
        }*/

        return returnStat;
    }
}
