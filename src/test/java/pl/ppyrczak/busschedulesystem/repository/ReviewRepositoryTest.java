package pl.ppyrczak.busschedulesystem.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PassengerRepository passengerRepository;


    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
    }

    private Bus createBus() {
        Bus bus = new Bus();
        bus.setBrand("Mercedes");
        bus.setModel("V200");
        bus.setEquipment("kitchen");
        bus.setPassengersLimit(20);
        busRepository.save(bus);
        return bus;
    }

    private Schedule createSchedule() {
        Schedule schedule = new Schedule();
        schedule.setBusId(createBus().getId());
        schedule.setDepartureFrom("Krakow");
        schedule.setDepartureTo("Malaga");
        schedule.setDeparture(LocalDateTime.of(2023, 10, 10, 10, 10));
        schedule.setArrival(LocalDateTime.of(2023, 10, 10, 12, 10));
        schedule.setTicketPrice(100);
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Test
    void shouldFindAllByScheduleIdIn() {
        Schedule schedule = createSchedule();
        Schedule schedule1 = createSchedule();
        Passenger passenger = new Passenger(1L, 1L, schedule.getId(), 1);
        Passenger passenger1 = new Passenger(2L, 1L, schedule.getId(), 2);
        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);

        Review review = new Review(passenger.getId(), schedule.getId(), 4, "Good", LocalDateTime.now());
        Review review1 = new Review(passenger1.getId(), schedule1.getId(), 4, "ok", LocalDateTime.now());

        reviewRepository.save(review);
        reviewRepository.save(review1);

        List<Review> reviews = reviewRepository.findAllByScheduleIdIn(Arrays.asList(schedule.getId(), schedule1.getId()));
        Assertions.assertEquals(reviews.size(), 2);
    }

//    @Test
//    void shouldFindAllByScheduleId() {
//        Schedule schedule = createSchedule();
//        Passenger passenger = new Passenger(1L, 1L, schedule.getId(), 1);
//        Passenger passenger1 = new Passenger(2L, 1L, schedule.getId(), 2);
//        passengerRepository.save(passenger);
//        passengerRepository.save(passenger1);
//
//        Review review = new Review(1L, passenger.getId(), schedule.getId(), 4, "Good", LocalDateTime.now());
//        Review review1 = new Review(2L, passenger.getId(), schedule.getId(), 4, "Good", LocalDateTime.now());
//        reviewRepository.save(review);
//        reviewRepository.save(review1);
//
//        List<Review> reviews = reviewRepository.findAllByScheduleId(schedule.getId());
//        Assertions.assertEquals(reviews.size(), 2);
//    }
}