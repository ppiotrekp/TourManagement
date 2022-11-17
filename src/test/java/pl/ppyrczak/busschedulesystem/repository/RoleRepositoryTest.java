package pl.ppyrczak.busschedulesystem.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ppyrczak.busschedulesystem.security.UserRole;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

    @Test
    void shouldFindByName() {
        UserRole role = new UserRole("ROLE_ADMIN");
        roleRepository.save(role);
        UserRole expectedRole = roleRepository.findByName(role.getName());
        assertNotNull(expectedRole);
    }
}