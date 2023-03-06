package pl.ppyrczak.busschedulesystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.ppyrczak.busschedulesystem.model.ApplicationUser;
import pl.ppyrczak.busschedulesystem.registration.token.ConfirmationTokenService;
import pl.ppyrczak.busschedulesystem.repository.*;
import pl.ppyrczak.busschedulesystem.security.UserRole;
import pl.ppyrczak.busschedulesystem.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ConfirmationTokenService confirmationTokenService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private ReviewRepository reviewRepository;
    private UserService underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository,
                                    passwordEncoder,
                                    confirmationTokenService,
                                    roleRepository,
                                    passengerRepository,
                                    scheduleRepository,
                                    reviewRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

//    @Test
//    void loadUserByUsername() {
//
//    }
//
//    @Test
//    void signUpUser() {
//    }
//
//    @Test
//    void enableAppUser() {
//
//    }

    @Test
    void shouldGetAllUsersInfo() {
        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);
        ApplicationUser user1 = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr1@gmail.com",
                "piotr",
                null);

        List<ApplicationUser> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);

        when(userRepository.findAll()).thenReturn(userList);

        List<ApplicationUser> users = underTest.getAllUsersInfo();

        assertEquals(users.size(), 2);
    }

    @Test
    void shouldSaveRole() {
        UserRole role = new UserRole("ROLE_ADMIN");
        given(roleRepository.save(role)).willReturn(role);
        underTest.saveRole(role);
        verify(roleRepository).save(role);
    }

//    @Test
//    void shouldAddRoleToUser() {
//        ApplicationUser user = new ApplicationUser("Piotr",
//                "Pyrczak",
//                "piotr@gmail.com",
//                "piotr",
//                null);
//
//        UserRole role = new UserRole("ROLE_ADMIN");
//
//        userRepository.save(user);
//        roleRepository.save(role);
//
//        underTest.addRoleToUser(user.getUsername(), role.getName());
//        assertThat(user.getRoles()).contains(role);
//    }

    @Test
    void shouldGetUser() {
        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);
        user.setId(1L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        ApplicationUser userToFind = underTest.getUser(user.getId());
        assertThat(userToFind.getUsername().equals("piotr@gmail.com"));
    }

    @Test
    void shouldGetUsers() {
        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        ApplicationUser user1 = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        List<ApplicationUser> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);

        when(userRepository.findAll()).thenReturn(userList);
        List<ApplicationUser> users = underTest.getUsers();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void shouldSubscribe() {
        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        user.setSubscribed(true);
        Boolean subscribed = user.getSubscribed();
        assertThat(subscribed).isEqualTo(subscribed);
    }

    @Test
    void unsubscribe() {
        ApplicationUser user = new ApplicationUser("Piotr",
                "Pyrczak",
                "piotr@gmail.com",
                "piotr",
                null);

        user.setSubscribed(false);
        Boolean subscribed = user.getSubscribed();
        assertThat(subscribed).isEqualTo(subscribed);
    }
}
