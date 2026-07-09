package com.mes.mes_project.controller;

import com.mes.mes_project.dto.codegroup.CodeGroupRequestDto;
import com.mes.mes_project.dto.codegroup.CodeGroupResponseDto;
import com.mes.mes_project.dto.commoncode.CommonCodeRequestDto;
import com.mes.mes_project.dto.commoncode.CommonCodeResponseDto;
import com.mes.mes_project.service.CodeGroupService;
import com.mes.mes_project.service.CommonCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/code-groups")
@RequiredArgsConstructor
public class CodeGroupController {

    private final CodeGroupService codeGroupService;
    private final CommonCodeService commonCodeService;

    // 그룹코드 전체조회
    @GetMapping
    public List<CodeGroupResponseDto> findAll() {
        return codeGroupService.findAll();
    }

    // 그룹코드 등록
    @PostMapping
    public CodeGroupResponseDto save(@Valid @RequestBody CodeGroupRequestDto requestDto) {
        return codeGroupService.save(requestDto);
    }

    // 그룹코드 수정
    @PutMapping("/{id}")
    public CodeGroupResponseDto update(@PathVariable Long id,
                                        @Valid @RequestBody CodeGroupRequestDto requestDto) {
        return codeGroupService.update(id, requestDto);
    }

    // 그룹코드 삭제 (하위 세부코드 함께 삭제)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        codeGroupService.delete(id);
    }

    // 그룹 하위 세부코드 전체조회
    @GetMapping("/{groupId}/codes")
    public List<CommonCodeResponseDto> findCodes(@PathVariable Long groupId) {
        return commonCodeService.findByGroupId(groupId);
    }

    // 그룹 하위 세부코드 등록
    @PostMapping("/{groupId}/codes")
    public CommonCodeResponseDto saveCode(@PathVariable Long groupId,
                                           @Valid @RequestBody CommonCodeRequestDto requestDto) {
        return commonCodeService.save(groupId, requestDto);
    }
}
