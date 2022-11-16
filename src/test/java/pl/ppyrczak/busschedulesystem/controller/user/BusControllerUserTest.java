package pl.ppyrczak.busschedulesystem.controller.admin;

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
import org.springframework.test.web.servlet.MvcResult;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@WithMockUser(roles = {"USER"})
class BusControllerUserTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BusRepository busRepository;

    @AfterEach
    public void tearDown() {
        busRepository.deleteAll();
    }

    @Test
    void shouldGetBus() throws Exception {

        Bus newBus = new Bus();
        newBus.setBrand("Mercedes");
        newBus.setModel("V200");
        newBus.setEquipment("kitchen");
        newBus.setPassengersLimit(20);
        busRepository.save(newBus);

        mockMvc.perform(get("/buses/" + newBus.getId()))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void shouldAddBus() throws Exception {
        Bus newBus = new Bus();
        newBus.setBrand("Opel");
        newBus.setModel("Vivaro");
        newBus.setEquipment("toilet");
        newBus.setPassengersLimit(200);

        mockMvc.perform(post("/bus")
                        .content(objectMapper.writeValueAsString(newBus))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldDeleteBus() throws Exception {
        Bus newBus = new Bus();
        newBus.setBrand("Mercedes");
        newBus.setModel("V200");
        newBus.setEquipment("kitchen");
        newBus.setPassengersLimit(20);
        busRepository.save(newBus);

        mockMvc.perform(delete("/bus/" + newBus.getId()))
                .andExpect(status().isForbidden())
                .andDo(print());
        assertEquals(busRepository.findAll().size(), 1);
    }
}