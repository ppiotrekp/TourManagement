package pl.ppyrczak.busschedulesystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.exception.runtime.AllSeatsTakenException;
import pl.ppyrczak.busschedulesystem.exception.runtime.ResourceNotFoundException;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final ScheduleRepository scheduleRepository;
    private final BusRepository busRepository;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository,
                            ScheduleRepository scheduleRepository,
                            BusRepository busRepository) {
        this.passengerRepository = passengerRepository;
        this.scheduleRepository = scheduleRepository;
        this.busRepository = busRepository;
    }

    public List<Passenger> getPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassenger(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Passenger with id " + id + " does not exist"));
    }

    public Passenger addPassenger(Passenger passenger) {
        Schedule schedule = scheduleRepository.findById(passenger.getScheduleId()).orElseThrow();
        Bus bus = busRepository.findById(schedule.getBusId()).orElseThrow();

        if (passenger.getNumberOfSeats() + passengerRepository.takenSeatsById(schedule.getId()) > bus.getPassengersLimit()) {
            throw new AllSeatsTakenException();
        } else {
            System.out.println(passengerRepository.takenSeatsById(schedule.getBusId()));
            return passengerRepository.save(passenger);
        }
    }

    public List<Passenger> getPassengersForSpecificSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Schedule with id " + id + " does not exist"));
        return passengerRepository.findByScheduleId(id);
    }

    public Long mapPassengerIdToUserId(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Passenger with id " + id + " does not exist"
                ));
        Long mappedId = passenger.getUserId();
        return mappedId;
    }
}
