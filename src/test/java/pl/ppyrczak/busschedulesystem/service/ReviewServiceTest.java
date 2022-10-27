package pl.ppyrczak.busschedulesystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

//    @Test
//    void shouldAddReview() {
//        //given
//        Review review = new Review(1L, 1L, 1L, 4, "ok", LocalDateTime.now());
//        given(reviewRepository.save(review)).willReturn(review);
//        //when
//        underTest.addReview(review);
//        //then
//        verify(reviewRepository).save(eq(review)); //TODO NPE
//    }

    @Test
    void shouldDeleteReview() {
        //given
        Review review = new Review(1L, 1L, 1L, 4, "ok", LocalDateTime.now());
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        doNothing().when(reviewRepository).deleteById(review.getId());
        //when
        underTest.deleteReview(review.getId());
        //then
        verify(reviewRepository).deleteById(review.getId());
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
}