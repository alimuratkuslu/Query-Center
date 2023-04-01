package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddReportToEmployee;
import com.bizu.querycenter.dto.ReportToEmployee;
import com.bizu.querycenter.dto.Request.SaveEmployeeRequest;
import com.bizu.querycenter.dto.Response.EmployeeResponse;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.service.EmployeeService;
import com.mongodb.client.FindIterable;
import org.bson.Document;
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

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/searchEmployee")
    public ResponseEntity<Employee> getEmployeeByName(@RequestParam String name){
        return ResponseEntity.ok(employeeService.getEmployeeByName(name));
    }

    @GetMapping("/runQuery")
    public ResponseEntity<List<String>> runQuery(@RequestParam String query){
        return ResponseEntity.ok(employeeService.runQuery(query));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody SaveEmployeeRequest request){
        return ResponseEntity.ok(employeeService.saveEmployee(request));
    }

    @PostMapping("/addReport")
    public ResponseEntity<EmployeeResponse> addReportToEmployee(@RequestBody ReportToEmployee request){
        return ResponseEntity.ok(employeeService.addReportToEmployee(request));
    }

    @PostMapping("/giveOwnership/{employeeId}")
    public void giveOwnership(@PathVariable Integer employeeId, @RequestBody AddReportToEmployee report){
        employeeService.giveOwnershipToEmployee(employeeId, report);
    }

    @PostMapping("/giveReadOwnership/{employeeId}")
    public void giveReadOwnership(@PathVariable Integer employeeId, @RequestBody AddReportToEmployee report){
        employeeService.giveReadOwnership(employeeId, report);
    }

    @PostMapping("/giveWriteOwnership/{employeeId}")
    public void giveWriteOwnership(@PathVariable Integer employeeId, @RequestBody AddReportToEmployee report){
        employeeService.giveWriteOwnership(employeeId, report);
    }

    @PostMapping("/giveRunOwnership/{employeeId}")
    public void giveRunOwnership(@PathVariable Integer employeeId, @RequestBody AddReportToEmployee report){
        employeeService.giveRunOwnership(employeeId, report);
    }

    @PostMapping("/giveAllOwnerships/{employeeId}")
    public void giveAllOwnerships(@PathVariable Integer employeeId, @RequestBody AddReportToEmployee report){
        employeeService.giveAllOwnerships(employeeId, report);
    }

    @PostMapping("/deleteOwnership/{employeeId}")
    public void deleteOwnership(@PathVariable Integer employeeId, @RequestBody AddReportToEmployee report){
        employeeService.deleteOwnership(employeeId, report);
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
