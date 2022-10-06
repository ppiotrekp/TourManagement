package pl.ppyrczak.busschedulesystem.repository;

import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.ppyrczak.busschedulesystem.model.Passenger;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    @Query(value = "select coalesce(sum(p.number_of_seats), 0) from passenger p \n" +
            "join schedule s on p.schedule_id = s.id  \n" +
            "join bus b on s.bus_id = b.id\n" +
            "where s.id =:id",
            nativeQuery = true)
    Integer takenSeatsById(Long id);

    List<Passenger> findAllByScheduleIdIn(List<Long> ids);

    List<Passenger> findByScheduleId(Long id);



}
