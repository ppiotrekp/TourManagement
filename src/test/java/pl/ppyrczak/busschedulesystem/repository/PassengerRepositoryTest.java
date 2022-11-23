package pl.ppyrczak.busschedulesystem.repository;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
@RunWith(SpringRunner.class)
class PassengerRepositoryTest {
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

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

    @AfterEach
    void tearDown() {
        passengerRepository.deleteAll();
        busRepository.deleteAll();
        scheduleRepository.deleteAll();
    }

    @Test
    void shouldReturnTakenSeatsById() {
        Schedule schedule = createSchedule();
        Passenger passenger = new Passenger(1L, 1L, schedule.getId(), 1);
        Passenger passenger1 = new Passenger(2L, 1L, schedule.getId(), 2);
        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);

        int expected = passengerRepository.takenSeatsById(schedule.getId());
        Assert.assertEquals(expected, 3);
    }

    @Test
    void shouldFindAllByScheduleIdInWorks() {
        Schedule schedule = createSchedule();

        Passenger passenger = new Passenger(1L, schedule.getId(), 1);
        Passenger passenger1 = new Passenger(1L, schedule.getId(), 1);
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
        Bus bus = new Bus(
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);
        busRepository.save(bus);

        Schedule schedule = createSchedule();
        Schedule schedule1 = createSchedule();
        Passenger passenger = new Passenger(1L, schedule.getId(), 1);
        Passenger passenger1 = new Passenger(1L, schedule1.getId(), 1);
        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);
        List<Long> ids = List.of(passenger.getUserId());

        List<Passenger> passengers = passengerRepository.findAllByUserIdIn(ids);
        System.out.println(passengers.toString());
        Assert.assertEquals(passengers.size(), 2);
    }

    @Test
    void shouldFindByScheduleId() {
        Bus bus = new Bus(
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);
        busRepository.save(bus);

        Schedule schedule = new Schedule(
                bus.getId(),
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                100,
                null, null);
        scheduleRepository.save(schedule);

        Schedule schedule1 = new Schedule(
                bus.getId(),
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                100,
                null, null);
        scheduleRepository.save(schedule1);

        Passenger passenger = new Passenger(1L, schedule.getId(), 2);
        Passenger passenger1 = new Passenger(2L, schedule.getId(), 2);
        Passenger passenger2 = new Passenger(3L, schedule1.getId(), 1);
        passengerRepository.save(passenger);
        passengerRepository.save(passenger1);
        passengerRepository.save(passenger2);

        List<Passenger> passengersList = passengerRepository.findByScheduleId(schedule1.getId());
        Assertions.assertEquals(passengersList.size(), 1);
    }
}