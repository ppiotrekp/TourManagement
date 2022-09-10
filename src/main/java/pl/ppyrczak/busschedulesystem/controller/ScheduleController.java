package pl.ppyrczak.busschedulesystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.ppyrczak.busschedulesystem.model.Schedule;
import pl.ppyrczak.busschedulesystem.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedules")
    public List<Schedule> getSchedules() {
        return scheduleService.getSchedules();
    }

    @GetMapping("/schedule/{id}")
    public Schedule getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @PostMapping("/addschedule")
    public Schedule addSchedule(@RequestBody Schedule schedule) {
        return scheduleService.addSchedule(schedule);
    }

    @PutMapping("/editschedule/{id}")
    public Schedule editSchedule(@RequestBody Schedule scheduleToUpdate, @PathVariable Long id) {
        return scheduleService.editSchedule(scheduleToUpdate, id);
    }

    @DeleteMapping("/deleteschedule/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}
