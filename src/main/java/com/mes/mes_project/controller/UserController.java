package com.mes.mes_project.controller;

import com.mes.mes_project.dto.user.UserPasswordDto;
import com.mes.mes_project.dto.user.UserRequestDto;
import com.mes.mes_project.dto.user.UserResponseDto;
import com.mes.mes_project.dto.user.UserSimpleDto;
import com.mes.mes_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    // 작업자 선택 팝업용 (전 권한 사용자 조회 가능 — SecurityConfig에서 별도 허용)
    @GetMapping("/simple")
    public List<UserSimpleDto> findAllActiveSimple() {
        return userService.findAllActiveSimple();
    }

    @PostMapping
    public UserResponseDto create(@RequestBody UserRequestDto dto) {
        return userService.create(dto);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return userService.update(id, dto);
    }

    @PutMapping("/{id}/password")
    public void changePassword(@PathVariable Long id, @RequestBody UserPasswordDto dto) {
        userService.changePassword(id, dto);
    }
}
