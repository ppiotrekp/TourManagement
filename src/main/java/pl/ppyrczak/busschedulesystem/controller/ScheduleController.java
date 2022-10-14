package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.controller.dto.ScheduleDto;
import pl.ppyrczak.busschedulesystem.controller.dto.ScheduleDtoMapper;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.service.ScheduleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedules")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<ScheduleDto> getSchedules() {
        return ScheduleDtoMapper.mapToScheduleDtos(scheduleService.getSchedules());
    }

    @GetMapping("/schedules/passengers")
    public List<Schedule> getSchedulesWithPassengersAndReviews() {
        return scheduleService.getSchedulesWithPassengersAndReviews();
    }

    @GetMapping("/schedules/{id}/passengers")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Schedule> getSchedulesWithPassengers() {
        throw new RuntimeException("not implemented");
    } //todo sprawdzic czy nie ma n+1

    @GetMapping("/admin/schedules/{id}")
    public Schedule getScheduleForAdmin(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @GetMapping("/schedules/{id}")
    public ScheduleDto getSchedule(@PathVariable Long id) {
        return ScheduleDtoMapper.mapToScheduleDto(scheduleService.getSchedule(id));
    }

    @PostMapping("/schedule")
    public Schedule addSchedule(@Valid @RequestBody Schedule schedule) {
        return scheduleService.addSchedule(schedule);
    }

    @PutMapping("/schedule/{id}")
    public Schedule editSchedule(@RequestBody Schedule scheduleToUpdate, @PathVariable Long id) {
        return scheduleService.editSchedule(scheduleToUpdate, id); //POZBYC SIE N+1
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
