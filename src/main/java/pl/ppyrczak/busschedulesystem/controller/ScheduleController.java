package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.dto.ScheduleDto;
import pl.ppyrczak.busschedulesystem.dto.mapper.ScheduleDtoMapper;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.service.ScheduleService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/schedules")
    public List<ScheduleDto> getSchedules(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        return ScheduleDtoMapper.mapToScheduleDtos(
                scheduleService.getSchedules(pageNumber, sortDirection));
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
        return scheduleService.getScheduleWithPassengersAndReviews(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(value = CREATED)
    @PostMapping("/schedules")
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
