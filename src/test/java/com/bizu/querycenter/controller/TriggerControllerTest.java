package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Add.AddNameToTrigger;
import com.bizu.querycenter.dto.Request.SaveTriggerRequest;
import com.bizu.querycenter.dto.Response.TriggerResponse;
import com.bizu.querycenter.model.Trigger;
import com.bizu.querycenter.service.TriggerService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "integration")
class TriggerControllerTest {

    @MockBean
    private TriggerService triggerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Trigger trigger;

    private String triggerName;

    private SaveTriggerRequest saveTriggerRequest;

    private SaveTriggerRequest updatedTriggerRequest;

    private TriggerResponse triggerResponse;

    private TriggerResponse updatedTriggerResponse;

    private TriggerResponse addedNameToTriggerResponse;

    @BeforeEach
    public void init(){

        trigger = Trigger.builder()
                ._id(1)
                .name("testName")
                .cronExpression("testCron")
                .schedules(null)
                .build();

        triggerName = "addedTriggerName";

        saveTriggerRequest = SaveTriggerRequest.builder()
                .name("testName")
                .cronExpression("testCron")
                .schedules(null)
                .build();

        updatedTriggerRequest = SaveTriggerRequest.builder()
                .name("testNameUpdated")
                .cronExpression("testCronUpdated")
                .schedules(null)
                .build();

        triggerResponse = TriggerResponse.builder()
                .name("testName")
                .cronExpression("testCron")
                .build();

        updatedTriggerResponse = TriggerResponse.builder()
                .name("testNameUpdated")
                .cronExpression("testCronUpdated")
                .build();

        addedNameToTriggerResponse = TriggerResponse.builder()
                .name("addedTriggerName")
                .cronExpression("testCron")
                .build();
    }

    @Test
    void itShouldSaveTriggerWhenValidTriggerBody() throws Exception {

        when(triggerService.saveTrigger(saveTriggerRequest)).thenReturn(triggerResponse);

        mockMvc.perform(post("/v1/trigger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(saveTriggerRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldGetTriggerWhenValidId() throws Exception {
        Integer triggerId = 1;

        when(triggerService.getTriggerById(triggerId)).thenReturn(trigger);

        mockMvc.perform(get("/v1/trigger/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._id").value(1))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldUpdateTriggerWhenValidTriggerUpdateRequest() throws Exception {
        Integer triggerId = 1;

        when(triggerService.updateTrigger(triggerId, updatedTriggerRequest)).thenReturn(updatedTriggerResponse);

        mockMvc.perform(put("/v1/trigger/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(updatedTriggerRequest)))
                .andExpect(jsonPath("$.name").value("testNameUpdated"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeleteTriggerWhenValidId() throws Exception {
        Integer triggerId = 1;

        doNothing().when(triggerService).deleteTriggerById(triggerId);

        mockMvc.perform(delete("/v1/trigger/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetAllTriggers() throws Exception {
        List<Trigger> triggers = triggerService.getAllTriggers();

        int size = triggers.size();

        when(triggerService.getAllTriggers()).thenReturn(triggers);

        mockMvc.perform(get("/v1/trigger"))
                .andExpect(jsonPath("$", hasSize(size)));

    }

    @Test
    void itShouldGetTriggerWhenValidTriggerName() throws Exception {

        String triggerName = "testName";

        when(triggerService.getTriggerByName(triggerName)).thenReturn(trigger);

        mockMvc.perform(get("/v1/trigger/searchTrigger")
                        .param("name", triggerName)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(trigger)))
                .andDo(print())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldAddNameToTriggerWhenValidAddNameToTriggerBody() throws Exception {

        Integer triggerId = 1;

        AddNameToTrigger request = AddNameToTrigger.builder()
                .name(triggerName)
                .build();

        when(triggerService.addName(triggerId, request)).thenReturn(addedNameToTriggerResponse);

        mockMvc.perform(post("/v1/trigger/addName/{id}", triggerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(request)))
                .andExpect(jsonPath("$.name").value("addedTriggerName"))
                .andExpect(status().isOk());
    }

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}