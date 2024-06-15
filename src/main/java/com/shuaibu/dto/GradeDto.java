package com.shuaibu.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GradeDto {
    private UUID id;
    
    // Todo: Add validation
    private String range;
    private String grade;
    private String remark;

}
