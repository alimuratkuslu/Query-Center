package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.SaveScheduleRequest;
import com.bizu.querycenter.dto.SaveTriggerRequest;
import com.bizu.querycenter.dto.ScheduleResponse;
import com.bizu.querycenter.dto.TriggerResponse;
import com.bizu.querycenter.model.Trigger;
import com.bizu.querycenter.service.TriggerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/trigger")
public class TriggerController {

    private final TriggerService triggerService;

    public TriggerController(TriggerService triggerService) {
        this.triggerService = triggerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trigger> getTriggerById(@PathVariable Integer id){
        return ResponseEntity.ok(triggerService.getTriggerById(id));
    }

    @GetMapping
    public ResponseEntity<List<Trigger>> getAllTriggers(){
        return ResponseEntity.ok(triggerService.getAllTriggers());
    }

    @PostMapping
    public ResponseEntity<TriggerResponse> saveTrigger(@RequestBody SaveTriggerRequest request){
        return ResponseEntity.ok(triggerService.saveTrigger(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TriggerResponse> updateTrigger(@PathVariable Integer id, @RequestBody SaveTriggerRequest request){
        return ResponseEntity.ok(triggerService.updateTrigger(id, request));
    }

    @DeleteMapping("/{id}")
    public void deleteTrigger(@PathVariable Integer id){
        triggerService.deleteTriggerById(id);
    }
}
