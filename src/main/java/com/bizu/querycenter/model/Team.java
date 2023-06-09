package com.bizu.querycenter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document("Teams")
public class Team {

    @Id
    private Integer _id;

    private String name;

    private List<Employee> employees;

    private String teamMail;
}
