package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.controller.dto.BusDto;
import pl.ppyrczak.busschedulesystem.controller.dto.BusDtoMapper;
import pl.ppyrczak.busschedulesystem.controller.dto.ScheduleDto;
import pl.ppyrczak.busschedulesystem.controller.dto.ScheduleDtoMapper;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.service.ScheduleService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedules")
    public List<ScheduleDto> getSchedulesForUsers() {
        return ScheduleDtoMapper.mapToScheduleDtos(scheduleService.getSchedules());
    }

    @PostMapping("/schedulesFilter")
    public List<ScheduleDto> getSchedules(@RequestBody Schedule schedule) {
        return ScheduleDtoMapper.mapToScheduleDtos(scheduleService.getSchedules(schedule));
    }

    @GetMapping("/schedules/{id}")
    public ScheduleDto getScheduleForUsers(@PathVariable Long id) {
        return ScheduleDtoMapper.mapToScheduleDto(scheduleService.getSchedule(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/schedules/{id}")
    public Schedule getScheduleForAdmin(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/schedules/passengers")
    public List<Schedule> getSchedulesWithPassengersAndReviews() {
        return scheduleService.getSchedulesWithPassengersAndReviews();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(value = CREATED)
    @PostMapping("/schedule")
    public Schedule addSchedule(@Valid @RequestBody Schedule schedule) {
        return scheduleService.addSchedule(schedule);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/schedules/{id}")
    public Schedule editSchedule(@Valid @RequestBody Schedule scheduleToUpdate, @PathVariable Long id) {
        return scheduleService.editSchedule(scheduleToUpdate, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }


}
