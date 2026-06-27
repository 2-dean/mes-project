package com.mes.mes_project.controller;

import com.mes.mes_project.dto.ScanRequestDto;
import com.mes.mes_project.entity.ProdResult;
import com.mes.mes_project.service.ProdResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prod-results")
@RequiredArgsConstructor
public class ProdResultController {

    private final ProdResultService prodResultService;

    // 전체조회
    @GetMapping()
    public List<ProdResult> findAll() {
        return prodResultService.findAll();
    }

    // 작업지시별 조회
    @GetMapping("/{workOrderId}")
    public List<ProdResult> findByWorkOrderId(@PathVariable Long workOrderId) {
        return prodResultService.findByWorkOrderId(workOrderId);
    }

    // 스캔
    @PostMapping("/scan")
    public void scan (@RequestBody ScanRequestDto dto) {
        prodResultService.scan(dto.getWorkOrderId(), dto.getWorker(), dto.getQty());
    }
    // 수동입력
    @PostMapping("/manual")
    public void manual (@RequestBody ScanRequestDto dto) {
        prodResultService.manual(dto.getWorkOrderId(), dto.getWorker(), dto.getQty());
    }
    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        prodResultService.delete(id);
    }
}
