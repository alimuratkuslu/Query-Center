package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddQueryToReport;
import com.bizu.querycenter.dto.Add.AddScheduleToReport;
import com.bizu.querycenter.dto.Request.SaveReportRequest;
import com.bizu.querycenter.dto.Response.ReportResponse;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.service.ReportService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/report")
public class ReportController {

    private final ReportService reportService;
    private final MongoTemplate mongoTemplate;

    public ReportController(ReportService reportService, MongoTemplate mongoTemplate) {
        this.reportService = reportService;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Integer id){
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports(){
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/searchReport")
    public ResponseEntity<Report> getReportByName(@RequestParam String name){
        return ResponseEntity.ok(reportService.getReportByName(name));
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

    @PatchMapping("/{id}")
    public void deactivateReport(@PathVariable Integer id){
        reportService.deactivateReport(id);
    }

    @PatchMapping("/activate/{id}")
    public void activateReport(@PathVariable Integer id){
        reportService.activateReport(id);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Integer id){
        reportService.deleteReportById(id);
    }
}
