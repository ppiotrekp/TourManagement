package pl.ppyrczak.busschedulesystem.auth;

import pl.ppyrczak.busschedulesystem.security.UserRole;

import java.util.List;

public interface UserInterface {
    ApplicationUser saveUser(ApplicationUser user);

    UserRole saveRole(UserRole role);

    void addRoleToUser(String username, String roleName);

    ApplicationUser getUser(String username);

    List<ApplicationUser> getUsers();
}
