package com.bizu.querycenter.dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveReportRequest {

    private String name;
    private String sqlQuery;

    private String databaseName;
}
