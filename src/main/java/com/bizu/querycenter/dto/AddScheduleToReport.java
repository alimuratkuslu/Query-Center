package com.bizu.querycenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddScheduleToReport {

    private Integer reportId;
    private Integer scheduleId;
}
