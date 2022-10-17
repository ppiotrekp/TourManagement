package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;

import java.util.Optional;


@Repository
@Transactional(readOnly = true)

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String e);

    @Modifying
    @Query("UPDATE ApplicationUser a " +
            "SET a.enabled = TRUE WHERE a.username = ?1")
    int enableAppUser(String username);
}