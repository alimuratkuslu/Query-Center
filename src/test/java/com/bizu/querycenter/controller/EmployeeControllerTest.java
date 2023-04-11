package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.ReportToEmployee;
import com.bizu.querycenter.dto.Request.SaveEmployeeRequest;
import com.bizu.querycenter.dto.Request.SaveReportRequest;
import com.bizu.querycenter.dto.Response.EmployeeResponse;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.service.EmployeeService;
import com.bizu.querycenter.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "integration")
class EmployeeControllerTest {

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ReportService reportService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void itShouldSaveEmployeeWhenValidEmployeeRequestBody() throws Exception {

        SaveEmployeeRequest request = SaveEmployeeRequest.builder()
                .name("testName")
                .email("testEmail")
                .build();

        EmployeeResponse response = EmployeeResponse.builder()
                .name("testName")
                .email("testEmail")
                .reports(null)
                .build();

        when(employeeService.saveEmployee(request)).thenReturn(response);

        mockMvc.perform(post("/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(request)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldGetEmployeeWhenValidEmployeeName() throws Exception {
        SaveEmployeeRequest request = SaveEmployeeRequest.builder()
                .name("testName")
                .email("testEmail")
                .build();

        Employee employee = Employee.builder()
                .name("testName")
                .email("testEmail")
                .build();

        employeeService.saveEmployee(request);

        String employeeName = "testName";

        when(employeeService.getEmployeeByName(employeeName)).thenReturn(employee);

        mockMvc.perform(get("/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(request)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetAllEmployees() throws Exception {
        List<Employee> employees = employeeService.getAllEmployees();

        int size = employees.size();

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/v1/employee"))
                .andExpect(jsonPath("$", hasSize(size)));

    }

    @Test
    void itShouldAddReportToEmployeeWhenReportAndEmployeeValid() throws Exception {
        Employee employee = Employee.builder()
                ._id(12345)
                .name("testEmployee")
                .email("testEmail")
                .build();

        Report report = Report.builder()
                ._id(6789)
                .name("testReport")
                .sqlQuery("{\n" + " \"_id \":{ \"$gt\" :  5 }\n" + "}")
                .build();

        SaveEmployeeRequest employeeRequest = SaveEmployeeRequest.builder()
                .name(employee.getName())
                .email(employee.getEmail())
                .build();

        SaveReportRequest reportRequest = SaveReportRequest.builder()
                .name(report.getName())
                .sqlQuery(report.getSqlQuery())
                .databaseName(null)
                .build();

        employeeService.saveEmployee(employeeRequest);
        reportService.saveReport(reportRequest);

        ReportToEmployee reportToEmployee = ReportToEmployee.builder()
                .reportId(report.get_id())
                .employeeId(employee.get_id())
                .build();

        List<Report> reports = new ArrayList<>();
        reports.add(report);

        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .name("testEmployee")
                .email("testEmail")
                .reports(reports)
                .build();

        when(employeeService.addReportToEmployee(reportToEmployee)).thenReturn(employeeResponse);

        mockMvc.perform(post("/v1/employee/addReport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(reportToEmployee)))
                .andDo(print())
                .andExpect(jsonPath("$.reports[0]._id").value(reportToEmployee.getReportId()))
                .andExpect(status().isOk());

    }

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}