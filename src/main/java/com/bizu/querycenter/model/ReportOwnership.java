package com.bizu.querycenter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document("ReportOwnerships")
public class ReportOwnership {

    private Integer _id;

    private Integer reportId;
    private Integer employeeId;
    private boolean isOwner;
    private boolean isRead;
    private boolean isWrite;
    private boolean isRun;
}
