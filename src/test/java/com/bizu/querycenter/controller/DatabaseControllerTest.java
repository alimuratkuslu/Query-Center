package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddDatabaseToReport;
import com.bizu.querycenter.dto.Request.SaveDatabaseRequest;
import com.bizu.querycenter.dto.Response.DatabaseResponse;
import com.bizu.querycenter.model.Database;
import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.service.DatabaseService;
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
class DatabaseControllerTest {

    @MockBean
    private DatabaseService databaseService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Database database;

    public Report report;

    private SaveDatabaseRequest databaseRequest;

    private SaveDatabaseRequest updatedDatabaseRequest;

    private DatabaseResponse databaseResponse;

    private DatabaseResponse updatedDatabaseResponse;

    private DatabaseResponse addedDatabaseToReportResponse;

    @BeforeEach
    public void init(){

        database = Database.builder()
                ._id(1)
                .name("testDB")
                .connectionString("testConn")
                .reports(null)
                .build();

        report = Report.builder()
                ._id(1)
                .name("testReport")
                .sqlQuery("testQuery")
                .isActive(true)
                .build();

        List<Report> reports = new ArrayList<>();
        reports.add(report);

        databaseRequest = SaveDatabaseRequest.builder()
                .name("testDB")
                .connectionString("testConn")
                .build();

        updatedDatabaseRequest = SaveDatabaseRequest.builder()
                .name("testDBUpdated")
                .connectionString("testConnUpdated")
                .build();

        databaseResponse = DatabaseResponse.builder()
                .name("testDB")
                .connectionString("testConn")
                .reports(null)
                .build();

        updatedDatabaseResponse = DatabaseResponse.builder()
                .name("testDBUpdated")
                .connectionString("testConnUpdated")
                .reports(null)
                .build();

        addedDatabaseToReportResponse = DatabaseResponse.builder()
                .name("testDB")
                .connectionString("testConn")
                .reports(reports)
                .build();
    }

    @Test
    void itShouldSaveDatabaseWhenValidDatabaseBody() throws Exception {

        when(databaseService.saveDatabase(databaseRequest)).thenReturn(databaseResponse);

        mockMvc.perform(post("/v1/database")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(databaseRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("testDB"))
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldGetDatabaseWhenValidId() throws Exception {
        Integer databaseId = 1;

        when(databaseService.getDatabaseById(databaseId)).thenReturn(database);

        mockMvc.perform(get("/v1/database/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldUpdateDatabaseWhenValidDatabaseUpdateRequest() throws Exception {
        Integer databaseId = 1;

        when(databaseService.updateDatabase(databaseId, updatedDatabaseRequest)).thenReturn(updatedDatabaseResponse);

        mockMvc.perform(put("/v1/database/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(updatedDatabaseRequest)))
                .andExpect(jsonPath("$.name").value("testDBUpdated"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeleteDatabaseWhenValidId() throws Exception {
        Integer databaseId = 1;

        doNothing().when(databaseService).deleteDatabaseById(databaseId);

        mockMvc.perform(delete("/v1/database/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetAllDatabases() throws Exception {
        List<Database> databases = databaseService.getAllDatabases();

        int size = databases.size();

        when(databaseService.getAllDatabases()).thenReturn(databases);

        mockMvc.perform(get("/v1/database"))
                .andExpect(jsonPath("$", hasSize(size)));

    }

    @Test
    void itShouldGetDatabaseWhenValidDatabaseName() throws Exception {

        String databaseName = "testDB";

        when(databaseService.getDatabaseByName(databaseName)).thenReturn(database);

        mockMvc.perform(get("/v1/database/searchDatabase")
                .param("name", databaseName)
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(database)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("testDB"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldAddDatabaseToReportWhenValidAddDatabaseToReportBody() throws Exception {

        Integer databaseId = 1;
        Integer reportId = 1;

        AddDatabaseToReport request = AddDatabaseToReport.builder()
                .databaseId(databaseId)
                .reportId(reportId)
                .build();

        when(databaseService.addDatabaseToReport(request)).thenReturn(addedDatabaseToReportResponse);

        mockMvc.perform(post("/v1/database/addReport")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializeJson(request)))
                .andExpect(jsonPath("$.reports[0].name").value("testReport"))
                .andExpect(status().isOk());
    }

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}