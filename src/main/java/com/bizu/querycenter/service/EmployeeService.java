package com.bizu.querycenter.service;

import com.bizu.querycenter.dto.Add.AddReportToEmployee;
import com.bizu.querycenter.dto.ReportToEmployee;
import com.bizu.querycenter.dto.Request.SaveEmployeeRequest;
import com.bizu.querycenter.dto.Request.SaveOwnershipRequest;
import com.bizu.querycenter.dto.Response.EmployeeResponse;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.ReportOwnership;
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

    public Employee getEmployeeByName(String employeeName){
        Employee employee = employeeRepository.findByName(employeeName);

        return employee;
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

    public EmployeeResponse addReportToEmployee(ReportToEmployee request){
        Employee employee = getEmployeeById(request.getEmployeeId());
        Report reportFromDB = reportRepository.findById(request.getReportId()).orElseThrow(RuntimeException::new);

        List<Report> reports = employee.getReports();
        List<Employee> employees = reportFromDB.getEmployees();

        reports.add(reportFromDB);
        employee.setReports(reports);

        employees.add(employee);
        reportFromDB.setEmployees(employees);

        employeeRepository.save(employee);
        reportRepository.save(reportFromDB);

        return EmployeeResponse.builder()
                .name(employee.getName())
                .email(employee.getEmail())
                .reports(reports)
                .build();
    }

    public void giveOwnershipToEmployee(Integer employeeId, AddReportToEmployee report){

        Employee employee = getEmployeeById(employeeId);
        Report reportFromDB = reportRepository.findByName(report.getReportName());

        ReportOwnership ownership = ownershipService.doesEmployeeHaveOwnership(employeeId, reportFromDB.get_id());

        ReportToEmployee request = ReportToEmployee.builder()
                .employeeId(employeeId)
                .reportId(reportFromDB.get_id())
                .build();

        addReportToEmployee(request);

        ownership.setOwner(true);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(ownership.isOwner())
                .isRead(ownership.isRead())
                .isWrite(ownership.isWrite())
                .isRun(ownership.isRun())
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void giveReadOwnership(Integer employeeId, AddReportToEmployee report){

        Employee employee = getEmployeeById(employeeId);
        Report reportFromDB = reportRepository.findByName(report.getReportName());

        ReportOwnership ownership = ownershipService.doesEmployeeHaveOwnership(employeeId, reportFromDB.get_id());

        ReportToEmployee request = ReportToEmployee.builder()
                .employeeId(employeeId)
                .reportId(reportFromDB.get_id())
                .build();

        addReportToEmployee(request);

        ownership.setRead(true);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(ownership.isOwner())
                .isRead(ownership.isRead())
                .isWrite(ownership.isWrite())
                .isRun(ownership.isRun())
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void giveWriteOwnership(Integer employeeId, AddReportToEmployee report){

        Employee employee = getEmployeeById(employeeId);
        Report reportFromDB = reportRepository.findByName(report.getReportName());
        ReportOwnership ownership = ownershipService.doesEmployeeHaveOwnership(employeeId, reportFromDB.get_id());

        ReportToEmployee request = ReportToEmployee.builder()
                .employeeId(employeeId)
                .reportId(reportFromDB.get_id())
                .build();

        addReportToEmployee(request);

        ownership.setWrite(true);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(ownership.isOwner())
                .isRead(ownership.isRead())
                .isWrite(ownership.isWrite())
                .isRun(ownership.isRun())
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void giveRunOwnership(Integer employeeId, AddReportToEmployee report){

        Employee employee = getEmployeeById(employeeId);
        Report reportFromDB = reportRepository.findByName(report.getReportName());
        ReportOwnership ownership = ownershipService.doesEmployeeHaveOwnership(employeeId, reportFromDB.get_id());

        ReportToEmployee request = ReportToEmployee.builder()
                .employeeId(employeeId)
                .reportId(reportFromDB.get_id())
                .build();

        addReportToEmployee(request);

        ownership.setRun(true);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(ownership.isOwner())
                .isRead(ownership.isRead())
                .isWrite(ownership.isWrite())
                .isRun(ownership.isRun())
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void giveAllOwnerships(Integer employeeId, AddReportToEmployee report){

        Employee employee = getEmployeeById(employeeId);
        Report reportFromDB = reportRepository.findByName(report.getReportName());
        ReportOwnership ownership = ownershipService.doesEmployeeHaveOwnership(employeeId, reportFromDB.get_id());

        ReportToEmployee request = ReportToEmployee.builder()
                .employeeId(employeeId)
                .reportId(reportFromDB.get_id())
                .build();

        addReportToEmployee(request);

        ownership.setRead(true);
        ownership.setWrite(true);
        ownership.setRun(true);

        SaveOwnershipRequest ownershipRequest = SaveOwnershipRequest.builder()
                .report(reportFromDB)
                .employee(employee)
                .isOwner(ownership.isOwner())
                .isRead(ownership.isRead())
                .isWrite(ownership.isWrite())
                .isRun(ownership.isRun())
                .build();

        ownershipService.saveOwnership(ownershipRequest);
    }

    public void deleteOwnership(Integer employeeId, AddReportToEmployee report){
        Employee employee = getEmployeeById(employeeId);
        Report reportFromDB = reportRepository.findByName(report.getReportName());
        ReportOwnership ownership = ownershipService.doesEmployeeHaveOwnership(employeeId, reportFromDB.get_id());

        ownership.setOwner(false);
        ownership.setRead(false);
        ownership.setWrite(false);
        ownership.setRun(false);

        // ownershipService.deleteOwnership(employeeId, reportFromDB.get_id());

        List<Report> reports = employee.getReports();
        reports.remove(reportFromDB);
        employee.setReports(reports);
        employeeRepository.save(employee);

        List<Employee> employees = reportFromDB.getEmployees();
        employees.remove(employee);
        reportFromDB.setEmployees(employees);
        reportRepository.save(reportFromDB);
    }

    public EmployeeResponse updateEmployee(Integer id, SaveEmployeeRequest request){
        Employee currentEmployee = getEmployeeById(id);

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
