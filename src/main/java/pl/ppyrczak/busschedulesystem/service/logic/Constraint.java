package pl.ppyrczak.busschedulesystem.service.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.util.List;

@Component
@Slf4j
public class Constraint {

    private final ScheduleRepository scheduleRepository;

    public Constraint(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    private List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public boolean isBusAvaliable(Schedule schedule) {
        List<Schedule> schedules = scheduleRepository.findAllByBusId(schedule.getBusId());
        boolean returnStat = true;

        log.info("schedules: " + schedules.size());
        for (Schedule scheduleIterator : schedules) {
            if ((scheduleIterator.getArrival().isBefore(schedule.getDeparture()) &&
                    HOURS.between(scheduleIterator.getArrival(), schedule.getDeparture()) < 24L ) ||

                    (schedule.getArrival().isBefore(scheduleIterator.getDeparture()) &&
                            HOURS.between(schedule.getArrival(), scheduleIterator.getDeparture()) < 24L )) {
                returnStat = false;
            }
        }
        return returnStat;
    }
}
