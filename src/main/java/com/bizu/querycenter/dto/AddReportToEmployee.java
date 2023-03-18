package com.bizu.querycenter.dto;

import com.bizu.querycenter.model.Report;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddReportToEmployee {

    private Integer employeeId;
    private Report report;
}
