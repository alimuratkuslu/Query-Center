package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, Integer> {
}
