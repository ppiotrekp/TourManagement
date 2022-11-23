package pl.ppyrczak.busschedulesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.RoleRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;
import pl.ppyrczak.busschedulesystem.security.UserRole;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BusScheduleSystemApplication  {
    public static void main(String[] args) {
        SpringApplication.run(BusScheduleSystemApplication.class, args);
    }
}
