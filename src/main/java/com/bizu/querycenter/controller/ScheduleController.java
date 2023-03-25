package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.AddTriggerToSchedule;
import com.bizu.querycenter.dto.SaveScheduleRequest;
import com.bizu.querycenter.dto.ScheduleResponse;
import com.bizu.querycenter.model.Schedule;
import com.bizu.querycenter.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Integer id){
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return ResponseEntity.ok(scheduleService.getAllSchedules());
    }

    @PostMapping
    public ResponseEntity<ScheduleResponse> saveSchedule(@RequestBody SaveScheduleRequest request){
        return ResponseEntity.ok(scheduleService.saveSchedule(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> updateSchedule(@PathVariable Integer id, @RequestBody SaveScheduleRequest request){
        return ResponseEntity.ok(scheduleService.updateSchedule(id, request));
    }

    @PostMapping("/addTrigger")
    public ResponseEntity<ScheduleResponse> addTriggerToSchedule(@RequestBody AddTriggerToSchedule request){
        return ResponseEntity.ok(scheduleService.addTriggerToSchedule(request));
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Integer id){
        scheduleService.deleteScheduleById(id);
    }
}
