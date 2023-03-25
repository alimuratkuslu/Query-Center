package com.bizu.querycenter.dto.Add;

import com.bizu.querycenter.model.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReportToEmployee {

    private String reportName;
}
