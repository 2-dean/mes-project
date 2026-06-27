package com.mes.mes_project.controller;

import com.mes.mes_project.entity.ProdIncentive;
import com.mes.mes_project.service.ProdIncentiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prod-incentives")
public class ProdIncentiveController {

    private final ProdIncentiveService prodIncentiveService;

    // 전체조회
    @GetMapping
    public List<ProdIncentive> findAll() {
        return prodIncentiveService.findAll();
    }

    // 작업지시별 조회
    @GetMapping("/work-order/{workOrderId}")
    public List<ProdIncentive> findByWorkOrderId(@PathVariable Long workOrderId) {
        return prodIncentiveService.findByWorkOrderId(workOrderId);
    }
}
