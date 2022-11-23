package pl.ppyrczak.busschedulesystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.registration.email.EmailSender;
import pl.ppyrczak.busschedulesystem.repository.*;
import pl.ppyrczak.busschedulesystem.service.logic.Constraint;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.springframework.data.domain.Sort.by;

@RunWith(MockitoJUnitRunner.class)

class ScheduleServiceTest {
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailSender emailSender;
    @Mock
    private Constraint constraint;
    private AutoCloseable autoCloseable;
    private BusRepository busRepository;
    private ScheduleService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ScheduleService(busRepository,
                scheduleRepository,
                passengerRepository,
                reviewRepository,
                userRepository,
                emailSender,
                constraint);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldGetSchedules() {
        Schedule schedule = new Schedule(1L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                100,
                null, null);

        Schedule schedule1 = new Schedule(1L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 7, 10, 10, 10),
                LocalDateTime.of(2022, 7, 10, 12, 10),
                100,
                null, null);

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule);
        scheduleList.add(schedule1);

        Pageable pageable = PageRequest.of(0, 10, by("departure"));

        when(scheduleRepository.findAllSchedules(Pageable.ofSize(10))).thenReturn(new ArrayList<>());

        underTest.getSchedules(0, Sort.Direction.ASC);

        verify(scheduleRepository).findAllSchedules(pageable);
    }

    @Test
    void shouldGetSchedule() {
        Schedule schedule = new Schedule(1L,
                1L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                100,
                null, null);


        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));

        Schedule scheduleToFind = underTest.getSchedule(schedule.getId());

        assertThat(scheduleToFind).isNotNull();
    }

//    @Test
//    void shouldAddSchedule() throws RuntimeException{
//        Schedule schedule = new Schedule(1L,
//                3L,
//                "Krakow",
//                "Malaga",
//                LocalDateTime.of(2023, 1, 10, 10, 10),
//                LocalDateTime.of(2023, 1, 10, 12, 10),
//                "100",
//                null, null);
//
//
//        given(scheduleRepository.save(schedule)).willReturn(schedule);
//
//        underTest.addSchedule(schedule);
//        verify(scheduleRepository).save(eq(schedule));
//    }//TODO NOT AVAIALBLE

//    @Test
//    void shouldNotAddSchedule() {
//        Schedule schedule = new Schedule(1L,
//                3L,
//                "Krakow",
//                "Malaga",
//                LocalDateTime.of(2022, 10, 10, 10, 10),
//                LocalDateTime.of(2022, 10, 10, 12, 10),
//                "100",
//                null, null);
//
//        given(scheduleRepository.save(schedule)).willReturn(schedule);
//
//        underTest.addSchedule(schedule);
//
//        verify(scheduleRepository).save(eq(schedule));
//    }

//    @Test
//    void shouldEditSchedule() {
//    } TODO

    @Test
    void shouldDeleteSchedule() {
        Schedule schedule = new Schedule(1L,
                3L,
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                100,
                null, null);

        when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));
        underTest.deleteSchedule(schedule.getId());
        verify(scheduleRepository).deleteById(schedule.getId());
    }

//    @Test
//    void shouldGetSchedulesWithPassengersAndReviews() { //TODO
//    }
//
//    @Test
//    void shouldSendInfoAboutNewTrip() {
//    }
}