package pl.ppyrczak.busschedulesystem.service.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

@Component
@Slf4j
public class Constraint {
    private final ScheduleRepository scheduleRepository;

    public Constraint(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public boolean isBusAvaliable(Schedule schedule) {
        List<Schedule> schedules = scheduleRepository.findAllByBusId(schedule.getBusId());
        boolean returnStat = true;

        for (Schedule scheduleIterator : schedules) {
            if ((scheduleIterator.getArrival().isBefore(schedule.getDeparture()) &&
                    HOURS.between(scheduleIterator.getArrival(), schedule.getDeparture()) < 24L ) ||

                    (schedule.getArrival().isBefore(scheduleIterator.getDeparture()) &&
                            HOURS.between(schedule.getArrival(), scheduleIterator.getDeparture()) < 24L ) ||

                    schedule.getArrival().isEqual(scheduleIterator.getArrival()) ||
                    schedule.getArrival().isEqual(scheduleIterator.getDeparture()) ||
                    schedule.getArrival().isEqual(scheduleIterator.getArrival()) ||
                    schedule.getArrival().isEqual(scheduleIterator.getDeparture())
            ) {
                returnStat = false;
            }
        }
        return returnStat;
    }
}
