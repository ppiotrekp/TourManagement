package pl.ppyrczak.busschedulesystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BusControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BusRepository busRepository;

    @Test
    @Transactional
    void shouldGetBus() throws Exception {

        Bus newBus = new Bus();
        newBus.setBrand("Mercedes");
        newBus.setModel("V200");
        newBus.setEquipment("kitchen");
        newBus.setPassengersLimit(20);
        busRepository.save(newBus);

        MvcResult mvcResult = mockMvc.perform(get("/bus/" + newBus.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200))
                .andReturn();

        Bus bus = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Bus.class);
        assertThat(bus).isNotNull();
        assertThat(bus.getId()).isEqualTo(newBus.getId());
        assertThat(bus.getModel()).isEqualTo("V200");
    }
}