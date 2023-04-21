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
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document("Employees")
public class Employee {

    @Id
    private Integer _id;

    private String name;
    private String email;

    @JsonIgnore
    @DBRef
    private List<Report> reports;

    private boolean isActive;

    private String password;

    @DBRef
    private Set<Role> roles;
}
