package pl.ppyrczak.busschedulesystem.service.logic;

import org.springframework.stereotype.Component;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class Constraint {

    private final ScheduleRepository scheduleRepository;

    public Constraint(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    private List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }
    public List<LocalDateTime> extractAllDeparturesWithTheSameBus(Schedule schedule) {
        List<Schedule> schedules = getSchedules();
        System.out.println(schedules.toString());

        List<LocalDateTime> allDepartures =
                schedules.stream()
                        .filter(s -> Objects.equals(s.getBusId(), schedule.getBusId()))
                        .map(Schedule::getDeparture)
                        .toList();
        System.out.println(allDepartures);
        return allDepartures;
    }

    public List<LocalDateTime> extractAllArrivalsWithTheSameBus(Schedule schedule) {
        List<Schedule> schedules = getSchedules();
        System.out.println(schedules.toString());

        List<LocalDateTime> allArrivals =
                schedules.stream()
                        .filter(s -> Objects.equals(s.getBusId(), schedule.getBusId()))
                        .map(Schedule::getArrival)
                        .toList();
        System.out.println(allArrivals);
        return allArrivals;
    }

    public boolean checkConstraintsForSchedule(Schedule schedule) { //TODO NAPRAWIC TO
        List<LocalDateTime> allDepartures = extractAllDeparturesWithTheSameBus(schedule);
        List<LocalDateTime> allArrivals = extractAllArrivalsWithTheSameBus(schedule);

        return allArrivals.stream() // czy inny przyjazd byl na 3 dni przed kolejnym odjazdem
                .noneMatch(s -> s.plusDays(2L).isAfter(schedule.getDeparture())) &&

                allDepartures.stream()
                        .noneMatch(s -> s.minusDays(2L).isAfter(schedule.getArrival()));
    }

}
