package com.bizu.querycenter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveScheduleRequest {


    private String name;

    private String mailSubject;

    private List<String> recipients;
}
