package pl.ppyrczak.busschedulesystem.dto.mapper;

import pl.ppyrczak.busschedulesystem.dto.ScheduleDto;
import pl.ppyrczak.busschedulesystem.model.Schedule;

import java.util.List;
import java.util.stream.Collectors;

public class ScheduleDtoMapper {
    private ScheduleDtoMapper() {}

    public static ScheduleDto mapToScheduleDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .busId(schedule.getBusId())
                .departureFrom(schedule.getDepartureFrom())
                .arrivalTo(schedule.getArrivalTo())
                .departure(schedule.getDeparture())
                .arrival(schedule.getArrival())
                .ticketPrice(schedule.getTicketPrice())
                .build();
    }

    public static List<ScheduleDto> mapToScheduleDtos(List<Schedule> schedules) {
        return schedules.stream()
                .map(schedule -> mapToScheduleDto(schedule))
                .collect(Collectors.toList());
    }
}
