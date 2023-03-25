package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.AddQueryToReport;
import com.bizu.querycenter.dto.AddScheduleToReport;
import com.bizu.querycenter.dto.ReportResponse;
import com.bizu.querycenter.dto.SaveReportRequest;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.Schedule;
import com.bizu.querycenter.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final ScheduleService scheduleService;

    public ReportService(ReportRepository reportRepository, ScheduleService scheduleService) {
        this.reportRepository = reportRepository;
        this.scheduleService = scheduleService;
    }

    public Report getReportById(Integer id){
        Report report = reportRepository.findById(id).orElseThrow(RuntimeException::new);

        return report;
    }

    public Report getReportByName(String reportName){
        Report report = reportRepository.findByName(reportName);

        return report;
    }

    public List<Report> getAllReports(){
        List<Report> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);

        return reports;
    }

    public ReportResponse saveReport(SaveReportRequest request){

        List<Report> reports = getAllReports();
        List<Employee> employees = new ArrayList<>();
        int size = reports.size() + 2;

        Report report = Report.builder()
                ._id(size)
                .name(request.getName())
                .employees(employees)
                .build();

        Report fromDB = reportRepository.save(report);

        return ReportResponse.builder()
                .name(fromDB.getName())
                .build();
    }

    public ReportResponse addQuery(Integer reportId, AddQueryToReport query){
        Report report = getReportById(reportId);

        report.setSqlQuery(query.getQuery());
        reportRepository.save(report);

        return ReportResponse.builder()
                .name(report.getName())
                .sqlQuery(report.getSqlQuery())
                .employees(report.getEmployees())
                .build();
    }

    public ReportResponse addScheduleToReport(AddScheduleToReport reportDto){
        Report report = getReportById(reportDto.getReportId());
        Schedule schedule = scheduleService.getScheduleById(reportDto.getScheduleId());
        List<Schedule> schedules = report.getSchedules();
        schedules.add(schedule);

        report.setSchedules(schedules);

        return ReportResponse.builder()
                .name(report.getName())
                .sqlQuery(report.getSqlQuery())
                .employees(report.getEmployees())
                .schedules(report.getSchedules())
                .build();
    }

    public ReportResponse updateReport(Integer id, SaveReportRequest request){
        Report currentReport = reportRepository.findById(id).orElseThrow(RuntimeException::new);

        currentReport.setName(request.getName());

        reportRepository.save(currentReport);

        return ReportResponse.builder()
                .name(currentReport.getName())
                .build();
    }

    public void deleteReportById(Integer id){
        if(doesEmployeeExist(id)){
            reportRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Report does not exist");
        }
    }

    private boolean doesEmployeeExist(Integer id){
        return reportRepository.existsById(id);
    }
}
