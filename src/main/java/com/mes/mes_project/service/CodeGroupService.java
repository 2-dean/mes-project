package com.mes.mes_project.service;

import com.mes.mes_project.dto.CodeGroupRequestDto;
import com.mes.mes_project.dto.CodeGroupResponseDto;
import com.mes.mes_project.entity.CodeGroup;
import com.mes.mes_project.repository.CodeGroupRepository;
import com.mes.mes_project.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeGroupService {

    private final CodeGroupRepository codeGroupRepository;
    private final CommonCodeRepository commonCodeRepository;

    // 전체조회
    public List<CodeGroupResponseDto> findAll() {
        return codeGroupRepository.findAllByOrderByGroupCode()
                .stream()
                .map(CodeGroupResponseDto::from)
                .collect(Collectors.toList());
    }

    // 등록
    public CodeGroupResponseDto save(CodeGroupRequestDto requestDto) {
        CodeGroup group = codeGroupRepository.save(requestDto.toEntity());
        return CodeGroupResponseDto.from(group);
    }

    // 수정
    @Transactional
    public CodeGroupResponseDto update(Long id, CodeGroupRequestDto requestDto) {
        CodeGroup group = codeGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그룹코드 없음"));
        group.update(requestDto.getGroupName(), requestDto.getDescription(), requestDto.getUseYn());
        return CodeGroupResponseDto.from(group);
    }

    // 삭제 (하위 세부코드 함께 삭제)
    @Transactional
    public void delete(Long id) {
        CodeGroup group = codeGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그룹코드 없음"));
        commonCodeRepository.deleteAll(commonCodeRepository.findByGroup_Id(id));
        codeGroupRepository.delete(group);
    }
}
