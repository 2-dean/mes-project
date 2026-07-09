package com.mes.mes_project.service;

import com.mes.mes_project.dto.commoncode.CommonCodeRequestDto;
import com.mes.mes_project.dto.commoncode.CommonCodeResponseDto;
import com.mes.mes_project.entity.CodeGroup;
import com.mes.mes_project.entity.CommonCode;
import com.mes.mes_project.repository.CodeGroupRepository;
import com.mes.mes_project.repository.CommonCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;
    private final CodeGroupRepository codeGroupRepository;

    // 그룹코드 문자열로 활성화된 세부코드 조회 (드롭다운 등에서 사용)
    public List<CommonCodeResponseDto> findByGroupCode(String groupCode) {
        return commonCodeRepository.findByGroup_GroupCodeAndUseYnOrderBySortOrder(groupCode, "Y")
                .stream()
                .map(CommonCodeResponseDto::from)
                .collect(Collectors.toList());
    }

    // 그룹 ID로 세부코드 전체조회 (관리화면)
    public List<CommonCodeResponseDto> findByGroupId(Long groupId) {
        return commonCodeRepository.findByGroup_IdOrderBySortOrder(groupId)
                .stream()
                .map(CommonCodeResponseDto::from)
                .collect(Collectors.toList());
    }

    // 등록
    @Transactional
    public CommonCodeResponseDto save(Long groupId, CommonCodeRequestDto requestDto) {
        CodeGroup group = codeGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("그룹코드 없음"));
        CommonCode commonCode = requestDto.toEntity();
        commonCode.setGroup(group);
        return CommonCodeResponseDto.from(commonCodeRepository.save(commonCode));
    }

    // 수정
    @Transactional
    public CommonCodeResponseDto update(Long id, CommonCodeRequestDto requestDto) {
        CommonCode commonCode = commonCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("세부코드 없음"));
        commonCode.update(
                requestDto.getCodeName(),
                requestDto.getDescription(),
                requestDto.getSortOrder(),
                requestDto.getUseYn()
        );
        return CommonCodeResponseDto.from(commonCode);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        CommonCode commonCode = commonCodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("세부코드 없음"));
        commonCodeRepository.delete(commonCode);
    }
}
