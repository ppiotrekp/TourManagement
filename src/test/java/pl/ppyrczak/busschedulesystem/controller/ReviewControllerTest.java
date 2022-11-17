package pl.ppyrczak.busschedulesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Review;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        scheduleRepository.deleteAll();
    }

    private Bus createBus() {
        Bus bus = new Bus();
        bus.setBrand("Mercedes");
        bus.setModel("V200");
        bus.setEquipment("kitchen");
        bus.setPassengersLimit(20);
        busRepository.save(bus);
        return bus;
    }

    private Schedule createSchedule() {
        Schedule schedule = new Schedule();
        schedule.setBusId(createBus().getId());
        schedule.setDepartureFrom("Krakow");
        schedule.setDepartureTo("Malaga");
        schedule.setDeparture(LocalDateTime.of(2022, 10, 10, 10, 10));
        schedule.setArrival(LocalDateTime.of(2022, 10, 10, 12, 10));
        schedule.setTicketPrice("100");
        scheduleRepository.save(schedule);
        return schedule;
    }

    private ApplicationUser createUser() {
        ApplicationUser user = new ApplicationUser();
        user.setFirstName("Piotr");
        user.setLastName("Pyrczak");
        user.setPhoneNumber("123123123");
        user.setUsername("ppyrczak@gmail.com");
        user.setPassword("piotr");
        userRepository.save(user);
        return user;
    }

    private Passenger createPassenger() {
        Passenger passenger = new Passenger();
        passenger.setScheduleId(createSchedule().getId());
        passenger.setNumberOfSeats(1);
        passenger.setUserId(createUser().getId());
        passengerRepository.save(passenger);
        return passenger;
    }

    private Review createReview() {
        Review review = new Review();
        review.setScheduleId(createSchedule().getId());
        review.setPassengerId(createPassenger().getId());
        review.setDescription("OK");
        review.setRating(4);
        review.setCreated(now());
        reviewRepository.save(review);
        return review;
    }

    @Test
    void shouldGetReviewsWithDetailsForSpecificSchedule() throws Exception {
        Bus bus = new Bus();
        bus.setBrand("Mercedes");
        bus.setModel("V200");
        bus.setEquipment("kitchen");
        bus.setPassengersLimit(20);
        busRepository.save(bus);

        Schedule schedule = new Schedule();
        schedule.setBusId(bus.getId());
        schedule.setDepartureFrom("Krakow");
        schedule.setDepartureTo("Malaga");
        schedule.setDeparture(LocalDateTime.of(2022, 10, 10, 10, 10));
        schedule.setArrival(LocalDateTime.of(2022, 10, 10, 12, 10));
        schedule.setTicketPrice("100");
        scheduleRepository.save(schedule);

        ApplicationUser user = new ApplicationUser();
        user.setFirstName("Piotr");
        user.setLastName("Pyrczak");
        user.setPhoneNumber("123123123");
        user.setUsername("ppyrczak@gmail.com");
        user.setPassword("piotr");
        userRepository.save(user);

        ApplicationUser user1 = new ApplicationUser();
        user1.setFirstName("Piotr");
        user1.setLastName("Pyrczak");
        user1.setPhoneNumber("123123123");
        user1.setUsername("ppyrczak1@gmail.com");
        user1.setPassword("piotr");
        userRepository.save(user1);

        Passenger passenger = new Passenger();
        passenger.setScheduleId(schedule.getId());
        passenger.setNumberOfSeats(1);
        passenger.setUserId(user.getId());
        passengerRepository.save(passenger);

        Passenger passenger1 = new Passenger();
        passenger1.setScheduleId(schedule.getId());
        passenger1.setNumberOfSeats(1);
        passenger1.setUserId(user1.getId());
        passengerRepository.save(passenger1);

        Review review = new Review();
        review.setPassengerId(passenger.getId());
        review.setDescription("OK");
        review.setScheduleId(schedule.getId());
        review.setRating(4);
        review.setCreated(now());

        Review review1 = new Review();
        review1.setPassengerId(passenger1.getId());
        review1.setDescription("OK");
        review1.setScheduleId(schedule.getId());
        review1.setRating(4);
        review1.setCreated(now());

        mockMvc.perform(get("/schedules/" + schedule.getId() + "/reviews"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}