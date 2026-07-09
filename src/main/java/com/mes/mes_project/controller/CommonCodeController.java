package com.mes.mes_project.controller;

import com.mes.mes_project.dto.commoncode.CommonCodeResponseDto;
import com.mes.mes_project.service.CommonCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/common-codes")
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping("/{groupCode}")
    public List<CommonCodeResponseDto> findByGroupCode(@PathVariable String groupCode) {
        return commonCodeService.findByGroupCode(groupCode);
    }
}
