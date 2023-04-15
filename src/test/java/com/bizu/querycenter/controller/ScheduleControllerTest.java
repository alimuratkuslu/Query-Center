package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddSubjectToSchedule;
import com.bizu.querycenter.dto.Add.AddTriggerToSchedule;
import com.bizu.querycenter.dto.Request.SaveScheduleRequest;
import com.bizu.querycenter.dto.Response.ScheduleResponse;
import com.bizu.querycenter.model.Schedule;
import com.bizu.querycenter.model.Trigger;
import com.bizu.querycenter.service.ScheduleService;
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
class ScheduleControllerTest {

    @MockBean
    private ScheduleService scheduleService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Schedule schedule;

    public Trigger trigger;

    private String mailSubject;

    private SaveScheduleRequest saveScheduleRequest;

    private SaveScheduleRequest updatedScheduleRequest;

    private ScheduleResponse scheduleResponse;

    private ScheduleResponse updatedScheduleResponse;

    private ScheduleResponse addedSubjectToScheduleResponse;

    private ScheduleResponse addedTriggerToScheduleResponse;

    @BeforeEach
    public void init(){

        schedule = Schedule.builder()
                ._id(1)
                .name("testName")
                .mailSubject("testSubject")
                .triggers(null)
                .build();

        trigger = Trigger.builder()
                ._id(1)
                .name("testTrigger")
                .cronExpression("0 * * * *")
                .build();

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(trigger);

        mailSubject = "addedSubject";

        saveScheduleRequest = SaveScheduleRequest.builder()
                .name("testName")
                .mailSubject("testSubject")
                .triggers(null)
                .recipients(null)
                .build();

        scheduleResponse = ScheduleResponse.builder()
                .name("testName")
                .mailSubject("testSubject")
                .recipients(null)
                .triggers(null)
                .build();

        updatedScheduleRequest = SaveScheduleRequest.builder()
                .name("testUpdatedName")
                .mailSubject("updatedSubject")
                .triggers(null)
                .recipients(null)
                .build();

        updatedScheduleResponse = ScheduleResponse.builder()
                .name("testUpdatedName")
                .mailSubject("updatedSubject")
                .triggers(null)
                .recipients(null)
                .build();

        addedSubjectToScheduleResponse = ScheduleResponse.builder()
                .name("testName")
                .mailSubject("addedSubject")
                .triggers(null)
                .recipients(null)
                .build();

        addedTriggerToScheduleResponse = ScheduleResponse.builder()
                .name("testName")
                .mailSubject("testSubject")
                .recipients(null)
                .triggers(triggers)
                .build();
    }

    @Test
    void itShouldSaveScheduleWhenValidScheduleBody() throws Exception {

        when(scheduleService.saveSchedule(saveScheduleRequest)).thenReturn(scheduleResponse);

        mockMvc.perform(post("/v1/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(saveScheduleRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldGetScheduleWhenValidId() throws Exception {
        Integer scheduleId = 1;

        when(scheduleService.getScheduleById(scheduleId)).thenReturn(schedule);

        mockMvc.perform(get("/v1/schedule/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldUpdateScheduleWhenValidScheduleUpdateRequest() throws Exception {
        Integer scheduleId = 1;

        when(scheduleService.updateSchedule(scheduleId, updatedScheduleRequest)).thenReturn(updatedScheduleResponse);

        mockMvc.perform(put("/v1/schedule/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(updatedScheduleRequest)))
                .andExpect(jsonPath("$.name").value("testUpdatedName"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeleteScheduleWhenValidId() throws Exception {
        Integer scheduleId = 1;

        doNothing().when(scheduleService).deleteScheduleById(scheduleId);

        mockMvc.perform(delete("/v1/schedule/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetAllSchedules() throws Exception {
        List<Schedule> schedules = scheduleService.getAllSchedules();

        int size = schedules.size();

        when(scheduleService.getAllSchedules()).thenReturn(schedules);

        mockMvc.perform(get("/v1/schedule"))
                .andExpect(jsonPath("$", hasSize(size)));

    }

    @Test
    void itShouldGetScheduleWhenValidScheduleName() throws Exception {

        String scheduleName = "testName";

        when(scheduleService.getScheduleByName(scheduleName)).thenReturn(schedule);

        mockMvc.perform(get("/v1/schedule/searchSchedule")
                        .param("name", scheduleName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(schedule)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldAddSubjectToScheduleWhenValidAddSubjectToScheduleBody() throws Exception {

        Integer scheduleId = 1;

        AddSubjectToSchedule request = AddSubjectToSchedule.builder()
                .subject(mailSubject)
                .build();

        when(scheduleService.addSchedule(scheduleId, request)).thenReturn(addedSubjectToScheduleResponse);

        mockMvc.perform(post("/v1/schedule/addSubject/{id}", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(request)))
                .andExpect(jsonPath("$.mailSubject").value("addedSubject"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldAddTriggerToScheduleWhenValidAddTriggerToScheduleBody() throws Exception {

        Integer scheduleId = 1;
        Integer triggerId = 1;

        AddTriggerToSchedule request = AddTriggerToSchedule.builder()
                .scheduleId(1)
                .triggerId(1)
                .build();

        when(scheduleService.addTriggerToSchedule(request)).thenReturn(addedTriggerToScheduleResponse);

        mockMvc.perform(post("/v1/schedule/addTrigger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(request)))
                .andExpect(jsonPath("$.triggers[0].name").value("testTrigger"))
                .andExpect(status().isOk());
    }

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}