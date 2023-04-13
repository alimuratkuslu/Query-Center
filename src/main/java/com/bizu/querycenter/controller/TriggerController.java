package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddNameToTrigger;
import com.bizu.querycenter.dto.Request.SaveTriggerRequest;
import com.bizu.querycenter.dto.Response.TriggerResponse;
import com.bizu.querycenter.model.Trigger;
import com.bizu.querycenter.service.TriggerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
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
    @Cacheable("triggers")
    public ResponseEntity<List<Trigger>> getAllTriggers(){
        return ResponseEntity.ok(triggerService.getAllTriggers());
    }

    @GetMapping("/searchTrigger")
    public ResponseEntity<Trigger> getTriggerByName(@RequestParam String name){
        return ResponseEntity.ok(triggerService.getTriggerByName(name));
    }

    @PostMapping
    public ResponseEntity<TriggerResponse> saveTrigger(@RequestBody SaveTriggerRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(triggerService.saveTrigger(request));
    }

    @PostMapping("/addName/{triggerId}")
    public ResponseEntity<TriggerResponse> addNameToTrigger(@PathVariable Integer triggerId, @RequestBody AddNameToTrigger request){
        return ResponseEntity.ok(triggerService.addName(triggerId, request));
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
