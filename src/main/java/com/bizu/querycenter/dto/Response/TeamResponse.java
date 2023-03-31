package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Employee;
import lombok.Builder;

import java.util.List;

@Builder
public class TeamResponse {

    private String name;

    private List<Employee> employees;

    private String teamMail;
}
