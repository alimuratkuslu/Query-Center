package com.bizu.querycenter.dto.Response;

import lombok.Builder;

@Builder
public class TriggerResponse {

    private String name;

    private String cronExpression;
}
