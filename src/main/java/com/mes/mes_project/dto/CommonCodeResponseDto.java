package com.mes.mes_project.dto;

import com.mes.mes_project.entity.CommonCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommonCodeResponseDto {

    private Long id;
    private Long groupId;
    private String groupCode;
    private String groupName;
    private String code;
    private String codeName;
    private String description;
    private int sortOrder;
    private String useYn;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommonCodeResponseDto from(CommonCode commonCode) {
        CommonCodeResponseDto dto = new CommonCodeResponseDto();
        dto.id = commonCode.getId();
        dto.groupId = commonCode.getGroup().getId();
        dto.groupCode = commonCode.getGroup().getGroupCode();
        dto.groupName = commonCode.getGroup().getGroupName();
        dto.code = commonCode.getCode();
        dto.codeName = commonCode.getCodeName();
        dto.description = commonCode.getDescription();
        dto.sortOrder = commonCode.getSortOrder();
        dto.useYn = commonCode.getUseYn();
        dto.createdBy = commonCode.getCreatedBy();
        dto.updatedBy = commonCode.getUpdatedBy();
        dto.createdAt = commonCode.getCreatedAt();
        dto.updatedAt = commonCode.getUpdatedAt();
        return dto;
    }
}
