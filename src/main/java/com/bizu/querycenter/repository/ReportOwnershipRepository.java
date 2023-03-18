package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.ReportOwnership;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportOwnershipRepository extends MongoRepository<ReportOwnership, Integer> {
}
