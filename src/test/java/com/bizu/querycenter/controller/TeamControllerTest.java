package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddEmployeeToTeam;
import com.bizu.querycenter.dto.Request.SaveTeamRequest;
import com.bizu.querycenter.dto.Response.TeamResponse;
import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Team;
import com.bizu.querycenter.service.TeamService;
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
class TeamControllerTest {

    @MockBean
    private TeamService teamService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Team team;

    private Employee employee;

    private SaveTeamRequest saveTeamRequest;

    private SaveTeamRequest updatedTeamRequest;

    private TeamResponse teamResponse;

    private TeamResponse updatedTeamResponse;

    private TeamResponse addedEmployeeToTeamResponse;

    @BeforeEach
    public void init(){

        team = Team.builder()
                ._id(1)
                .name("testTeam")
                .employees(null)
                .teamMail("testMail")
                .build();

        employee = Employee.builder()
                ._id(1)
                .name("testName")
                .email("testEmail")
                .build();

        saveTeamRequest = SaveTeamRequest.builder()
                .name("testTeam")
                .teamMail("testMail")
                .build();

        updatedTeamRequest = SaveTeamRequest.builder()
                .name("testTeamUpdated")
                .teamMail("testMailUpdated")
                .build();

        teamResponse = TeamResponse.builder()
                .name("testTeam")
                .employees(null)
                .teamMail("testMail")
                .build();

        updatedTeamResponse = TeamResponse.builder()
                .name("testTeamUpdated")
                .employees(null)
                .teamMail("testMailUpdated")
                .build();

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        addedEmployeeToTeamResponse = TeamResponse.builder()
                .name("testTeamUpdated")
                .employees(employees)
                .teamMail("testMailUpdated")
                .build();

    }

    @Test
    void itShouldSaveTeamWhenValidTeamBody() throws Exception {

        when(teamService.saveTeam(saveTeamRequest)).thenReturn(teamResponse);

        mockMvc.perform(post("/v1/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(saveTeamRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("testTeam"))
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldGetTeamWhenValidId() throws Exception {
        Integer teamId = 1;

        when(teamService.getTeamById(teamId)).thenReturn(team);

        mockMvc.perform(get("/v1/team/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldUpdateTeamWhenValidTeamUpdateRequest() throws Exception {
        Integer teamId = 1;

        when(teamService.updateTeam(teamId, updatedTeamRequest)).thenReturn(updatedTeamResponse);

        mockMvc.perform(put("/v1/team/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(updatedTeamRequest)))
                .andExpect(jsonPath("$.name").value("testTeamUpdated"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeleteTeamWhenValidId() throws Exception {
        Integer teamId = 1;

        doNothing().when(teamService).deleteTeamById(teamId);

        mockMvc.perform(delete("/v1/team/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetAllTeams() throws Exception {
        List<Team> teams = teamService.getAllTeams();

        int size = teams.size();

        when(teamService.getAllTeams()).thenReturn(teams);

        mockMvc.perform(get("/v1/team"))
                .andExpect(jsonPath("$", hasSize(size)));

    }

    @Test
    void itShouldGetTeamWhenValidTeamName() throws Exception {

        String teamName = "testTeam";

        when(teamService.getTeamByName(teamName)).thenReturn(team);

        mockMvc.perform(get("/v1/team/searchTeam")
                        .param("name", teamName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(team)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("testTeam"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldAddEmployeeToTeamWhenValidAddEmployeeToTeamBody() throws Exception {

        Integer employeeId = 1;
        Integer teamId = 1;

        AddEmployeeToTeam request = AddEmployeeToTeam.builder()
                .employeeId(employeeId)
                .teamId(teamId)
                .build();

        when(teamService.addEmployeeToTeam(request)).thenReturn(addedEmployeeToTeamResponse);

        mockMvc.perform(post("/v1/team/addEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(request)))
                .andExpect(jsonPath("$.employees[0].name").value("testName"))
                .andExpect(status().isOk());
    }

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}