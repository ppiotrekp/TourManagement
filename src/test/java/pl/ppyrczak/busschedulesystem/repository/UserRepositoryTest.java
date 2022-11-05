package pl.ppyrczak.busschedulesystem.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;

import java.util.Optional;

@DataJpaTest
class UserRepositoryTest {


    @Mock
    private UserRepository userRepository;

    @Test
    void shouldfindByUsername() {
        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        user.setId(1L);
        userRepository.save(user);

        String username = "piotr@gmail.com";
        Optional<ApplicationUser> users = userRepository.findByUsername(username);

    }

//    @Test
//    void enableAppUser() {
//        ApplicationUser user = new ApplicationUser("Piotr",
//                "Pyrczak",
//                "piotr@gmail.com",
//                "piotr",
//                null);
//
//        user.setId(1L);
//        userRepository.save(user);
//
//        userRepository.enableAppUser(user.getUsername());
//        System.out.println(user.getUsername());
//        Assertions.assertEquals(user.getEnabled(), true); //TODO NIE DZIALA
//    }
}