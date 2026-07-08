package com.mes.mes_project.service;

import com.mes.mes_project.dto.UserPasswordDto;
import com.mes.mes_project.dto.UserRequestDto;
import com.mes.mes_project.dto.UserResponseDto;
import com.mes.mes_project.dto.UserSimpleDto;
import com.mes.mes_project.entity.User;
import com.mes.mes_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::from)
                .collect(Collectors.toList());
    }

    // 작업자 선택 팝업 등에서 사용 (활성 사용자만, 최소 정보만 노출)
    public List<UserSimpleDto> findAllActiveSimple() {
        return userRepository.findByUseYnOrderByUsername("Y")
                .stream()
                .map(UserSimpleDto::from)
                .collect(Collectors.toList());
    }

    public UserResponseDto create(UserRequestDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }
        User user = User.create(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getName(),
                dto.getRole()
        );
        return UserResponseDto.from(userRepository.save(user));
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        // ADMIN 계정은 인원수에 상관없이 강등/비활성화 불가
        if ("ADMIN".equals(user.getRole())
                && (!"ADMIN".equals(dto.getRole()) || !"Y".equals(dto.getUseYn()))) {
            throw new RuntimeException("관리자(ADMIN) 계정은 강등하거나 비활성화할 수 없습니다.");
        }

        user.update(dto.getName(), dto.getRole(), dto.getUseYn());
        return UserResponseDto.from(user);
    }

    @Transactional
    public void changePassword(Long id, UserPasswordDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        user.changePassword(passwordEncoder.encode(dto.getNewPassword()));
    }
}
