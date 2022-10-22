package pl.ppyrczak.busschedulesystem.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.MockitoAnnotations.openMocks;


@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    private ReviewService reviewService;
    @Autowired
    private PassengerRepository passengerRepository;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewService(reviewRepository,
                passengerRepository,
                scheduleRepository);
    }


    @Test()
    void shouldThrowExceptionDuringAddingReviewBecauseOfInvalidDate() {

        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        Passenger passenger1 = new Passenger(2L, 1L, 2L, 1);
        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);

        List<Passenger> schedulePassengers = new ArrayList<>();
        schedulePassengers.add(passenger);

        Schedule schedule = new Schedule(1L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(2023, 10, 10, 10, 10),
                LocalDateTime.of(2023, 10, 10, 12, 10),
                "100",
                schedulePassengers, null);
        schedule.setId(1L);
        scheduleRepository.save(schedule);
        Review review = new Review(1L, 1L, 1L, 4, "good", LocalDateTime.now());

        String expectedMessage = "You can not add review before arrival";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> reviewService.addReview(review));
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getReviewsForSpecificSchedule() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addReview() {
    }

    @Test
    void deleteReview() {
    }

    @Test
    void getReviewsWithDetailsForSpecificSchedule() {
    }
}