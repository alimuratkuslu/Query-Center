package com.bizu.querycenter.dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveTeamRequest {

    private String name;
    private String teamMail;
}
