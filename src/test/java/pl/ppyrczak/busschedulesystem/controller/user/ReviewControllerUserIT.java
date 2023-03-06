package pl.ppyrczak.busschedulesystem.controller.user;

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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@WithMockUser(roles = {"USER"})
class ReviewControllerUserIT {
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
        passengerRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "ppyrczak@gmail.com")
    void shouldAddReview() throws Exception {
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
        schedule.setTicketPrice(100);
        scheduleRepository.save(schedule);

        ApplicationUser user = new ApplicationUser();
        user.setFirstName("Piotr");
        user.setLastName("Pyrczak");
        user.setPhoneNumber("123123123");
        user.setUsername("ppyrczak@gmail.com");
        user.setPassword("piotr");
        userRepository.save(user);

        Passenger passenger = new Passenger();
        passenger.setScheduleId(schedule.getId());
        passenger.setNumberOfSeats(1);
        passenger.setUserId(user.getId());
        passengerRepository.save(passenger);

        Review review = new Review();
        review.setPassengerId(passenger.getId());
        review.setDescription("OK");
        review.setScheduleId(schedule.getId());
        review.setRating(4);
        review.setCreated(now());

        mockMvc.perform(post("/review")
                        .content(objectMapper.writeValueAsString(review))
                        .contentType(APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "ppyrczak11@gmail.com")
    void shouldNotAddReview() throws Exception {
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
        schedule.setTicketPrice(100);
        scheduleRepository.save(schedule);

        ApplicationUser user = new ApplicationUser();
        user.setFirstName("Piotr");
        user.setLastName("Pyrczak");
        user.setPhoneNumber("123123123");
        user.setUsername("ppyrczak@gmail.com");
        user.setPassword("piotr");
        userRepository.save(user);

        Passenger passenger = new Passenger();
        passenger.setScheduleId(schedule.getId());
        passenger.setNumberOfSeats(1);
        passenger.setUserId(user.getId());
        passengerRepository.save(passenger);

        Review review = new Review();
        review.setPassengerId(passenger.getId());
        review.setDescription("OK");
        review.setScheduleId(schedule.getId());
        review.setRating(4);
        review.setCreated(now());

        mockMvc.perform(post("/review")
                        .content(objectMapper.writeValueAsString(review))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
