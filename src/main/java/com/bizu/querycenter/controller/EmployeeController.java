package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.EmployeeResponse;
import com.bizu.querycenter.dto.SaveEmployeeRequest;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody SaveEmployeeRequest request){
        return ResponseEntity.ok(employeeService.saveEmployee(request));
    }

    @PostMapping("/addReport/{employeeId}")
    public ResponseEntity<EmployeeResponse> addReportToEmployee(@PathVariable Integer employeeId, @RequestBody Report report){
        return ResponseEntity.ok(employeeService.addReportToEmployee(employeeId, report));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Integer id, @RequestBody SaveEmployeeRequest request){
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id){
        employeeService.deleteEmployeeById(id);
    }
}
