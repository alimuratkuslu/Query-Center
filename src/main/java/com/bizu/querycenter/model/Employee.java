package com.bizu.querycenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private Role role;

}
