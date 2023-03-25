package com.bizu.querycenter.dto.Request;

import com.bizu.querycenter.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveTriggerRequest {

    private String name;
    private List<Schedule> schedules;
}
