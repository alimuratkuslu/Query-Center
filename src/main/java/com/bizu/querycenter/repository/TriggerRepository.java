package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Trigger;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TriggerRepository extends MongoRepository<Trigger, Integer> {

    Trigger findByName(String name);
}
