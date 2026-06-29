package com.mes.mes_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "MES_USER")
@EntityListeners(AuditingEntityListener.class)
@Getter
@RequiredArgsConstructor
public class User implements UserDetails {  // 추가!{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;    // 암호화된 비밀번호

    @Column(length = 50)
    private String name;        // 이름 (홍길동)

    @Column(length = 20)
    private String role = "USER";  // 권한 (ADMIN, USER)

    private String useYn = "Y";

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // 비밀번호 변경
    public void changePassword(String password) {
        this.password = password;
    }

    // 사용자 등록
    public static User create(String username, String password,
                              String name, String role) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.name = name;
        user.role = role;
        return user;
    }

    // UserDetails 구현 메서드들
    @Override                                       // 권한 반환 (ROLE_ADMIN, ROLE_USER)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
    @Override       //계정만료여부
    public boolean isAccountNonExpired() { return true; }

    @Override       //계정잠금여부
    public boolean isAccountNonLocked() { return true; }

    @Override       //자격증명만료여부(비밀번호 만료여부)
    public boolean isCredentialsNonExpired() { return true; }

    @Override       //계정활성화여부
    public boolean isEnabled() { return "Y".equals(useYn); }

}
