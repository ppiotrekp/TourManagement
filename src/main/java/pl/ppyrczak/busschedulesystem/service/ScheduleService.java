package pl.ppyrczak.busschedulesystem.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.exception.runtime.BusNotAvailableException;
import pl.ppyrczak.busschedulesystem.exception.runtime.ResourceNotFoundException;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.registration.email.EmailSender;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;
import pl.ppyrczak.busschedulesystem.repository.UserRepository;
import pl.ppyrczak.busschedulesystem.service.logic.Constraint;
import pl.ppyrczak.busschedulesystem.service.subscription.Subscriber;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static pl.ppyrczak.busschedulesystem.service.util.EmailBuilder.buildEmail;

@Service
public class ScheduleService implements Subscriber {
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final EmailSender emailSender;
    private final Constraint constraint;

    public ScheduleService(ScheduleRepository scheduleRepository,
                           PassengerRepository passengerRepository,
                           ReviewRepository reviewRepository,
                           UserRepository userRepository, EmailSender emailSender,
                           Constraint constraint) {
        this.scheduleRepository = scheduleRepository;
        this.passengerRepository = passengerRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.emailSender = emailSender;
        this.constraint = constraint;
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        "Schedule with id " + id + " does not exist"));
    }

    public List<Schedule> getSchedules(Schedule schedule) {
        Schedule scheduleToFind = Schedule.builder()
                .departureFrom(schedule.getDepartureFrom())
                .departureTo(schedule.getDepartureTo())
                .departure(schedule.getDeparture())
                .arrival(schedule.getArrival())
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

        sendInfoAboutNewTrip(schedule, subscribers);
        return scheduleRepository.save(schedule);
    }

    public Schedule editSchedule(Schedule scheduleToUpdate, Long id) {

        if (scheduleRepository.findById(id).isPresent()) {
            Schedule originalSchedule = scheduleRepository.getById(id);
            if (originalSchedule.getArrival().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("You can not change finished schedule");
            }
        }

        if (scheduleToUpdate.getArrival().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("You can not set arrival in the past");
        }

        if (scheduleToUpdate.getArrival().isBefore(scheduleToUpdate.getDeparture())) {
            throw new RuntimeException("Arrival can not be before departure");
        } //todo obsluzyc wyjatki

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
                    return addSchedule(scheduleToUpdate);
                });
    }

    public void deleteSchedule(Long id) {
        if (!scheduleRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Schedule with id " + id + " does not exist");
        }
        scheduleRepository.deleteById(id);
    }

    public List<Schedule> getSchedulesWithPassengersAndReviews() {
        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Long> ids = allSchedules.stream()
                .map(Schedule::getId)
                .collect(Collectors.toList());
        List<Passenger> passengers = passengerRepository.
                findAllByScheduleIdIn(ids);
        List<Review> reviews = reviewRepository.
                findAllByScheduleIdIn(ids);

        allSchedules.forEach(schedule -> schedule.
                setPassengers(extractPassengers(passengers, schedule.getId())));

        allSchedules.forEach(schedule -> schedule.
                setReviews(extractReviews(reviews, schedule.getId())));

        return allSchedules;
    } //todo przeniesc to do passenger service bo to wyswietla pasazerow a nie przejazdy

    private List<Passenger> extractPassengers(List<Passenger> passengers, Long id) {
        return passengers.stream()
                .filter(passenger -> Objects.equals(passenger.getScheduleId(), id))
                .collect(Collectors.toList());
    }

    private List<Review> extractReviews(List<Review> reviews, Long id) {
        return reviews.stream()
                .filter(review -> Objects.equals(review.getScheduleId(), id))
                .collect(Collectors.toList());
    }

    @Override
    public void sendInfoAboutNewTrip(Schedule schedule, List<ApplicationUser> users) {
        for (ApplicationUser user : users) {
            emailSender.send(user.getUsername(),
                            buildEmail(user.getFirstName(), schedule));
        }
    }
    public List<Schedule> getAllSchedules(int page, Sort.Direction sort) {
        return scheduleRepository.findAllSchedules(
                PageRequest.of(page, 3,
                        Sort.by(sort, "busId")));
    }
}
