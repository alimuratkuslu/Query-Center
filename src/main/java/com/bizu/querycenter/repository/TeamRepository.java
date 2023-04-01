package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, Integer> {

    Team findByName(String name);
}
