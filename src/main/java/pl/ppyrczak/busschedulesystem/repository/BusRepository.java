package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.ppyrczak.busschedulesystem.model.Bus;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    @Query("select b from Bus b " +
            "left join fetch b.scheduleList")
    List<Bus> findAllBuses(Pageable pageablepaging);
}
