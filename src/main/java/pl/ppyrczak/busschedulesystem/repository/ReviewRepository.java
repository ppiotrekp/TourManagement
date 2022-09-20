package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ppyrczak.busschedulesystem.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByPassengerId(Long id);
}
