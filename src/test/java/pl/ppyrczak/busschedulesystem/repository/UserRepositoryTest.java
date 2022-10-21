package pl.ppyrczak.busschedulesystem.repository;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.User;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    @Test
    void IfItProperlyfindsByUsername() {
        ApplicationUser user = new ApplicationUser(
                "Piotr",
                "Pyrczak",
                "ppyrczak5@gmail.com",
                "password",
                null
        );

        user.setId(1L);
        user.setEnabled(true);
        user.setLocked(false);
        user.setPhoneNumber("111222333");

        userRepository.save(user);

        String username = "ppyrczak5@gmail.com";
        ApplicationUser user1 = userRepository.findByUsername(username).orElseThrow();

        Assert.assertEquals(user1.getFirstName(), "Piotr");


    }

    @Test
    void enableAppUser() {
    }
}