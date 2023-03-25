package com.bizu.querycenter.dto.Request;

import com.bizu.querycenter.model.Trigger;
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

    private List<Trigger> triggers;
}
