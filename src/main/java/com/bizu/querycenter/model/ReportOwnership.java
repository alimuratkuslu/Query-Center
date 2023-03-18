package com.bizu.querycenter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document("ReportOwnerships")
public class ReportOwnership {

    @Id
    private Integer _id;

    @DBRef
    private Report report;
    @DBRef
    private Employee employee;

    private boolean isOwner;
    private boolean isRead;
    private boolean isWrite;
    private boolean isRun;
}
