package com.bizu.querycenter.dto;

import com.bizu.querycenter.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {

    private Schedule schedule;
    private String filter;
    private String databaseName;
}
