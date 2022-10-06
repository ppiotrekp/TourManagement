package pl.ppyrczak.busschedulesystem.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import pl.ppyrczak.busschedulesystem.model.Bus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class BusServiceTest {

    @Autowired
    private BusService busService;

    @Test
    void shouldGetBus() {
        Bus bus = busService.getBus(1l);
        assertThat(bus).isNotNull();
        assertThat(bus.getId()).isEqualTo(1L);
    }
}