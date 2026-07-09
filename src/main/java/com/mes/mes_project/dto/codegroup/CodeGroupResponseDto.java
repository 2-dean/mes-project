package com.mes.mes_project.dto.codegroup;

import com.mes.mes_project.entity.CodeGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CodeGroupResponseDto {

    private Long id;
    private String groupCode;
    private String groupName;
    private String description;
    private String useYn;

    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CodeGroupResponseDto from(CodeGroup group) {
        CodeGroupResponseDto dto = new CodeGroupResponseDto();
        dto.id = group.getId();
        dto.groupCode = group.getGroupCode();
        dto.groupName = group.getGroupName();
        dto.description = group.getDescription();
        dto.useYn = group.getUseYn();
        dto.createdBy = group.getCreatedBy();
        dto.updatedBy = group.getUpdatedBy();
        dto.createdAt = group.getCreatedAt();
        dto.updatedAt = group.getUpdatedAt();
        return dto;
    }
}
