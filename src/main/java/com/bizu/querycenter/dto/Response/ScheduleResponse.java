package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Trigger;
import lombok.Builder;

import java.util.List;

@Builder
public class ScheduleResponse {


    private String name;

    private String mailSubject;

    private List<String> recipients;

    private List<Trigger> triggers;
}
