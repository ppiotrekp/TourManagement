package pl.ppyrczak.busschedulesystem.service.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
class ConstraintTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    private Constraint underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new Constraint(scheduleRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void isBusAvaliable() {

//        Schedule schedule = new Schedule(1L,
//                "Krakow",
//                "Malaga",
//                LocalDateTime.of(2022, 10, 10, 10, 10),
//                LocalDateTime.of(2022, 10, 10, 12, 10),
//                "100",
//                null, null);
//
//        Schedule schedule1 = new Schedule(2L,
//                "Krakow",
//                "Malaga",
//                LocalDateTime.of(2022, 10, 10, 10, 10),
//                LocalDateTime.of(2022, 10, 10, 12, 10),
//                "100",
//                null, null);
//
//        given(scheduleRepository.save(schedule)).willReturn(schedule);
//        //when

//todo nie przechodzi

    }
}