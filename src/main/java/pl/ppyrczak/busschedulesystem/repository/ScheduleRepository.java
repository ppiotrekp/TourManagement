package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByBusIdIn(List<Long> ids);
    List<Schedule> findAllByBusId(Long id);
    boolean existsByBusId(Long id );

    boolean existsByPassengers(Long id);

}
