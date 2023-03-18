package com.bizu.querycenter.model;

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
@Document("reports")
public class Report {

    @Id
    private Integer id;

    private String name;

    @DBRef
    private List<Employee> employees;

    /*
    private Boolean isOwner;
    private Boolean isRead;
    private Boolean isWrite;
    private Boolean isRun;
    */

}
