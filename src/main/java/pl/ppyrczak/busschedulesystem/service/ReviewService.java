package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PassengerRepository passengerRepository;

    public Review addReview(Review review) {
        Passenger passenger = passengerRepository.findById(review.getPassengerId()).orElseThrow();
        System.out.println(review.getPassengerId());
        if (reviewRepository.existsByPassengerId(review.getPassengerId()) ) {
            throw new RuntimeException("You have a review about this transit");
            //TODO wywalic wyjatek
        }

        else if (passenger.getScheduleId() != review.getScheduleId()) {
            throw new RuntimeException("Bad schedule id");
        }

        else {
            return reviewRepository.save(review);
        }

    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
