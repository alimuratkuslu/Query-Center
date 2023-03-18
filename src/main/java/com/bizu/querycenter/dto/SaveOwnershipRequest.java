package com.bizu.querycenter.dto;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveOwnershipRequest {

    private Integer _id;
    private Report report;
    private Employee employee;
    private boolean isOwner;
    private boolean isRead;
    private boolean isWrite;
    private boolean isRun;
}
