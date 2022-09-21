package pl.ppyrczak.busschedulesystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.ppyrczak.busschedulesystem.model.Bus;

class BusServiceTest {

    @Autowired
    private final BusService busService;

    BusServiceTest(BusService busService) {
        this.busService = busService;
    }

    @Test
    void addBus() {
        Bus bus = new Bus();
        bus.setBrand("Opel");
        bus.setModel("Vivaro");
        bus.setPassengersLimit(10);
        bus.setEquipment("tv");
    }
}