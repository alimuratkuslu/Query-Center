package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.*;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.repository.EmployeeRepository;
import com.bizu.querycenter.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ReportRepository reportRepository;

    private final ReportOwnershipService ownershipService;


    public EmployeeService(EmployeeRepository employeeRepository, ReportRepository reportRepository, ReportOwnershipService ownershipService) {
        this.employeeRepository = employeeRepository;
        this.reportRepository = reportRepository;
        this.ownershipService = ownershipService;
    }

    public Employee getEmployeeById(Integer id){
        Employee employee = employeeRepository.findById(id).orElseThrow(RuntimeException::new);

        return employee;
    }

    public List<Employee> getAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);

        return employees;
    }

    public EmployeeResponse saveEmployee(SaveEmployeeRequest request){

        List<Employee> employees = getAllEmployees();
        List<Report> reports = new ArrayList<>();
        int size = employees.size() + 2;

        Employee employee = Employee.builder()
                ._id(size)
                .name(request.getName())
                .email(request.getEmail())
                .reports(reports)
                .build();

        Employee fromDB = employeeRepository.save(employee);

        return EmployeeResponse.builder()
                .name(fromDB.getName())
                .email(fromDB.getEmail())
                .build();
    }

    public OwnershipDto addReportToEmployee(Integer employeeId, AddReportToEmployee report){
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Report reportFromDB = reportRepository.findByName(report.getReportName());

        if(doesEmployeeExist(employee.get_id())){
            List<Report> reports = employee.getReports();
            reports.add(reportFromDB);
            employee.setReports(reports);
            employeeRepository.save(employee);

            List<Employee> employees = reportFromDB.getEmployees();
            employees.add(employee);
            reportFromDB.setEmployees(employees);
            reportRepository.save(reportFromDB);
        }

        return OwnershipDto.builder()
                .report(reportFromDB)
                .employee(employee)
                .build();
    }

    public void giveOwnershipToEmployee(Integer employeeId, AddReportToEmployee report){

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Report reportFromDB = reportRepository.findByName(report.getReportName());

        addReportToEmployee(employeeId, report);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(true)
                .isRead(false)
                .isWrite(false)
                .isRun(false)
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void giveReadOwnership(Integer employeeId, AddReportToEmployee report){

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Report reportFromDB = reportRepository.findByName(report.getReportName());

        addReportToEmployee(employeeId, report);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(false)
                .isRead(true)
                .isWrite(false)
                .isRun(false)
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void giveWriteOwnership(Integer employeeId, AddReportToEmployee report){

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Report reportFromDB = reportRepository.findByName(report.getReportName());

        addReportToEmployee(employeeId, report);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(false)
                .isRead(false)
                .isWrite(true)
                .isRun(false)
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void giveRunOwnership(Integer employeeId, AddReportToEmployee report){

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Report reportFromDB = reportRepository.findByName(report.getReportName());

        addReportToEmployee(employeeId, report);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(false)
                .isRead(false)
                .isWrite(false)
                .isRun(true)
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void giveAllOwnerships(Integer employeeId, AddReportToEmployee report){

        Employee employee = employeeRepository.findById(employeeId).orElseThrow(RuntimeException::new);
        Report reportFromDB = reportRepository.findByName(report.getReportName());

        addReportToEmployee(employeeId, report);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(false)
                .isRead(true)
                .isWrite(true)
                .isRun(true)
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public EmployeeResponse updateEmployee(Integer id, SaveEmployeeRequest request){
        Employee currentEmployee = employeeRepository.findById(id).orElseThrow(RuntimeException::new);

        currentEmployee.setName(request.getName());
        currentEmployee.setEmail(request.getEmail());

        employeeRepository.save(currentEmployee);

        return EmployeeResponse.builder()
                .name(currentEmployee.getName())
                .email(currentEmployee.getEmail())
                .build();
    }

    public void deleteEmployeeById(Integer id){
        if(doesEmployeeExist(id)){
            employeeRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Employee does not exist");
        }
    }

    private boolean doesEmployeeExist(Integer id){
        return employeeRepository.existsById(id);
    }
}
