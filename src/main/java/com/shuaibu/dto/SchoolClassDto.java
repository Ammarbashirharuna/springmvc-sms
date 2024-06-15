package com.shuaibu.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolClassDto {
    private UUID id;
    
    // Todo: Add validation
    private String sectionName;
    private String className;
}
