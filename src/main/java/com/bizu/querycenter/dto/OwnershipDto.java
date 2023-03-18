package com.bizu.querycenter.dto;

import com.bizu.querycenter.model.Employee;
import com.bizu.querycenter.model.Report;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnershipDto {

    private Report report;
    private Employee employee;
}
