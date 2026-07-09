package com.mes.mes_project.controller;

import com.mes.mes_project.dto.commoncode.CommonCodeRequestDto;
import com.mes.mes_project.dto.commoncode.CommonCodeResponseDto;
import com.mes.mes_project.service.CommonCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {

    private final CommonCodeService commonCodeService;

    // 세부코드 수정
    @PutMapping("/{id}")
    public CommonCodeResponseDto update(@PathVariable Long id,
                                         @Valid @RequestBody CommonCodeRequestDto requestDto) {
        return commonCodeService.update(id, requestDto);
    }

    // 세부코드 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commonCodeService.delete(id);
    }
}
