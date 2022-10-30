package pl.ppyrczak.busschedulesystem.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.BDDMockito.*;

import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
class BusServiceTest {

    private BusService busService;
    @Mock
    private BusRepository busRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        busService = new BusService(busRepository, scheduleRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldAddBus() {
        //given
        Bus bus = new Bus(1L, "Merdeces", "Vivaro", 10, "toilet", null);
        given(busRepository.save(bus)).willReturn(bus);
        //when
        busService.addBus(bus);
        //then
        verify(busRepository).save(eq(bus));
    }

    @Test
    void shouldGetBus() {
        Bus bus = new Bus(1L,
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);

        //given
        when(busRepository.findById(1L)).thenReturn(Optional.of(bus));
        //when
        Bus busToFind = busService.getBus(1L);
        //then
        Assertions.assertThat(busToFind).isNotNull();
    }

    @Test
    void shouldDeleteBus() {
        Bus bus = new Bus(1L,
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);
        //given
        List<Bus> busList = new ArrayList<>();
        busList.add(bus);
        doNothing().when(busRepository).deleteById(bus.getId());
        //when
        busService.deleteBus(bus.getId());
        //then
        verify(busRepository).deleteById(bus.getId());
    }
}