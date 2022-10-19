package pl.ppyrczak.busschedulesystem.service;

import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;
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
    private final ReviewRepository reviewRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, PassengerRepository passengerRepository, ReviewRepository reviewRepository) {
        this.scheduleRepository = scheduleRepository;
        this.passengerRepository = passengerRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).
                orElseThrow();
    }

    public Schedule addSchedule(Schedule schedule) {
//        if (constraint.checkConstraintsForSchedule(schedule)) {
//            return scheduleRepository.save(schedule);
//        } else {
//            throw new RuntimeException("Constraints not passed");
//        }
        return scheduleRepository.save(schedule);
    }

    public Schedule editSchedule(Schedule scheduleToUpdate, Long id) {
        Schedule originalSchedule = scheduleRepository.findById(id)
                .orElseThrow();

        if (originalSchedule.getArrival().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("You can not change finished schedule");
        }

        if (scheduleToUpdate.getArrival().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("You can not set arrival in the past");
        }

        if (scheduleToUpdate.getArrival().isBefore(scheduleToUpdate.getDeparture())) {
            throw new RuntimeException("Arrival can not be before departure");
        }

        return scheduleRepository.findById(id) //TODO ograniczenia nowe typu nie mozna zmienic na date przeszla
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
        List<Review> reviews = reviewRepository.
                findAllByScheduleIdIn(ids);

        allSchedules.forEach(schedule -> schedule.
                setPassengers(extractPassengers(passengers, schedule.getId())));

        allSchedules.forEach(schedule -> schedule.
                setReviews(extractReviews(reviews, schedule.getId())));

        return allSchedules;
    }

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
}
