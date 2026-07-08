package com.mes.mes_project.dto;

import com.mes.mes_project.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String username;
    private String name;
    private String role;
    private String useYn;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public static UserResponseDto from(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.name = user.getName();
        dto.role = user.getRole();
        dto.useYn = user.getUseYn();
        dto.createdBy = user.getCreatedBy();
        dto.createdAt = user.getCreatedAt();
        dto.updatedBy = user.getUpdatedBy();
        dto.updatedAt = user.getUpdatedAt();
        return dto;
    }
}
