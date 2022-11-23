package pl.ppyrczak.busschedulesystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.exception.runtime.IllegalDateException;
import pl.ppyrczak.busschedulesystem.exception.runtime.IllegalPassengerException;
import pl.ppyrczak.busschedulesystem.exception.runtime.ResourceNotFoundException;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;

@Service
public class ReviewService {
    private static final int PAGE_SIZE = 5;
    private final ReviewRepository reviewRepository;
    private final PassengerRepository passengerRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, PassengerRepository passengerRepository, ScheduleRepository scheduleRepository) {
        this.reviewRepository = reviewRepository;
        this.passengerRepository = passengerRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Review addReview(Review review) {
        review.setCreated();
        Schedule schedule = scheduleRepository.findById(review.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Schedule with id " + review.getScheduleId() + " does not exist"
                ));
        if (!reviewIsNotBeforeArrival(review)) {
            throw new IllegalDateException(schedule.getArrival());
        } else if (!scheduleHasPassenger(review)) {
            throw new IllegalPassengerException();
        } else {
            return reviewRepository.save(review);
        }
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Review with id " + id + " does not exist");
        }
        reviewRepository.deleteById(id);
    }

    public List<Review> getReviewsForSpecificSchedule(Long id,
                                                      int page,
                                                      Sort.Direction sort) {
        List<Review> reviews = reviewRepository.
                findAllByScheduleId(id,  PageRequest.of(page, PAGE_SIZE,
                        Sort.by(sort, "created")));
        return reviews;
    }

    private boolean reviewIsNotBeforeArrival(Review review) {
        boolean returnStat = true;
        Long scheduleId = review.getScheduleId();
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        if (review.getCreated().isBefore(schedule.getArrival())) {
            returnStat = false;
        }
        return returnStat;
    }

    private boolean scheduleHasPassenger(Review review) {
        boolean returnStat = false;
        Long scheduleId = review.getScheduleId();
        Long passengerId = review.getPassengerId();
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        Passenger passenger = passengerRepository.findById(passengerId).orElseThrow();

        if (schedule.getPassengers().contains(passenger)) {
            returnStat = true;
        }

        return returnStat;
    }
}
