package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Report;
import com.bizu.querycenter.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Builder
@Document("EmployeeResponse")
public class EmployeeResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;


    @JsonProperty("reports")
    private List<Report> reports;

    @JsonProperty("roles")
    private Set<Role> roles;
}
