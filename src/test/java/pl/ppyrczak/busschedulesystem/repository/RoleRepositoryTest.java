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
        UserRole role1 = new UserRole("ROLE_USER");

        role.setId(1L);
        role1.setId(2L);

        roleRepository.save(role);
        roleRepository.save(role1);

        UserRole role2 = roleRepository.findById(1L).orElseThrow();

        UserRole expectedRole = roleRepository.findByName(role2.getName());
        Assert.assertNotNull(expectedRole);
    }
}