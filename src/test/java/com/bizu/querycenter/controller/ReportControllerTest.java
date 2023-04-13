package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddQueryToReport;
import com.bizu.querycenter.dto.Add.AddScheduleToReport;
import com.bizu.querycenter.dto.Request.SaveReportRequest;
import com.bizu.querycenter.dto.Response.ReportResponse;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.Schedule;
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
class ReportControllerTest {

    @MockBean
    private ReportService reportService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Report reportActive;

    private Report reportNotActive;

    private Schedule schedule;

    private List<Schedule> schedules = new ArrayList<>();

    private SaveReportRequest saveReportRequest;

    private SaveReportRequest updateReportRequest;

    private ReportResponse reportResponse;

    private ReportResponse updatedReportResponse;

    private ReportResponse addedQueryResponse;

    private ReportResponse addedScheduleResponse;

    @BeforeEach
    public void init(){

        reportActive = Report.builder()
                ._id(1)
                .name("testName")
                .sqlQuery("testSqlQuery")
                .isActive(true)
                .build();

        reportNotActive = Report.builder()
                ._id(2)
                .name("deactiveReport")
                .sqlQuery("testSqlQuery-inactive")
                .isActive(false)
                .build();

        schedule = Schedule.builder()
                ._id(1)
                .name("testSchedule")
                .mailSubject("testSubject")
                .build();

        saveReportRequest = SaveReportRequest.builder()
                .name("testName")
                .sqlQuery("Employees query")
                .databaseName("Employees")
                .build();

        updateReportRequest = SaveReportRequest.builder()
                .name("testName1")
                .sqlQuery("Reports query")
                .databaseName("Reports")
                .build();

        reportResponse = ReportResponse.builder()
                .name("testName")
                .sqlQuery("Employees query")
                .employees(null)
                .schedules(null)
                .build();

        updatedReportResponse = ReportResponse.builder()
                .name("testName1")
                .sqlQuery("Reports query")
                .employees(null)
                .schedules(null)
                .build();

        addedQueryResponse = ReportResponse.builder()
                .name("testName")
                .sqlQuery("added query")
                .employees(null)
                .schedules(null)
                .build();

        schedules.add(schedule);

        addedScheduleResponse = ReportResponse.builder()
                .name("testName1")
                .sqlQuery("Reports query")
                .employees(null)
                .schedules(schedules)
                .build();
    }

    @Test
    void itShouldSaveReportWhenValidReportRequestBody() throws Exception {

        when(reportService.saveReport(saveReportRequest)).thenReturn(reportResponse);

        mockMvc.perform(post("/v1/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(saveReportRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(saveReportRequest.getName()))
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldGetReportWhenValidReportName() throws Exception {

        String reportName = "testName";

        when(reportService.getReportByName(reportName)).thenReturn(reportActive);

        mockMvc.perform(get("/v1/report/searchReport")
                        .param("name", reportName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(reportActive)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value(reportName))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetReportWhenValidId() throws Exception {
        Integer reportId = 1;

        when(reportService.getReportById(reportId)).thenReturn(reportActive);

        mockMvc.perform(get("/v1/report/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(reportActive)))
                .andExpect(jsonPath("$._id").value(reportId))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetAllReports() throws Exception {
        List<Report> reports = reportService.getAllReports();

        int size = reports.size();

        when(reportService.getAllReports()).thenReturn(reports);

        mockMvc.perform(get("/v1/report"))
                .andExpect(jsonPath("$", hasSize(size)));

    }

    @Test
    void itShouldAddQueryToReport() throws Exception {
        Integer reportId = 1;
        AddQueryToReport queryRequest = AddQueryToReport.builder()
                .query("added query")
                .build();

        when(reportService.addQuery(reportId, queryRequest)).thenReturn(addedQueryResponse);

        mockMvc.perform(post("/v1/report/addQuery/{reportId}", reportId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(queryRequest)))
                .andExpect(jsonPath("$.sqlQuery").value("added query"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldAddScheduleToReport() throws Exception {
        Integer reportId = 1;
        AddScheduleToReport scheduleRequest = AddScheduleToReport.builder()
                .scheduleId(schedule.get_id())
                .reportId(reportId)
                .build();

        when(reportService.addScheduleToReport(scheduleRequest)).thenReturn(addedScheduleResponse);

        mockMvc.perform(post("/v1/report/addSchedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(scheduleRequest)))
                .andExpect(jsonPath("$.schedules[0].mailSubject").value("testSubject"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldUpdateReportWhenValidReportUpdateRequest() throws Exception {
        Integer reportId = 1;

        when(reportService.updateReport(reportId, updateReportRequest)).thenReturn(updatedReportResponse);

        mockMvc.perform(put("/v1/report/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(updateReportRequest)))
                .andExpect(jsonPath("$.sqlQuery").value("Reports query"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeactivateActiveReportWhenValid() throws Exception {
        Integer reportId = 1;

        doNothing().when(reportService).deactivateReport(reportId);

        mockMvc.perform(patch("/v1/report/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldActivateNotActiveReportWhenValid() throws Exception {
        Integer reportId = 2;

        doNothing().when(reportService).activateReport(reportId);

        mockMvc.perform(patch("/v1/report/activate/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeleteReportWhenValidId() throws Exception {
        Integer reportId = 1;

        doNothing().when(reportService).deleteReportById(reportId);

        mockMvc.perform(delete("/v1/report/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}