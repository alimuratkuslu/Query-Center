package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Request.SaveOwnershipRequest;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.ReportOwnership;
import com.bizu.querycenter.service.ReportOwnershipService;
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
class ReportOwnershipControllerTest {

    @MockBean
    private ReportOwnershipService ownershipService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ReportOwnership reportOwnership;

    private SaveOwnershipRequest saveOwnershipRequest;

    private Report report;

    @BeforeEach
    public void init(){

        report = Report.builder()
                ._id(1)
                .name("testReport")
                .sqlQuery("testQuery")
                .build();

        reportOwnership = ReportOwnership.builder()
                ._id(1)
                .report(report)
                .employee(null)
                .isOwner(true)
                .isRead(true)
                .isWrite(false)
                .isRun(false)
                .build();

        saveOwnershipRequest = SaveOwnershipRequest.builder()
                .report(report)
                .employee(null)
                .isOwner(true)
                .isRead(true)
                .isWrite(false)
                .isRun(false)
                .build();
    }

    @Test
    void itShouldSaveOwnershipWhenValidOwnershipBody() throws Exception {

        when(ownershipService.saveOwnership(saveOwnershipRequest)).thenReturn(reportOwnership);

        mockMvc.perform(post("/v1/ownership")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(saveOwnershipRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.report.name").value("testReport"))
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldGetOwnershipWhenValidId() throws Exception {
        Integer ownershipId = 1;

        when(ownershipService.getReportOwnershipById(ownershipId)).thenReturn(reportOwnership);

        mockMvc.perform(get("/v1/ownership/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetAllOwnerships() throws Exception {
        List<ReportOwnership> ownerships = ownershipService.getAllOwnerships();

        int size = ownerships.size();

        when(ownershipService.getAllOwnerships()).thenReturn(ownerships);

        mockMvc.perform(get("/v1/ownership"))
                .andExpect(jsonPath("$", hasSize(size)));

    }

    @Test
    void itShouldGetOwnershipWhenValidReportName() throws Exception {

        String reportName = "testReport";

        when(ownershipService.getOwnershipByName(reportName)).thenReturn(reportOwnership);

        mockMvc.perform(get("/v1/ownership/searchOwnership")
                        .param("name", reportName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(reportOwnership)))
                .andDo(print())
                .andExpect(jsonPath("$.report.name").value("testReport"))
                .andExpect(status().isOk());
    }

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}