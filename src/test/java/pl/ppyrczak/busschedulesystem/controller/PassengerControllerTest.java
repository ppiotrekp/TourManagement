package pl.ppyrczak.busschedulesystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.ppyrczak.busschedulesystem.auth.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;
import pl.ppyrczak.busschedulesystem.repository.UserRepository;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
class PassengerControllerTest {

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
    private ObjectMapper objectMapper;

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
        schedule.setDeparture(LocalDateTime.of(2023, 10, 10, 10, 10));
        schedule.setArrival(LocalDateTime.of(2023, 10, 10, 12, 10));
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

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldGetPassengers() throws Exception {
        Passenger passenger = createPassenger();
        Passenger passenger1 = createPassenger();

        mockMvc.perform(get("/passengers"))
                .andDo(print())
                .andExpect(status().isOk());
        Assertions.assertThat(passengerRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldGetPassenger() throws Exception {
        Passenger passenger = createPassenger();
        mockMvc.perform(get("/passengers/" + passenger.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

//    @Test
//    void shouldAddPassenger() throws Exception {
//        Passenger passenger = createPassenger();
//        Authentication authentication;
//        mockMvc.perform(post("/passenger")
//                .content(objectMapper.writeValueAsString(passenger))
//                .contentType(APPLICATION_JSON)
//                .accept(APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isCreated()); //todo ustawic uprawnienia
//    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldGetRidesWithPassengers() throws Exception {
        Passenger passenger = createPassenger();
        mockMvc.perform(get("/schedules/" + passenger.getScheduleId() +"/passengers"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}