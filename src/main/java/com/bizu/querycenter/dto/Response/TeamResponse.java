package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document("TeamResponse")
public class TeamResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("employees")
    private List<Employee> employees;

    @JsonProperty("teamMail")
    private String teamMail;
}
