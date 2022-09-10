package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow();
    }

    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public Schedule editSchedule(Schedule scheduleToUpdate, Long id) {
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setBusId(scheduleToUpdate.getBusId());
                    schedule.setDepartureFrom(scheduleToUpdate.getDepartureFrom());
                    schedule.setDepartureTo(scheduleToUpdate.getDepartureTo());
                    schedule.setDeparture(scheduleToUpdate.getDeparture());
                    schedule.setArrival(scheduleToUpdate.getArrival());
                    schedule.setTicketPrice(scheduleToUpdate.getTicketPrice());
                    return scheduleRepository.save(schedule);
                })
                .orElseGet(() -> {
                    return scheduleRepository.save(scheduleToUpdate);
                });
    }
}
