package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.ReportResponse;
import com.bizu.querycenter.dto.SaveReportRequest;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportResponse getReportById(Integer id){
        Report report = reportRepository.findById(id).orElseThrow(RuntimeException::new);

        return ReportResponse.builder()
                .name(report.getName())
                .build();
    }

    public List<Report> getAllReports(){
        List<Report> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);

        return reports;
    }

    public ReportResponse saveReport(SaveReportRequest request){

        Report report = Report.builder()
                .name(request.getName())
                .build();

        Report fromDB = reportRepository.save(report);

        return ReportResponse.builder()
                .name(fromDB.getName())
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
