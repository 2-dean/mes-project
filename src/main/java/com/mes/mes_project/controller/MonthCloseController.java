package com.mes.mes_project.controller;

import com.mes.mes_project.entity.MonthClose;
import com.mes.mes_project.service.MonthCloseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/month-close")
@RequiredArgsConstructor
public class MonthCloseController {

    private final MonthCloseService monthCloseService;

    // 월마감
    @PostMapping
    public void monthClose(@RequestBody Map<String, String> request) {
        monthCloseService.monthClose(
                request.get("yearMonth"),
                request.get("closedBy")
        );
    }

    // 월마감 취소
    @DeleteMapping
    public void cancelMonthClose(@RequestBody Map<String, String> request) {
        monthCloseService.cancelMonthClose(request.get("yearMonth"));
    }

    // 전체조회
    @GetMapping
    public List<MonthClose> findAll() {
        return monthCloseService.findAll();
    }
}