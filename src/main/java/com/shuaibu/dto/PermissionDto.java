package com.shuaibu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDto {
    private Long id;
    
    // Todo: Add validation
    private String permissionName;

}
