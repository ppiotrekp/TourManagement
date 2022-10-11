package pl.ppyrczak.busschedulesystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.time.LocalDateTime;

@SpringBootTest
class ScheduleServiceTest {

    final ScheduleService scheduleService;

    ScheduleServiceTest(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Test
    void shouldCheckSufficientBreakBetweenSchedules(Schedule schedule) {
        //given
        Bus bus = new Bus();
        bus.setId(1L);

        schedule.setBusId(bus.getId());
        schedule.setArrival(LocalDateTime.of(2022, 10, 5, 20, 20));
        schedule.setDeparture(LocalDateTime.of(2022, 10, 5, 22, 20));

        Schedule schedule1 = new Schedule();
        schedule1.setBusId(bus.getId());
        schedule1.setArrival(LocalDateTime.of(2022, 12, 5, 22, 20));
        schedule.setDeparture(LocalDateTime.of(2022, 10, 5, 22, 20));

        //scheduleService.checkConstraintsForSchedule(schedule);

    }
}