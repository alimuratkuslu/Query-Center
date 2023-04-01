package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Add.AddQueryToReport;
import com.bizu.querycenter.dto.Add.AddScheduleToReport;
import com.bizu.querycenter.dto.Request.SaveOwnershipRequest;
import com.bizu.querycenter.dto.Response.ReportResponse;
import com.bizu.querycenter.dto.Request.SaveReportRequest;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.ReportOwnership;
import com.bizu.querycenter.model.Schedule;
import com.bizu.querycenter.repository.ReportRepository;
import com.bizu.querycenter.repository.ScheduleRepository;
import com.github.vincentrussell.query.mongodb.sql.converter.MongoDBQueryHolder;
import com.github.vincentrussell.query.mongodb.sql.converter.ParseException;
import com.github.vincentrussell.query.mongodb.sql.converter.QueryConverter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    private final ScheduleRepository scheduleRepository;
    private final ScheduleService scheduleService;

    public ReportService(ReportRepository reportRepository, ScheduleRepository scheduleRepository, ScheduleService scheduleService) {
        this.reportRepository = reportRepository;
        this.scheduleRepository = scheduleRepository;
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
                .sqlQuery(request.getSqlQuery())
                .employees(employees)
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

    public List<Employee> runEmployeeQuery(String query){
        System.out.println("Query string: " + query);
        MongoClient mongoClient = MongoClients.create("mongodb+srv://alimuratkuslu:alis2001@movieapi.urlccoc.mongodb.net/test");
        MongoDatabase database = mongoClient.getDatabase("QueryCenter");
        MongoCollection<Document> collection = database.getCollection("Employees");
        
        List<Document> queryResults = collection.find(Document.parse(query)).into(new ArrayList<>());

        System.out.println("Query results size: " + queryResults.size());

        List<Employee> employees = new ArrayList<>();
        for (Document doc : queryResults) {
            Employee employee = new Employee();

            // employee.setId(doc.getObjectId("_id").toString());
            employee.set_id(doc.getInteger("_id"));
            employee.setName(doc.getString("name"));
            employee.setEmail(doc.getString("email"));
            employees.add(employee);
            System.out.println("Creating Employee object: " + employee);
        }
        return employees;
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
