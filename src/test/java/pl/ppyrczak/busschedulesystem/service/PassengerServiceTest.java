package pl.ppyrczak.busschedulesystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private BusRepository busRepository;
    private PassengerService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new PassengerService(passengerRepository, scheduleRepository, busRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldGetPassengers() {
        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        Passenger passenger2 = new Passenger(3L, 1L, 1L, 1);
        Passenger passenger1 = new Passenger(2L, 2L, 1L, 1);

        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(passenger);
        passengerList.add(passenger1);
        passengerList.add(passenger2);

        when(passengerRepository.findAll()).thenReturn(passengerList);

        List<Passenger> passengers = underTest.getPassengers();

        Assertions.assertEquals(passengers.size(), 3);
    }

    @Test
    void shouldGetPassenger() {
        Passenger passenger = new Passenger(1L, 1L, 1L, 1);
        when(passengerRepository.findById(passenger.getId())).thenReturn(Optional.of(passenger));
        Passenger passengerToFind = underTest.getPassenger(passenger.getId());
        assertThat(passengerToFind).isNotNull();
    }

    @Test
    void shouldAddPassenger() {
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
        given(passengerRepository.save(passenger)).willReturn(passenger);
        underTest.addPassenger(passenger);
        verify(passengerRepository).save(passenger);
    }

    @Test
    void shouldGetPassengersForSpecificSchedule() {

        Bus bus = new Bus(1L,
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);
        when(busRepository.findById(bus.getId())).thenReturn(Optional.of(bus));

        Schedule schedule = new Schedule(1L,
                1L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                100,
                null, null);
        when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));

        Passenger passenger = new Passenger(1L, 1L, schedule.getId(), 1);
        Passenger passenger2 = new Passenger(3L, 1L, schedule.getId(), 1);
        Passenger passenger1 = new Passenger(2L, 2L, schedule.getId(), 1);

        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(passenger);
        passengerList.add(passenger1);
        passengerList.add(passenger2);

        when(passengerRepository.findByScheduleId(1L)).thenReturn(passengerList);

        List<Passenger> passengers = underTest.getPassengersForSpecificSchedule(1L);

        assertEquals(passengers.size(), 3);
    }
}