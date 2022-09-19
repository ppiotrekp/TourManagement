package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ppyrczak.busschedulesystem.model.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
