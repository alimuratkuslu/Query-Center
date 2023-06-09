package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Report;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document("EmployeeResponse")
public class EmployeeResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;


    @JsonProperty("reports")
    private List<Report> reports;

}
