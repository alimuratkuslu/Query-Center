package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.EmployeeResponse;
import com.bizu.querycenter.dto.SaveEmployeeRequest;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeResponse getEmployeeById(Integer id){
        Employee employee = employeeRepository.findById(id).orElseThrow(RuntimeException::new);

        return EmployeeResponse.builder()
                .name(employee.getName())
                .email(employee.getEmail())
                .reports(employee.getReports())
                .build();
    }

    public List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);

        return employees;
    }

    public EmployeeResponse saveEmployee(SaveEmployeeRequest request){

        Employee employee = Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        Employee fromDB = employeeRepository.save(employee);

        return EmployeeResponse.builder()
                .name(fromDB.getName())
                .email(fromDB.getEmail())
                .build();
    }
}
