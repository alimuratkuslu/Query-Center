package com.bizu.querycenter.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("TriggerResponse")
public class TriggerResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("cronExpression")
    private String cronExpression;
}
