package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Add.AddQueryToReport;
import com.bizu.querycenter.dto.Add.AddScheduleToReport;
import com.bizu.querycenter.dto.Request.SaveReportRequest;
import com.bizu.querycenter.dto.Response.ReportResponse;
import com.bizu.querycenter.model.Database;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.Schedule;
import com.bizu.querycenter.repository.ReportRepository;
import com.bizu.querycenter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;

    private final DatabaseService databaseService;

    public ReportService(ReportRepository reportRepository, ScheduleRepository scheduleRepository, ScheduleService scheduleService, DatabaseService databaseService) {
        this.reportRepository = reportRepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
        this.databaseService = databaseService;
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
        List<Schedule> schedules = new ArrayList<>();

        Database database = databaseService.getDatabaseByName(request.getDatabaseName());

        int size = reports.size() + 2;

        Report report = Report.builder()
                ._id(size)
                .name(request.getName())
                .sqlQuery(request.getSqlQuery())
                .database(database)
                .employees(employees)
                .schedules(schedules)
                .isActive(true)
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
        List<Report> reports = schedule.getReports();

        reports.add(report);
        schedule.setReports(reports);

        schedules.add(schedule);
        report.setSchedules(schedules);

        scheduleRepository.save(schedule);
        reportRepository.save(report);

        return ReportResponse.builder()
                .name(report.getName())
                .sqlQuery(report.getSqlQuery())
                .employees(report.getEmployees())
                .schedules(report.getSchedules())
                .build();
    }

    public ReportResponse updateReport(Integer id, SaveReportRequest request){
        Report currentReport = reportRepository.findById(id).orElseThrow(RuntimeException::new);
        Database database = databaseService.getDatabaseByName(request.getDatabaseName());

        currentReport.setName(request.getName());
        currentReport.setSqlQuery(request.getSqlQuery());
        currentReport.setDatabase(database);

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

    public void activateReport(Integer id){
        changeReportStatus(id, true);
    }

    public void deactivateReport(Integer id){
        changeReportStatus(id, false);
    }

    private void changeReportStatus(Integer id, Boolean isActive){
        Report currentReport = getReportById(id);

        currentReport.setActive(isActive);
        reportRepository.save(currentReport);
    }

    private boolean doesEmployeeExist(Integer id){
        return reportRepository.existsById(id);
    }
}
