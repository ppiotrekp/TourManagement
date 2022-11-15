package pl.ppyrczak.busschedulesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;

import javax.transaction.Transactional;

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
class BusControllerTest {

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
    @WithMockUser(roles = {"ADMIN"})
    void shouldGetBus() throws Exception {

        Bus newBus = new Bus();
        newBus.setBrand("Mercedes");
        newBus.setModel("V200");
        newBus.setEquipment("kitchen");
        newBus.setPassengersLimit(20);
        busRepository.save(newBus);

        MvcResult mvcResult = mockMvc.perform(get("/buses/" + newBus.getId()))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        Bus bus = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Bus.class);
        assertThat(bus).isNotNull();
        assertThat(bus.getId()).isEqualTo(newBus.getId());
        assertThat(bus.getModel()).isEqualTo("V200");
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldDeleteBus() throws Exception {
        Bus newBus = new Bus();
        newBus.setBrand("Mercedes");
        newBus.setModel("V200");
        newBus.setEquipment("kitchen");
        newBus.setPassengersLimit(20);
        busRepository.save(newBus);

        mockMvc.perform(delete("/bus/" + newBus.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertEquals(busRepository.findAll().size(), 0);
    }
}