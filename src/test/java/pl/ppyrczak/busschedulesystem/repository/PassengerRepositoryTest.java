package pl.ppyrczak.busschedulesystem.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThan;

import static org.hamcrest.MatcherAssert.assertThat;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
//@RunWith(SpringRunner.class)
class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository = Mockito.mock(PassengerRepository.class);
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private BusRepository busRepository = Mockito.mock(BusRepository.class);
    private ScheduleRepository scheduleRepository = Mockito.mock(ScheduleRepository.class);

    @Test
    void takenSeatsById() {
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

        scheduleRepository.save(schedule);

        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        user.setId(1L);
        userRepository.save(user);

        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        Passenger passenger1 = new Passenger(2L, 1L, 1L, 1);
        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);

        int expected = passengerRepository.takenSeatsById(1L);
        Assert.assertEquals(expected, 2);
    }

    @Test
    void checkIffindAllByScheduleIdInWorks() {
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

        scheduleRepository.save(schedule);

        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        user.setId(1L);
        userRepository.save(user);

        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        Passenger passenger1 = new Passenger(2L, 1L, 1L, 1);
        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);

        List<Passenger> passengers = Arrays.asList(passenger, passenger1);

        List<Long> ids = passengers.stream()
                .map(Passenger::getId)
                .collect(Collectors.toList());

        List<Passenger> result = passengerRepository.findAllByScheduleIdIn(ids);
        Assert.assertEquals(result.size(), passengers.size());

    }

    @Test
    void findAllByUserIdIn() {

        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);
        user.setId(1L);

        ApplicationUser user1 = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);
        user1.setId(2L);

        userRepository.save(user);
        userRepository.save(user1);

        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        Passenger passenger1 = new Passenger(2L, 1L, 2L, 1);

        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);

        List<Long> ids = Arrays.asList(user.getId());

        List<Passenger> passengers = passengerRepository.findAllByUserIdIn(ids);
        System.out.println(passengers.toString());
        Assert.assertEquals(passengers.size(), 2);
    }
}