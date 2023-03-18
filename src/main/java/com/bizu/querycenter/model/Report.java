package com.bizu.querycenter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToMany(mappedBy = "reports")
    private List<Employee> employees;

    private Boolean isOwner;
    private Boolean isRead;
    private Boolean isWrite;
    private Boolean isRun;


}
