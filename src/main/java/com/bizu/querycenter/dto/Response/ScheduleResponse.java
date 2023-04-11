package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Trigger;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document("ScheduleResponse")
public class ScheduleResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("mailSubject")
    private String mailSubject;

    @JsonProperty("recipients")
    private List<String> recipients;

    @JsonProperty("triggers")
    private List<Trigger> triggers;
}
