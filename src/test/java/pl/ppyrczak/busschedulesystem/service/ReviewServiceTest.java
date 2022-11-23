package pl.ppyrczak.busschedulesystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private BusRepository busRepository;
    private ReviewService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ReviewService(reviewRepository,
                                       passengerRepository,
                                        scheduleRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldAddReview() {
        //given
        Bus bus = new Bus(1L,
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);
        when(busRepository.findById(bus.getId())).thenReturn(Optional.of(bus));

        Schedule schedule = new Schedule(1L,bus.getId(),
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                100,
                null, null);
        when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));

        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        when(passengerRepository.findById(passenger.getId())).thenReturn(Optional.of(passenger));

        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(passenger);
        schedule.setPassengers(passengerList);

        Review review = new Review(1L, passenger.getId(), schedule.getId(), 4, "ok", LocalDateTime.now());
        given(reviewRepository.save(review)).willReturn(review);
        //when
        underTest.addReview(review);
        //then
        verify(reviewRepository).save(eq(review));
    }

    @Test
    void shouldGetReviewsWithDetailsForSpecificSchedule() {
        //given
        Review review = new Review(1L, 1L, 1L, 4, "ok", LocalDateTime.now());
        Review review1 = new Review(2L, 2L, 1L, 4, "ok", LocalDateTime.now());

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        reviewList.add(review1);

        when(reviewRepository.findAllByScheduleId(1L)).thenReturn(reviewList);
        //when
        List<Review> reviews = underTest.getReviewsWithDetailsForSpecificSchedule(1L);
        //then
        Assertions.assertEquals(reviews.size(), 2);
    }

    @Test
    void shouldDeleteReview(){
        Review review = new Review(1L, 1L, 1L, 4, "ok", LocalDateTime.now());
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        underTest.deleteReview(review.getId());
        verify(reviewRepository).deleteById(review.getId());
    }
}