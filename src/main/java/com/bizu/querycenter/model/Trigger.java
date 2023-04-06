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
@Document("Triggers")
public class Trigger {

    @Id
    private Integer _id;

    private String name;

    private String cronExpression;

    @JsonIgnore
    @DBRef
    private List<Schedule> schedules;
}
