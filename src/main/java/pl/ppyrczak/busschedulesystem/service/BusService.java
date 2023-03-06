package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.exception.runtime.model.BusNotFoundException;
import pl.ppyrczak.busschedulesystem.exception.runtime.model.ResourceNotFoundException;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusService {

    private static final int PAGE_SIZE = 3;
    private final BusRepository busRepository;

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
                orElseThrow(() -> new BusNotFoundException(id));
    }

    public void deleteBus(Long id) {
        if (!busRepository.findById(id).isPresent()) {
            throw new BusNotFoundException(id);
        }
        busRepository.deleteById(id);
    }
}
