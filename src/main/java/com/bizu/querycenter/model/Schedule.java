package com.bizu.querycenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document("Schedules")
public class Schedule {

    @Id
    private Integer _id;

    private String name;

    private String triggerFreq;

    private List<String> recipients;

    @JsonIgnore
    @DBRef
    private List<Report> reports;
}
