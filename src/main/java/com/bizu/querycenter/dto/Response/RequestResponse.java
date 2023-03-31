package com.bizu.querycenter.dto.Response;

import com.bizu.querycenter.model.Status;
import lombok.Builder;

@Builder
public class RequestResponse {

    private String description;
    private Status status;
}
