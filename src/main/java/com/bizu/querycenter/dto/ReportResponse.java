package com.bizu.querycenter.dto;

import com.bizu.querycenter.model.Employee;
import lombok.Builder;

import java.util.List;

@Builder
public class ReportResponse {

    private String name;
    private List<Employee> employees;
}
