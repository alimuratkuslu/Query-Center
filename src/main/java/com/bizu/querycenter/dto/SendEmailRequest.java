package com.bizu.querycenter.dto;

import com.bizu.querycenter.model.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailRequest {

    private Report report;
    private String filter;
    private String projection;
}
