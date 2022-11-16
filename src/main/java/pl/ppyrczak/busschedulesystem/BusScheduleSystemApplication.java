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

//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private BusRepository busRepository;
//    @Autowired
//    private ScheduleRepository scheduleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BusScheduleSystemApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        List<UserRole> roles = Arrays.asList(
//                new UserRole("ROLE_USER"),
//                new UserRole("ROLE_ADMIN")
//        );
//
//        roles.stream().forEach(role -> roleRepository.save(role));
//
//        List<Bus> buses = Arrays.asList(
//                new Bus("Mercedes", "Tourismo", 10, "toilet", null),
//                new Bus("Mercedes", "Tourismo1", 10, "toilet", null),
//                new Bus("Mercedes", "Tourismo2", 10, "toilet", null),
//                new Bus("Mercedes", "Tourismo3", 10, "toilet", null),
//                new Bus("Mercedes", "Tourismo4", 10, "toilet", null)
//        );
//
//        buses.stream().forEach(bus -> busRepository.save(bus));
//
//        List<Schedule> schedules = Arrays.asList(
//                new Schedule(
//                        1L,
//                        "Krakow",
//                        "Malaga",
//                        LocalDateTime.of(2022, 10, 10, 10, 10),
//                        LocalDateTime.of(2022, 10, 10, 12, 10),
//                        "100",
//                        null,
//                        null),
//
//                new Schedule(
//                        2L,
//                        "Mediolan",
//                        "Madryt",
//                        LocalDateTime.of(2022, 10, 10, 10, 10),
//                        LocalDateTime.of(2022, 10, 10, 12, 10),
//                        "100",
//                        null,
//                        null)
//        );
//
//        schedules.stream().forEach(schedule -> scheduleRepository.save(schedule));
//
//    }



}
