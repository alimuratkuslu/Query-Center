package com.bizu.querycenter.service;

import com.bizu.querycenter.repository.ReportOwnershipRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportOwnershipService {

    private final ReportOwnershipRepository repository;

    public ReportOwnershipService(ReportOwnershipRepository repository) {
        this.repository = repository;
    }
}
