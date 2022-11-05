package pl.ppyrczak.busschedulesystem.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ppyrczak.busschedulesystem.model.Review;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
    }

    @Test
    void shouldFindAllByScheduleIdIn() {
        Review review = new Review(1L, 1L, 1L, 4, "Good", LocalDateTime.now());
        Review review1 = new Review(2L, 2L, 2L, 4, "ok", LocalDateTime.now());

        reviewRepository.save(review);
        reviewRepository.save(review1);

        List<Review> reviews = reviewRepository.findAllByScheduleIdIn(Arrays.asList(1L, 2L));
        Assertions.assertEquals(reviews.size(), 2);
    }

    @Test
    void shouldFindAllByScheduleId() {
        Review review = new Review(1L, 1L, 1L, 4, "Good", LocalDateTime.now());
        Review review1 = new Review(2L, 1L, 1L, 4, "Good", LocalDateTime.now());

        reviewRepository.save(review);
        reviewRepository.save(review1);

        List<Review> reviews = reviewRepository.findAllByScheduleId(1L);
        Assertions.assertEquals(reviews.size(), 2);
    }
}