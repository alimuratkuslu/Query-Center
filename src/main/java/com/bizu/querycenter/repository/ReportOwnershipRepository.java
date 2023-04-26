package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.ReportOwnership;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportOwnershipRepository extends MongoRepository<ReportOwnership, Integer> {

    List<ReportOwnership> findEmployeeByEmail(String email);
}
