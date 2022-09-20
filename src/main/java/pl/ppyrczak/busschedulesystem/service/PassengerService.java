package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final ScheduleRepository scheduleRepository;
    private final BusRepository busRepository;
    public List<Passenger> getPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getPassenger(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow();
    }

    public Passenger addPassenger(Passenger passenger) {
        Schedule schedule = scheduleRepository.findById(passenger.getScheduleId()).orElseThrow();
        Bus bus = busRepository.findById(schedule.getBusId()).orElseThrow();
        if (bus.getPassengersLimit() == passengerRepository.takenSeatsById(bus.getId())) {
            throw new RuntimeException("All seats taken");
            //TODO wywalic wyjatek
        }

        else if (passenger.getNumberOfSeats() + passengerRepository.takenSeatsById(bus.getId()) > bus.getPassengersLimit()) {
            throw new RuntimeException("number of seats is not enough");
        }

        else {
            return passengerRepository.save(passenger);
        }
    }
}
