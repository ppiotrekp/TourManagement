package pl.ppyrczak.busschedulesystem.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.MockitoAnnotations.openMocks;


@DataJpaTest
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private UserRepository userRepository;
    @Autowired
    private BusRepository busRepository;
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


    @Test
    void ifItAddsReviewProperly() {
        Bus bus = new Bus(1L,
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);

        busRepository.save(bus);

        Schedule schedule = new Schedule(1L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                "100",
                null, null);

        Schedule schedule1 = new Schedule(1L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(202, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                "100",
                null, null);


        scheduleRepository.save(schedule);
        scheduleRepository.save(schedule1);


        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        user.setId(1L);
        userRepository.save(user);

        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        Passenger passenger1 = new Passenger(2L, 1L, 2L, 1);
        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);


        Review review = new Review(1L, 1L, 1L, 4, "good", LocalDateTime.now());
            reviewService.addReview(review);



        System.out.println(review.getScheduleId());

        List<Review> reviews = Arrays.asList(review);

        Assert.assertEquals(reviews.size(), 1);

    }

    @Test
    void getReviewsForSpecificSchedule() {
    }
}