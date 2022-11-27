package pl.ppyrczak.busschedulesystem.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ppyrczak.busschedulesystem.controller.dto.UserHistoryDto;
import pl.ppyrczak.busschedulesystem.exception.runtime.EmailTakenException;
import pl.ppyrczak.busschedulesystem.exception.runtime.ResourceNotFoundException;
import pl.ppyrczak.busschedulesystem.registration.token.ConfirmationToken;
import pl.ppyrczak.busschedulesystem.registration.token.ConfirmationTokenService;
import pl.ppyrczak.busschedulesystem.repository.*;
import pl.ppyrczak.busschedulesystem.security.UserRole;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private static final int PAGE_SIZE =5;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private final RoleRepository roleRepository;
    private final PassengerRepository passengerRepository;
    private final ScheduleRepository scheduleRepository;
    private final ReviewRepository reviewRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user == null) {
            log.error("User not found");
        } else {
            log.info("User found");
        }

        Collection <SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(),
                        user.getPassword(),
                        authorities);
    }


    public String signUpUser(ApplicationUser user) {
        boolean userExists = userRepository.findByUsername(user.getUsername())
                .isPresent();

        if (userExists) {
            throw new EmailTakenException();
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        return token;
    }

    public int enableAppUser(String username) {
        return userRepository.enableAppUser(username);
    }

    public List<ApplicationUser> getAllUsersInfo() {
        List<ApplicationUser> users = userRepository.findAll();
        List<Long> ids = users.stream()
                .map(ApplicationUser::getId)
                .collect(Collectors.toList());

        return users;
    }

    public UserRole saveRole(UserRole role) {
        log.info("saving {} role", role.getName());
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("adding role to user");
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserRole role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public ApplicationUser getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<ApplicationUser> getUsers() {
        return userRepository.findAll();
    }

    public List<ApplicationUser> getUsers(int page, Sort.Direction sort) {
        return userRepository.findAllUsers(
                PageRequest.of(page, PAGE_SIZE,
                        Sort.by(sort, "id")));

    }

    public ApplicationUser getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    public void subscribe(ApplicationUser user) {
        user.setSubscribed(true);
    }

    public void unsubscribe(ApplicationUser user) {
        user.setSubscribed(false);
    }

    public List<UserHistoryDto> getUserHistory(Long id) {
        return userRepository.getUserHistory(id);
    }
}
