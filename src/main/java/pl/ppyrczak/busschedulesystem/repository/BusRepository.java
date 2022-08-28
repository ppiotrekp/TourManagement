package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ppyrczak.busschedulesystem.model.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {
}
