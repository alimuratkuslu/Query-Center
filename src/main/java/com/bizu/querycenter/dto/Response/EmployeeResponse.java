package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Report;
import lombok.Builder;

import java.util.List;

@Builder
public class EmployeeResponse {

    private String name;
    private String email;
    private List<Report> reports;

}
