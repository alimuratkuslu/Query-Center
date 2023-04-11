package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("RequestResponse")
public class RequestResponse {

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private Status status;
}
