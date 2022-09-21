package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.ppyrczak.busschedulesystem.model.Passenger;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query(value = "select count(p.*) from passenger p  \n" +
            "            join schedule s on p.schedule_id = s.id  \n" +
            "            join bus b on s.bus_id = b.id\n" +
            "            where b.id = :id",
            nativeQuery = true)
    int takenSeatsById(Long id);

    List<Passenger> findAllByScheduleIdIn(List<Long> ids);
}
