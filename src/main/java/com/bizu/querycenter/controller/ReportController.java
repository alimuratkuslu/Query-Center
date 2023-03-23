package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.*;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Integer id){
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports(){
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @PostMapping
    public ResponseEntity<ReportResponse> saveReport(@RequestBody SaveReportRequest request){
        return ResponseEntity.ok(reportService.saveReport(request));
    }

    @PostMapping("/addQuery/{reportId}")
    public ResponseEntity<ReportResponse> addQueryToReport(@PathVariable Integer reportId, @RequestBody AddQueryToReport query){
        return ResponseEntity.ok(reportService.addQuery(reportId, query));
    }

    @PostMapping("/addSchedule")
    public ResponseEntity<ReportResponse> addScheduleToReport(@RequestBody AddScheduleToReport scheduleDto){
        return ResponseEntity.ok(reportService.addScheduleToReport(scheduleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable Integer id, @RequestBody SaveReportRequest request){
        return ResponseEntity.ok(reportService.updateReport(id, request));
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Integer id){
        reportService.deleteReportById(id);
    }
}
