package pl.ppyrczak.busschedulesystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public List<Bus> getBuses() {
        return busRepository.findAllBuses(PageRequest.of(0, 2));
    }

    public Bus addBus(Bus bus) {
        return busRepository.save(bus);
    }

    public Bus getBus(Long id) {
        return busRepository.findById(id).orElseThrow();
    }

   public Bus editBus(Bus busToUpdate, Long id) {
       return busRepository.findById(id)
               .map(bus -> {
                   bus.setPassengersLimit(busToUpdate.getPassengersLimit());
                   bus.setEquipment(busToUpdate.getEquipment());
                   return busRepository.save(bus);
               })
               .orElseGet(() -> {
                   return busRepository.save(busToUpdate);
               });
   }

    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }
}
