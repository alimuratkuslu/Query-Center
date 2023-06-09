package com.bizu.querycenter.dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveEmployeeRequest {

    private String name;
    private String email;
    private String password;
}
