package com.bizu.querycenter.dto.Request;

import com.bizu.querycenter.model.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveRequestRequest {

    private String description;
}
