package pl.ppyrczak.busschedulesystem.repository;


import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
//@RunWith(SpringRunner.class)
class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private BusRepository busRepository = Mockito.mock(BusRepository.class);
    private ScheduleRepository scheduleRepository = Mockito.mock(ScheduleRepository.class);

    @BeforeEach
    void setUp() {
        passengerRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        passengerRepository.deleteAll();
    }

    @Test
    void shouldReturnTakenSeatsById() {
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
    void shouldFindAllByScheduleIdInWorks() {
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
    void shouldFindAllByUserIdIn() {

        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        Passenger passenger1 = new Passenger(2L, 1L, 2L, 1);

        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);

        List<Long> ids = Arrays.asList(1L);

        List<Passenger> passengers = passengerRepository.findAllByUserIdIn(ids);
        System.out.println(passengers.toString());
        Assert.assertEquals(passengers.size(), 2);
    }

    @Test
    void shouldFindByScheduleId() {
        Passenger passenger = new Passenger(1L, 1L, 1L, 2);
        Passenger passenger1 = new Passenger(2L, 2L, 1L, 2);
        Passenger passenger2 = new Passenger(3L, 3L, 2L, 1);

        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);
        passengerRepository.save(passenger2);

        List<Passenger> passengersList = passengerRepository.findByScheduleId(2L);
        Assertions.assertEquals(passengersList.size(), 1);
    }
}