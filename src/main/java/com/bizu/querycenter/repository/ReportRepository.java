package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
