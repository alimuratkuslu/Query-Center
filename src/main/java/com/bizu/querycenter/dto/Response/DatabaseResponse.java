package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Report;
import lombok.Builder;

import java.util.List;

@Builder
public class DatabaseResponse {

    private String name;
    private String connectionString;
    private List<Report> reports;
}
