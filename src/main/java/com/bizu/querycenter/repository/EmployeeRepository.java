package com.bizu.querycenter.repository;

import com.bizu.querycenter.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
