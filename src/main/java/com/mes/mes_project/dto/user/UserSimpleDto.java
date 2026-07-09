package com.mes.mes_project.dto.user;

import com.mes.mes_project.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSimpleDto {
    // 작업자 선택 팝업 등에서 사용하는 최소 정보 (권한/비밀번호/감사필드 제외)

    private Long id;
    private String username;
    private String name;

    public static UserSimpleDto from(User user) {
        UserSimpleDto dto = new UserSimpleDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.name = user.getName();
        return dto;
    }
}
