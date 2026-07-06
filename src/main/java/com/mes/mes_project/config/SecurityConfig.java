package com.mes.mes_project.config;

import com.mes.mes_project.security.CustomUserDetailsService;
import com.mes.mes_project.security.JwtFilter;
import com.mes.mes_project.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity // SpringSecurity 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * BCrypt 암호화 Bean 등록
     * 로그인할 때 비밀번호 검증에 사용
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean  // 모든 http요청들이 여기를 거쳐감 > SecurityConfig 설정대로 처리
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())   // CSRF 보안 끄기 (브라우저 세션기반 공격방어용)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // 추가!
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 안 쓴다고 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 누구나 접근가능 (로그인은 허용해야하니깐ㅋ)

                        // 사용자 관리는 조회(GET)까지 포함해서 ADMIN만
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // 생산실적 등록(스캔/수동입력)은 현장 작업자(USER)도 가능
                        .requestMatchers(HttpMethod.POST, "/api/prod-results/scan", "/api/prod-results/manual").authenticated()

                        // 게스트(USER)는 조회(GET)만 가능, 나머지 쓰기 요청은 ADMIN만 허용
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        // 나머지(GET 등 조회)는 인증만 되면 ADMIN/게스트(USER) 모두 허용
                        .anyRequest().authenticated() // 나머지는 인증필요 (토큰없으면 401에러)
            )
            .addFilterBefore( // JwtFilter를 Spring Security 필터 앞에 추가,  토큰검증후 SecurityContext에 저장
                new JwtFilter(jwtUtil, customUserDetailsService),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
    // 메서드 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
