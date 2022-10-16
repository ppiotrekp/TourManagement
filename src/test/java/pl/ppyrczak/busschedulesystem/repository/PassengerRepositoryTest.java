package pl.ppyrczak.busschedulesystem.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


class PassengerRepositoryTest {
    private final PassengerRepository passengerRepository;


    @Autowired
    PassengerRepositoryTest(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    @Test
    void takenSeatsById() {
    }

    @Test
    void checkIffindAllByScheduleIdInWorks() {
        Bus bus = new Bus(1L,
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);

        Schedule schedule = new Schedule(1L,
                1L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                "100",
                null);

        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        user.setId(1L);

        List<Long> ids= new ArrayList<>();
        ids.add(schedule.getId());

        Passenger passenger = new Passenger(1L, 1L, 1L, 1, null);


        List<Passenger> result = passengerRepository.findAllByScheduleIdIn(ids);
       // MatcherAssert.assertThat();

    }

    @Test
    void findAllByUserIdIn() {
    }

    @Test
    void findByScheduleId() {
    }
}