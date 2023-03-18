package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.EmployeeResponse;
import com.bizu.querycenter.dto.ReportResponse;
import com.bizu.querycenter.dto.SaveReportRequest;
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
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Integer id){
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

    @PutMapping("/{id}")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable Integer id, @RequestBody SaveReportRequest request){
        return ResponseEntity.ok(reportService.updateReport(id, request));
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Integer id){
        reportService.deleteReportById(id);
    }
}
