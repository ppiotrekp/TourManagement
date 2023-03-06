package pl.ppyrczak.busschedulesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ppyrczak.busschedulesystem.model.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByName(String name);
}
