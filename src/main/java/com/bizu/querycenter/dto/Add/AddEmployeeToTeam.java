package com.bizu.querycenter.dto.Add;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddEmployeeToTeam {

    private Integer employeeId;
    private Integer teamId;
}
