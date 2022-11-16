package pl.ppyrczak.busschedulesystem.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BusRepository busRepository;

    @Test
    void shouldFindAllByBusId() {
        Bus bus = new Bus("A", "M", 10, "a", null);
        bus.setId(1L);
        busRepository.save(bus);


        Schedule schedule = new Schedule(bus.getId(),
                "Krakow",
                "Malaga",
                LocalDateTime.of(2022, 10, 10, 10, 10),
                LocalDateTime.of(2022, 10, 10, 12, 10),
                "100",
                null, null);

//        Schedule schedule1 = new Schedule(1L,
//                "Krakow",
//                "Malaga",
//                LocalDateTime.of(2022, 10, 10, 10, 10),
//                LocalDateTime.of(2022, 10, 10, 12, 10),
//                "100",
//                null, null);
//
//        Schedule schedule2 = new Schedule(1L,
//                "Krakow",
//                "Malaga",
//                LocalDateTime.of(2022, 10, 10, 10, 10),
//                LocalDateTime.of(2022, 10, 10, 12, 10),
//                "100",
//                null, null);

        schedule.setId(1L);
//        schedule1.setId(2L);
//        schedule2.setId(3L);

        scheduleRepository.save(schedule);
//        scheduleRepository.save(schedule1);
//        scheduleRepository.save(schedule2);

        List<Schedule> schedules = scheduleRepository.findAllByBusId(1L);

        Assertions.assertEquals(schedules.size(), 1);
    }
}