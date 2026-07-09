package com.mes.mes_project.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
    private String name;   // 추가
    private String role;   // 추가
}