package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Request.SaveOwnershipRequest;
import com.bizu.querycenter.model.ReportOwnership;
import com.bizu.querycenter.service.ReportOwnershipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ownership")
public class ReportOwnershipController {

    private final ReportOwnershipService service;

    public ReportOwnershipController(ReportOwnershipService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportOwnership> getOwnershipById(@PathVariable Integer id){
        return ResponseEntity.ok(service.getReportOwnershipById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReportOwnership>> getAllOwnerships(){
        return ResponseEntity.ok(service.getAllOwnerships());
    }

    @GetMapping("/owned")
    public ResponseEntity<List<ReportOwnership>> getOwnershipOfEmployee(){
        return ResponseEntity.ok(service.getOwnershipForEmployee());
    }

    @GetMapping("/searchOwnership")
    public ResponseEntity<ReportOwnership> getOwnershipByName(@RequestParam String name){
        return ResponseEntity.ok(service.getOwnershipByName(name));
    }

    @PostMapping
    public ResponseEntity<ReportOwnership> saveOwnership(@RequestBody SaveOwnershipRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.saveOwnership(request));
    }
}
