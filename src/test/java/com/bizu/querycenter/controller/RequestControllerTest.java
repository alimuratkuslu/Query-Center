package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Request.SaveRequestRequest;
import com.bizu.querycenter.dto.Response.RequestResponse;
import com.bizu.querycenter.model.Request;
import com.bizu.querycenter.model.Status;
import com.bizu.querycenter.service.RequestService;
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
class RequestControllerTest {

    @MockBean
    private RequestService requestService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RequestResponse rejectedRequestResponse;

    private Request pendingRequest;

    private Request finishedRequest;

    private RequestResponse finishedRequestResponse;

    private SaveRequestRequest saveRequestRequest;

    private SaveRequestRequest updateRequestRequest;

    private RequestResponse requestResponse;

    private RequestResponse updatedRequestResponse;

    @BeforeEach
    public void init(){

        finishedRequestResponse = RequestResponse.builder()
                .description("testRequestDone")
                .status(Status.DONE)
                .build();

        finishedRequest = Request.builder()
                ._id(1)
                .description("testRequestDone")
                .status(Status.DONE)
                .build();

        pendingRequest = Request.builder()
                ._id(1)
                .description("testRequestPending")
                .status(Status.IN_PROGRESS)
                .build();

        rejectedRequestResponse = RequestResponse.builder()
                .description("testRequestRejected")
                .status(Status.REJECTED)
                .build();

        saveRequestRequest = SaveRequestRequest.builder()
                .description("testDesc")
                .build();

        updateRequestRequest = SaveRequestRequest.builder()
                .description("testDescUpdated")
                .build();

        requestResponse = RequestResponse.builder()
                .description("testDesc")
                .status(Status.IN_PROGRESS)
                .build();

        updatedRequestResponse = RequestResponse.builder()
                .description("testDescUpdated")
                .status(Status.IN_PROGRESS)
                .build();
    }

    @Test
    void itShouldSaveRequestWhenValidRequestBody() throws Exception {

        when(requestService.saveRequest(saveRequestRequest)).thenReturn(requestResponse);

        mockMvc.perform(post("/v1/request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(saveRequestRequest)))
                .andDo(print())
                .andExpect(jsonPath("$.description").value(saveRequestRequest.getDescription()))
                .andExpect(status().isCreated());
    }

    @Test
    void itShouldGetRequestWhenValidId() throws Exception {
        Integer requestId = 1;

        when(requestService.getRequestById(requestId)).thenReturn(finishedRequest);

        mockMvc.perform(get("/v1/request/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._id").value(requestId))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetRequestWhenValidIdParam() throws Exception {
        String stringRequestId = "1";
        Integer requestId = Integer.parseInt(stringRequestId);

        when(requestService.getRequestById(requestId)).thenReturn(finishedRequest);

        mockMvc.perform(get("/v1/request/searchRequest")
                        .param("id", stringRequestId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._id").value(requestId))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldUpdateRequestWhenValidRequestUpdateRequest() throws Exception {
        Integer requestId = 1;

        when(requestService.updateRequest(requestId, updateRequestRequest)).thenReturn(updatedRequestResponse);

        mockMvc.perform(put("/v1/request/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeJson(updateRequestRequest)))
                .andExpect(jsonPath("$.description").value("testDescUpdated"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDeleteRequestWhenValidId() throws Exception {
        Integer requestId = 1;

        doNothing().when(requestService).deleteRequestById(requestId);

        mockMvc.perform(delete("/v1/request/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldGetAllRequests() throws Exception {
        List<Request> requests = requestService.getAllRequests();

        int size = requests.size();

        when(requestService.getAllRequests()).thenReturn(requests);

        mockMvc.perform(get("/v1/request"))
                .andExpect(jsonPath("$", hasSize(size)));

    }

    @Test
    void itShouldRejectRequestWhenValidId() throws Exception {
        Integer requestId = 1;

        when(requestService.rejectRequest(requestId)).thenReturn(rejectedRequestResponse);

        mockMvc.perform(get("/v1/request/reject/{id}", requestId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("REJECTED"))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldDoneRequestWhenValidId() throws Exception {
        Integer requestId = 1;

        when(requestService.doneRequest(requestId)).thenReturn(finishedRequestResponse);

        mockMvc.perform(get("/v1/request/done/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("DONE"))
                .andExpect(status().isOk());
    }

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}