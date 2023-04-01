package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, Integer> {

    Employee findByName(String name);
}
