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
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private Employee employeeActive;

    private Employee employeeNotActive;

    private SaveEmployeeRequest saveEmployeeRequest;

    private SaveEmployeeRequest updateEmployeeRequest;

    private EmployeeResponse employeeResponse;

    private EmployeeResponse updatedEmployeeResponse;

    @BeforeEach
    public void init(){
        employeeActive = Employee.builder()
                ._id(1)
                .name("testName")
                .email("test@gmail.com")
                .reports(null)
                .isActive(true)
                .build();

        employeeNotActive = Employee.builder()
                ._id(2)
                .name("deactiveName")
                .email("deactiveEmail@gmail.com")
                .reports(null)
                .isActive(false)
                .build();

        saveEmployeeRequest = SaveEmployeeRequest.builder()
                .name("testName")
                .email("test@gmail.com")
                .build();

        updateEmployeeRequest = SaveEmployeeRequest.builder()
                .name("testName1")
                .email("test1@gmail.com")
                .build();

        employeeResponse = EmployeeResponse.builder()
                .name("testName")
                .email("test@gmail.com")
                .reports(null)
                .build();

        updatedEmployeeResponse = EmployeeResponse.builder()
                .name("testName1")
                .email("test1@gmail.com")
                .reports(null)
                .build();
    }

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

        String employeeName = "testName";

        when(employeeService.getEmployeeByName(employeeName)).thenReturn(employeeActive);

        mockMvc.perform(get("/v1/employee/searchEmployee")
                .param("name", employeeName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(employeeActive)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(employeeName))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetEmployeeWhenValidId() throws Exception {
        Integer employeeId = 1;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employeeActive);

        mockMvc.perform(get("/v1/employee/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(employeeActive)))
                .andExpect(jsonPath("$._id").value(employeeId))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldUpdateEmployeeWhenValidEmployeeUpdateRequest() throws Exception {
        Integer employeeId = 1;

        when(employeeService.updateEmployee(employeeId, updateEmployeeRequest)).thenReturn(updatedEmployeeResponse);

        mockMvc.perform(put("/v1/employee/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(updatedEmployeeResponse)))
                .andExpect(jsonPath("$.email").value("test1@gmail.com"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeleteEmployeeWhenValidId() throws Exception {
        Integer employeeId = 1;

        doNothing().when(employeeService).deleteEmployeeById(employeeId);

        mockMvc.perform(delete("/v1/employee/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeactivateActiveEmployeeWhenValid() throws Exception {
        Integer employeeId = 1;

        doNothing().when(employeeService).deactivateEmployee(employeeId);

        mockMvc.perform(patch("/v1/employee/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldActivateNotActiveEmployeeWhenValid() throws Exception {
        Integer employeeId = 2;

        doNothing().when(employeeService).activateEmployee(employeeId);

        mockMvc.perform(patch("/v1/employee/activate/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
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