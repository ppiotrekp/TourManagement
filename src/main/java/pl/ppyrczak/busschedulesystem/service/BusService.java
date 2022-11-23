package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.exception.runtime.ResourceNotFoundException;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusService {

    private static final int PAGE_SIZE = 3;
    private final BusRepository busRepository;
    private final ScheduleRepository scheduleRepository;

    public List<Bus> getBuses(int page, Sort.Direction sort) {
        return busRepository.findAllBuses(
                PageRequest.of(page, PAGE_SIZE,
                        Sort.by(sort, "brand")));
    }

    public Bus addBus(Bus bus) {
        return busRepository.save(bus);
    }

    public Bus getBus(Long id) {
        return busRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        "Bus with id " + id + " does not exist"));
    }

    public void deleteBus(Long id) {
        if (!busRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Bus with id " + id + " does not exist");
        }
        busRepository.deleteById(id);
    }
}
