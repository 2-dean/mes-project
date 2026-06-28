package com.mes.mes_project.controller;

import com.mes.mes_project.dto.LoginRequestDto;
import com.mes.mes_project.dto.LoginResponseDto;
import com.mes.mes_project.entity.User;
import com.mes.mes_project.repository.UserRepository;
import com.mes.mes_project.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 로그인
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {

        // 1. 유저 조회
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호 불일치");
        }

        // 3. JWT 토큰 생성
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        // 4. 토큰 반환
        return new LoginResponseDto(token, user.getUsername(), user.getName(), user.getRole());
    }

    // 유저등록
    @PostMapping("/register")
    public void register(@RequestBody LoginRequestDto request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.create(
                request.getUsername(),
                encodedPassword,
                request.getName(),
                request.getRole()
        );
        userRepository.save(user);
    }
}