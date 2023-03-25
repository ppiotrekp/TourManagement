package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ppyrczak.busschedulesystem.exception.runtime.ArrivalBeforeDepartureException;
import pl.ppyrczak.busschedulesystem.exception.runtime.ArrivalInPastException;
import pl.ppyrczak.busschedulesystem.exception.runtime.BusNotAvailableException;
import pl.ppyrczak.busschedulesystem.exception.runtime.FinishedTripException;
import pl.ppyrczak.busschedulesystem.exception.runtime.model.BusNotFoundException;
import pl.ppyrczak.busschedulesystem.exception.runtime.model.ScheduleNotFoundException;
import pl.ppyrczak.busschedulesystem.model.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.registration.email.EmailSender;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;
import pl.ppyrczak.busschedulesystem.repository.UserRepository;
import pl.ppyrczak.busschedulesystem.service.logic.Constraint;
import pl.ppyrczak.busschedulesystem.service.subscription.Subscriber;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static pl.ppyrczak.busschedulesystem.service.util.EmailBuilder.buildEmail;

@Service
@RequiredArgsConstructor
public class ScheduleService implements Subscriber {
    private static final int PAGE_SIZE = 10;
    private final BusRepository busRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final Constraint constraint;

    public List<Schedule> getSchedules(int page, Sort.Direction sort) {
        return scheduleRepository.findAllSchedules(
                PageRequest.of(page, PAGE_SIZE,
                        Sort.by(sort, "departure")));
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).stream()
                .filter(schedule -> schedule.getDeparture().isAfter(now())).
                findFirst().
                orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    public Schedule getScheduleWithPassengersAndReviews(Long id) {
        return scheduleRepository.findById(id).
                orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    public List<Schedule> getSchedules(Schedule schedule) {
        Schedule scheduleToFind = Schedule.builder()
                .departureFrom(schedule.getDepartureFrom())
                .arrivalTo(schedule.getArrivalTo())
                .departure(schedule.getDeparture())
                .arrival(schedule.getArrival())
                .ticketPrice(schedule.getTicketPrice())
                .build();

        return scheduleRepository.findAll(Example.of(scheduleToFind));
    }

    @Transactional
    public Schedule addSchedule(Schedule schedule) {
        if (!constraint.isBusAvaliable(schedule)) {
            throw new BusNotAvailableException();
        }
        List<ApplicationUser> users = userRepository.findAll();

        List<ApplicationUser> subscribers = users.stream()
                .filter(ApplicationUser::getSubscribed)
                .collect(Collectors.toList());

        if (busRepository.findById(schedule.getBusId()).isEmpty()) {
            throw new BusNotFoundException(schedule.getBusId());
        }
        sendInfoAboutNewTrip(schedule, subscribers);
        return scheduleRepository.save(schedule);
    }

    public Schedule editSchedule(Schedule scheduleToUpdate, Long id) {
        if (scheduleRepository.findById(id).isPresent()) {
            Schedule originalSchedule = scheduleRepository.getById(id);
            if (originalSchedule.getArrival().isBefore(now())) {
                throw new FinishedTripException();
            }
        }

        if (scheduleToUpdate.getArrival().isBefore(now())) {
            throw new ArrivalInPastException();
        }

        if (scheduleToUpdate.getArrival().isBefore(scheduleToUpdate.getDeparture())) {
            throw new ArrivalBeforeDepartureException();
        }
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setBusId(scheduleToUpdate.getBusId());
                    schedule.setDepartureFrom(scheduleToUpdate.getDepartureFrom());
                    schedule.setArrivalTo(scheduleToUpdate.getArrivalTo());
                    schedule.setDeparture(scheduleToUpdate.getDeparture());
                    schedule.setArrival(scheduleToUpdate.getArrival());
                    schedule.setTicketPrice(scheduleToUpdate.getTicketPrice());
                    return scheduleRepository.save(schedule);
                })
                .orElseGet(() -> {
                    return addSchedule(scheduleToUpdate);
                });
    }

    public void deleteSchedule(Long id) {
        if (!scheduleRepository.findById(id).isPresent()) {
            throw new ScheduleNotFoundException(id);
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public void sendInfoAboutNewTrip(Schedule schedule, List<ApplicationUser> users) {
        for (ApplicationUser user : users) {
            emailSender.send(user.getUsername(), buildEmail(user.getFirstName(), schedule));
        }
    }

    public List<Schedule> getScheduleByUser(Long id) {
        List<Schedule> schedules = scheduleRepository.findAllSchedules();

        List<Passenger> passengers = scheduleRepository.findAll().stream()
                .map(Schedule::getPassengers)
                .flatMap(List::stream)
                .toList();

        List<Long> ids = passengers.stream()
                .filter(passenger -> passenger.getUserId() == id)
                .map(Passenger::getId)
                .collect(Collectors.toList());

        schedules.forEach(schedule -> schedule.setPassengers(extractPassengers(passengers, schedule.getId())));

        return schedules;
    }

    private List<Passenger> extractPassengers(List<Passenger> passengers, Long id) {
        return passengers.stream()
                .filter(passenger -> passenger.getUserId().equals(id))
                .collect(Collectors.toList());
    }
}
