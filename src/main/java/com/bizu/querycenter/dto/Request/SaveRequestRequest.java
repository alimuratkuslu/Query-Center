package com.bizu.querycenter.dto.Request;

import com.bizu.querycenter.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveRequestRequest {

    private String description;
}
