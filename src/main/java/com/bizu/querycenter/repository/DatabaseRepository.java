package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Database;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatabaseRepository extends MongoRepository<Database, Integer> {

    Database findByName(String name);
}
