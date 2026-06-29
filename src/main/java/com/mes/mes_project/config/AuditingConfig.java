package com.mes.mes_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditingConfig implements AuditorAware<String> {
                                    // Spring이 제공한 인터페이스

    /**
     * 현재 로그인한 유저가 누구인지 알려주는 역할
     */

    @Override
    public Optional<String> getCurrentAuditor() {
        // 1. SecurityContext에서 인증 정보 꺼내기
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();

        // 2. 로그인 안 됐으면 SYSTEM 반환
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getName().equals("anonymousUser")) {
            return Optional.of("SYSTEM");
        }
        // 3. 로그인한 유저 username 반환
        return Optional.of(authentication.getName());
    }
}
