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

import pl.ppyrczak.busschedulesystem.model.ApplicationUser;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.model.Passenger;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.PassengerRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;
import pl.ppyrczak.busschedulesystem.repository.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@WithMockUser(roles = {"USER"})
class ScheduleControllerUserIT {

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
        schedule.setArrivalTo("Malaga");
        schedule.setDeparture(LocalDateTime.of(2023, 10, 10, 10, 10));
        schedule.setArrival(LocalDateTime.of(2023, 10, 10, 12, 10));
        schedule.setTicketPrice(100);
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
    void shouldNotAddSchedule() throws Exception {
        Schedule schedule = createSchedule();

        mockMvc.perform(post("/schedules")
                        .content(objectMapper.writeValueAsString(schedule))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldNotEditSchedule() throws Exception {
        Schedule schedule = createSchedule();

        mockMvc.perform(put("/schedules/" + schedule.getId())
                        .content(objectMapper.writeValueAsString(schedule))
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldNotDeleteSchedule() throws Exception {
        Schedule schedule = createSchedule();

        mockMvc.perform(delete("/schedules/" + schedule.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());

        assertEquals(scheduleRepository.findAll().size(), 1);
    }
}
