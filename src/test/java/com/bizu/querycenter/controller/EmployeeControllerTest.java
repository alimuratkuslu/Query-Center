package com.bizu.querycenter.controller;

import com.bizu.querycenter.dto.Request.SaveEmployeeRequest;
import com.bizu.querycenter.dto.Response.EmployeeResponse;
import com.bizu.querycenter.service.EmployeeService;
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

import static org.mockito.Mockito.when;
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

    private String serializeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}