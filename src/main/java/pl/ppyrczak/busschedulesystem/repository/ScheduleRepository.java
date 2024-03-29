package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByBusId(Long id);
    @Query(value = "select s.* from schedule s where date (s.departure) >= now() ", nativeQuery = true)
    List<Schedule> findAllSchedules(Pageable pageable);
}
