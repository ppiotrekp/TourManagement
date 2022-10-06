package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ReviewRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PassengerRepository passengerRepository;
    private final ReviewRepository reviewRepository;
    private final BusRepository busRepository;

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow();
    }

    public Schedule addSchedule(Schedule schedule) {
       /* List<Schedule> schedules = scheduleRepository.findAllByBusId(schedule.getBusId());
        for (Schedule scheduleIterator : schedules) {
            long daysDifference = 3;
            if (scheduleRepository.existsByBusId(scheduleIterator.getBusId())
                    && (DAYS.between(scheduleIterator.getArrival(), schedule.getDeparture()) < daysDifference ||
                    DAYS.between(schedule.getArrival(), scheduleIterator.getDeparture()) < daysDifference)) {
                        throw new RuntimeException("Too less time to prepare bus");
            }
        }*/
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

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    //@Cacheable(cacheNames = "SchedulesWithPassengersAndReviews")
    public List<Schedule> getSchedulesWithPassengersAndReviews() {
        List<Schedule> allSchedules = scheduleRepository.findAll();
        List<Long> ids = allSchedules.stream()
                .map(schedule -> schedule.getId())
                .collect(Collectors.toList());
        List<Passenger> passengers = passengerRepository.findAllByScheduleIdIn(ids);

        /*List<Review> allReviews = reviewRepository.findAllByPassengerIdIn(ids);
        List<Long> schedulesId = allReviews.stream()
                .map(review -> review.getPassengerId())
                .collect(Collectors.toList());
        */


        allSchedules.forEach(schedule -> schedule.setPassengers(extractPassengers(passengers, schedule.getId())));
       // allSchedules.forEach(schedule -> schedule.setReviews(extractReviews(allReviews, schedule.getId())));
        return allSchedules;
    }

    /*private List<Review> extractReviews(List<Review> reviews, Long id) {
        return reviews.stream()
                .filter(review -> review.getScheduleId() == id)
                .collect(Collectors.toList());
    }
*/
    private List<Passenger> extractPassengers(List<Passenger> passengers, Long id) {
        return passengers.stream()
                .filter(passenger -> passenger.getScheduleId() == (id))
                .collect(Collectors.toList());
    }
}
