package pl.ppyrczak.busschedulesystem.auth;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ppyrczak.busschedulesystem.registration.token.ConfirmationToken;
import pl.ppyrczak.busschedulesystem.registration.token.ConfirmationTokenService;
import pl.ppyrczak.busschedulesystem.repository.RoleRepository;
import pl.ppyrczak.busschedulesystem.repository.UserRepository;
import pl.ppyrczak.busschedulesystem.security.UserRole;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.ResponseEntity.*;

@Service
@AllArgsConstructor
@Transactional//
@Slf4j
public class UserService implements UserDetailsService, UserInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private final RoleRepository roleRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
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
    } //zmiana na maila

    public ApplicationUser addUser(ApplicationUser user) {
        return userRepository.save(user);
    }

    public String signUpUser(ApplicationUser user) {
        boolean userExists = userRepository.findByUsername(user.getUsername())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);

        Optional<ApplicationUser> role = userRepository.findByUsername("ROLE_ADMIN");
        addRoleToUser(user.getUsername(), role.toString());

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
        permitUserToLogin(username);
        return userRepository.enableAppUser(username);
    }

    public List<ApplicationUser> getAllUsers() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> blockLoginIfUserIsNotConfirmed(ApplicationUser user) {
        if (!user.getEnabled()) {
            return notFound().build();
        } else {
            return ok().build();
        }

    }

    @Override
    public ApplicationUser saveUser(ApplicationUser user) {
        log.info("saving {} user", user.getFirstName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserRole saveRole(UserRole role) {
        log.info("saving {} role", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("adding role to user");
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserRole role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public void permitUserToLogin(String username) {
        log.info("adding role to user");
        ApplicationUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserRole roleToRemove = roleRepository.findById(3L).orElseThrow();

        user.getRoles().remove(roleToRemove); //TODO MOZE TRZEBA PODAC NAZWE ROLI A NIE INDEKS
        UserRole roleToAdd = roleRepository.findById(1L).orElseThrow(); //TODO poprawic zeby nie podawac tu konkretnego id tylko nazwe roli
        System.out.println(roleToAdd.getName());
        addRoleToUser(user.getUsername(), roleToAdd.getName());
    }

    @Override
    public ApplicationUser getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<ApplicationUser> getUsers() {
        return userRepository.findAll();
    }
}
