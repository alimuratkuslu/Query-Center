package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Schedule;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document("ReportResponse")
public class ReportResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("sqlQuery")
    private String sqlQuery;

    @JsonProperty("employees")
    private List<Employee> employees;

    @JsonProperty("schedules")
    private List<Schedule> schedules;
}
