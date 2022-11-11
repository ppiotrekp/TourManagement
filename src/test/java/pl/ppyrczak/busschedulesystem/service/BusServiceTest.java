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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.ppyrczak.busschedulesystem.exception.runtime.ResourceNotFoundException;
import pl.ppyrczak.busschedulesystem.model.Bus;
import pl.ppyrczak.busschedulesystem.repository.BusRepository;
import pl.ppyrczak.busschedulesystem.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class BusServiceTest {

    private BusService underTest;
    @Mock
    private BusRepository busRepository;
    @Mock
    private ScheduleRepository scheduleRepository;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new BusService(busRepository, scheduleRepository);
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
        underTest.addBus(bus);
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
        when(busRepository.findById(bus.getId())).thenReturn(Optional.of(bus));
        //when
        Bus busToFind = underTest.getBus(1L);
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
        when(busRepository.findById(bus.getId())).thenReturn(Optional.of(bus));
        underTest.deleteBus(bus.getId());
        verify(busRepository).deleteById(bus.getId());
    }

//    @Test
//    void shouldNotDeleteBus() {
//        Bus bus = new Bus(1L,
//                "Merdeces",
//                "Vivaro",
//                10,
//                "toilet",
//                null);
//        given(busRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
//        underTest.deleteBus(bus.getId());
//        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()-> {});
//    }
}