package pl.ppyrczak.busschedulesystem.service;

import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;
import pl.ppyrczak.busschedulesystem.service.logic.Constraint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;
    private final Constraint constraint;

    public ScheduleService(ScheduleRepository scheduleRepository, PassengerRepository passengerRepository, Constraint constraint) {
        this.scheduleRepository = scheduleRepository;
        this.passengerRepository = passengerRepository;
        this.constraint = constraint;
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).
                orElseThrow();
    }

    public Schedule addSchedule(Schedule schedule) {
        if (constraint.checkConstraintsForSchedule(schedule)) {
            return scheduleRepository.save(schedule);
        } else {
            throw new RuntimeException("Constraints not passed");
        }
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

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getSchedulesWithPassengersAndReviews() {
        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Long> ids = allSchedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());
        List<Passenger> passengers = passengerRepository.
                findAllByScheduleIdIn(ids);

        allSchedules.forEach(schedule -> schedule.
                setPassengers(extractPassengers(passengers, schedule.getId())));
        return allSchedules;
    }

    private List<Passenger> extractPassengers(List<Passenger> passengers, Long id) {
        return passengers.stream()
                .filter(passenger -> Objects.equals(passenger.getScheduleId(), id))
                .collect(Collectors.toList());
    }
}
