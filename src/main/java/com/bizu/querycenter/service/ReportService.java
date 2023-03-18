package com.bizu.querycenter.service;

import com.bizu.querycenter.repository.ReportRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }
}
