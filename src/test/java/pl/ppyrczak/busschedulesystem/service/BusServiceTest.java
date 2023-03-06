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
import static org.springframework.data.domain.Sort.by;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new BusService(busRepository);
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
    void shouldGetBuses() {
        Bus bus = new Bus(1L,
                "Merdeces",
                "Vivaro",
                10,
                "toilet",
                null);
        List<Bus> buses = new ArrayList<>();
        buses.add(bus);

        Pageable pageable = PageRequest.of(0, 3, by("brand"));

        when(busRepository.findAllBuses(Pageable.ofSize(3))).thenReturn(new ArrayList<>());
        underTest.getBuses(0, Sort.Direction.ASC
        );
        verify(busRepository).findAllBuses(pageable);
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
//
////        assertThrows(ResourceNotFoundException.class, ()-> {
////            underTest.deleteBus(bus.getId());});
//
//        given(busRepository.findById(bus.getId())).willReturn(Optional.of(bus));
//        willThrow(new ResourceNotFoundException("Bus with id " + bus.getId() + " does not exist"))
//                .given(busRepository)
//                .deleteById(bus.getId());
//
//        try {
//            underTest.deleteBus(bus.getId());
//        } catch (ResourceNotFoundException e) {}
//
//        then(busRepository)
//                .should(never());
//                //.deleteById(bus.getId());
//
//    }
}
