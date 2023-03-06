package pl.ppyrczak.busschedulesystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import pl.ppyrczak.busschedulesystem.model.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.UserRole;
import pl.ppyrczak.busschedulesystem.repository.RoleRepository;
import pl.ppyrczak.busschedulesystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

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
