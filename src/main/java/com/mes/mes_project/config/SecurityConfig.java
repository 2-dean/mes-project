package com.mes.mes_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // SpringSecurity 활성화
public class SecurityConfig {


    @Bean  // 모든 http요청들이 여기를 거쳐감
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                // CSRF 보안 비활성화
                // REST API는 보통 끔
                // (CSRF는 브라우저 기반 공격 방어용인데
                //  JWT 쓰면 필요 없음)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 일단 전체 허용
                        // 모든 요청 허용
                        // 나중에 JWT 추가하면
                        // 여기서 인증 필요한 URL 지정
                );
        return http.build();
    }
}
