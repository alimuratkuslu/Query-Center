package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepository extends MongoRepository<Request, Integer> {
}
