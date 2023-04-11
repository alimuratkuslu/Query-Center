package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Report;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document("DatabaseResponse")
public class DatabaseResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("connectionString")
    private String connectionString;

    @JsonProperty("reports")
    private List<Report> reports;
}
