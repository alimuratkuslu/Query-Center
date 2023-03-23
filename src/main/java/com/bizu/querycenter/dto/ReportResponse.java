package com.bizu.querycenter.dto;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Schedule;
import lombok.Builder;

import java.util.List;

@Builder
public class ReportResponse {

    private String name;
    private String sqlQuery;
    private List<Employee> employees;
    private List<Schedule> schedules;
}
